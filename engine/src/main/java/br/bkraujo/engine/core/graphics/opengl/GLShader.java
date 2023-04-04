package br.bkraujo.engine.core.graphics.opengl;

import br.bkraujo.engine.graphics.intrinsics.Shader;
import br.bkraujo.engine.graphics.intrinsics.ShaderDataType;
import br.bkraujo.utils.Resources;
import org.joml.Matrix4fc;

import java.util.HashMap;
import java.util.Map;

import static br.bkraujo.engine.Logger.*;
import static org.lwjgl.opengl.GL20.*;

final class GLShader extends GLObject implements Shader {

    private Integer program;
    private final Map<Type, String> sources = new HashMap<>();
    private final Map<String, Integer> uniforms = new HashMap<>();
    private final Map<String, Integer> attributes = new HashMap<>();

    GLShader(){}

    public int getId() {
        return program;
    }

    public boolean initialize() {
        // Do not reinitialize
        if (program != null) return true;
        program = glCreateProgram();

        if (!sources.containsKey(Type.VERTEX)) {
            warn("[ID %s] No VERTEX shader supplied, using packed shader", program);
            sources.put(Type.VERTEX, Resources.asString("common.vert"));
        }

        final var vertex = compile(Type.VERTEX);
        if (vertex == 0) return false;

        if (!sources.containsKey(Type.FRAGMENT)) {
            warn("[ID %s] No FRAGMENT shader supplied, using packed shader", program);
            sources.put(Type.FRAGMENT, Resources.asString("common.frag"));
        }

        final var fragment = compile(Type.FRAGMENT);
        if (fragment == 0) {
            delete(vertex);
            return false;
        }

        var geometry = 0;
        if (sources.containsKey(Type.GEOMETRY)) {
            geometry = compile(Type.GEOMETRY);
            if (geometry == 0) {
                delete(vertex, fragment);
                return false;
            }
        }

        var tesselation_c = 0;
        if (sources.containsKey(Type.TESSELATION_CONTROL)) {
            tesselation_c = compile(Type.TESSELATION_CONTROL);
            if (tesselation_c == 0) {
                delete(vertex, fragment, geometry);
                return false;
            }
        }

        var tesselation_e = 0;
        if (sources.containsKey(Type.TESSELATION_EVALUATION)) {
            tesselation_e = compile(Type.TESSELATION_EVALUATION);
            if (tesselation_e == 0) {
                delete(vertex, fragment, geometry, tesselation_c);
                return false;
            }
        }

        glLinkProgram(program);
        if(glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE) {
            glDeleteProgram(program);
            error("[ID %s] Failed to link program: %s", program, glGetProgramInfoLog(program, Integer.MAX_VALUE));
            return false;
        }

        delete(vertex, fragment, geometry, tesselation_c, tesselation_e);
        return true;
    }

    public boolean addSource(Type type, String source) {
        if (sources.containsKey(type)) {
            error("[ID %s] Source already present for %s Shader", program, type.name());
            return false;
        }

        if (source == null || source.trim().length() == 0) {
            error("[ID %s] Null or Empty source for %s Shader", program, type.name());
            return false;
        }

        sources.put(type, source);
        return true;
    }

    private int compile(Type type) {
        checkInitialized();
        if (!sources.containsKey(type)) {
            error("[ID %s] Failed to compile %s Shader: No source available.", program, type.name());
            return 0;
        }

        final var source = sources.get(type);
        if (source == null || source.trim().length() == 0) {
            error("[ID %s] Failed to compile %s Shader: Empty source.", program, type.name());
            return 0;
        }

        final var handle = glCreateShader(type.value);
        if (handle == 0) {
            error("[ID %s] Failed to compile %s Shader: glCreateShader() failed ", program, type.name());
            return 0;
        }

        glShaderSource(handle, sources.get(type));
        glCompileShader(handle);
        if(glGetShaderi(handle, GL_COMPILE_STATUS) != GL_TRUE) {
            error("[ID %s] Failed to compile %s Shader: %s", program, type.name(), glGetShaderInfoLog(handle, Integer.MAX_VALUE));
            return 0;
        }

        glAttachShader(program, handle);
        if (hasError()) {
            error("[ID %s] glAttachShader(program, %s)", program, type.name());
            return 0;
        }

        return handle;
    }

    private void delete(int ... value) {
        for (var shader : value)
            if (shader != 0)
                glDeleteShader(shader);
    }

    public void bind() {
        checkInitialized();
        glUseProgram(program);
    }

    public void unbind() {
        checkInitialized();
        glUseProgram(GL_NONE);
    }

    public int getUniformLocation(String name) {
        checkInitialized();
        if (uniforms.containsKey(name))
            return uniforms.get(name);

        uniforms.put(name, hasError() ? -1 : glGetUniformLocation(program, name));
        return uniforms.get(name);
    }

    public int getAttributeLocation(String name) {
        checkInitialized();
        if (attributes.containsKey(name))
            return attributes.get(name);

        attributes.put(name, hasError() ? -1 : glGetAttribLocation(program, name));
        return attributes.get(name);
    }

    public boolean setUniform(String name, int ... value) {
        final var location = getUniformLocation(name);
        if (location == -1) return false;

        if (value.length == 0 || value.length > 4) {
            error("[ID %s] Invalid value length [%s]", program, value.length);
            return false;
        }

        if (value.length == 1) glUniform1iv(location, value);
        if (value.length == 2) glUniform2iv(location, value);
        if (value.length == 3) glUniform3iv(location, value);
        if (value.length == 4) glUniform4iv(location, value);

        return !hasError();
    }

    public boolean setUniform(String name, float ... value) {
        final var location = getUniformLocation(name);
        if (location == -1) return false;

        if (value.length == 0 || value.length > 4) {
            error("[ID %s] Invalid value length [%s]", program, value.length);
            return false;
        }

        if (value.length == 1) glUniform1fv(location, value);
        if (value.length == 2) glUniform2fv(location, value);
        if (value.length == 3) glUniform3fv(location, value);
        if (value.length == 4) glUniform4fv(location, value);

        return !hasError();
    }

    public boolean setUniformMatrix(String name, boolean transpose, Matrix4fc matrix) {
        return setUniformMatrix(name, transpose, matrix.get(new float[16]));
    }

    public boolean setUniformMatrix(String name, boolean transpose, float ... value) {
        final var location = getUniformLocation(name);
        if (location == -1) return false;

        if (value.length != 4 && value.length != 9 && value.length != 16) {
            error("[ID %s] Invalid value length [%s]", program, value.length);
            return false;
        }

        if (value.length == 4) glUniformMatrix2fv(location, transpose, value);
        if (value.length == 9) glUniformMatrix3fv(location, transpose, value);
        if (value.length == 16) glUniformMatrix4fv(location, transpose, value);

        return !hasError();
    }

    public void terminate() {
        if (program != null) glDeleteProgram(program);
    }

    public static int translate(ShaderDataType type) {
        switch (type) {
            case INT:
            case INT2:
            case INT3:
            case INT4: return GL_INT;
            case FLOAT:
            case FLOAT2:
            case FLOAT3:
            case FLOAT4:
            case MAT2:
            case MAT3:
            case MAT4: return GL_FLOAT;
            case BOOL: return GL_BOOL;
        }

        return -1;
    }

    private void checkInitialized() {
        if (program == null)
            fatal("Shader not initialized");
    }
}
