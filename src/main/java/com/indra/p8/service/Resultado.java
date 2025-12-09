package com.indra.p8.service;

import lombok.Data;

@Data
public class Resultado<T> extends Error {

    private final T resultado;

    public Resultado(String error) {
        super(error);
        this.resultado = null;
    }

    public Resultado(T resultado) {
        super();
        this.resultado = resultado;
    }
}