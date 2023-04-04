package br.bkraujo.engine.core.graphics.opengl;

import br.bkraujo.engine.Logger;

import static org.lwjgl.opengl.ARBImaging.GL_TABLE_TOO_LARGE;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_INVALID_FRAMEBUFFER_OPERATION;
import static org.lwjgl.opengl.GL45C.GL_CONTEXT_LOST;

abstract class GLObject {

    protected boolean hasError() {
        switch(glGetError()) {
            case GL_INVALID_ENUM: Logger.fatal("Invalid Enum");
            case GL_INVALID_VALUE: Logger.fatal("Invalid Value");
            case GL_INVALID_OPERATION: Logger.fatal("Invalid Operation");
            case GL_STACK_OVERFLOW: Logger.fatal("Stack Overflow");
            case GL_STACK_UNDERFLOW: Logger.fatal("Stack Underflow");
            case GL_CONTEXT_LOST: Logger.fatal("Context lost");
            case GL_TABLE_TOO_LARGE: Logger.fatal("[ARB] Table too Large");
            case GL_INVALID_FRAMEBUFFER_OPERATION: Logger.fatal("Invalid Framebuffer Operation");
            case GL_OUT_OF_MEMORY: Logger.fatal("Out of Memory");
        }

        return false;
    }

}
