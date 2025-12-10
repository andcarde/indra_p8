package com.indra.p8.service;

import com.indra.p8.model.Lector;
import com.indra.p8.model.Multa;

import java.util.List;

public interface MultaService {

    void multar(Long lectorId, int dias);
    void pagarMulta(Long multaId);
    byte[] exportarMultasPdf();
}
