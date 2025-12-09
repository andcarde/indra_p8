package com.indra.p8.service;

import lombok.Data;

@Data
public class Error {

    private final String error;

    public Error() {
        this.error = null;
    }

    public Error(String error) {
        this.error = error;
    }

    public boolean hasError() {
        return error != null;
    }
}
