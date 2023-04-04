#version 330 core

layout (location = 0) in vec3 iPosition;

uniform mat4 uTransform;
uniform mat4 uViewProjection;

void main() {
    gl_Position = uViewProjection * uTransform * vec4(iPosition, 1.0);
}