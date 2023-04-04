package br.bkraujo.engine.graphics.intrinsics;

import br.bkraujo.engine.graphics.Bindable;

import java.util.List;

public interface VertexBuffer extends Buffer, Bindable {

    void setLayout(BufferLayout... layout);
    List<BufferLayout> getLayout();
}
