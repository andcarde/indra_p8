package com.indra.p8.service;

import com.indra.p8.model.EstadoMulta;
import com.indra.p8.model.Lector;
import com.indra.p8.model.Multa;
import com.indra.p8.repository.LectorRepository;
import com.indra.p8.repository.MultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class MultaServiceImp implements MultaService {

    @Autowired
    private MultaRepository multaRepository;
    @Autowired
    private LectorRepository lectorRepository;

    private final float montoPorDia = 2;

    @Override
    public void multar(Lector lector, int dias) {
        Multa multa = new Multa();
        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.now().plusDays(dias);
        float retraso = fin.toEpochDay() - inicio.toEpochDay();
        multa.setFInicio(inicio);
        multa.setFFin(fin);
        multa.setMonto(retraso * montoPorDia);
        multa.setEstado(EstadoMulta.PENDIENTE);
        multa.setLector(lector);
        multaRepository.save(multa);

        lector.setMulta(multa);
        lectorRepository.save(lector);
    }

    @Override
    public void pagarMulta(Long multaId) {
        Multa multa = multaRepository.findById(multaId).orElseThrow();
        multa.setEstado(EstadoMulta.PAGADA);
        multaRepository.save(multa);
    }

    @Override
    public byte[] exportarMultasPdf() {
        List<Multa> multas = multaRepository.findAll();
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            Document doc = new Document();
            PdfWriter.getInstance(doc, bos);
            doc.open();

            Font titleFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            doc.add(new Paragraph("Listado de Multas", titleFont));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Lector");
            table.addCell("Fecha Inicio");
            table.addCell("Fecha Fin");
            table.addCell("Días Atraso");
            table.addCell("Monto (€)");
            table.addCell("Estado");

            for (Multa m : multas) {
                table.addCell(String.valueOf(m.getId()));
                table.addCell(m.getLector().getNombre());
                table.addCell(String.valueOf(m.getFInicio()));
                table.addCell(String.valueOf(m.getFFin()));
                table.addCell(String.valueOf(m.getFFin().toEpochDay() - m.getFInicio().toEpochDay()));
                table.addCell(String.valueOf(m.getMonto()));
                table.addCell(m.getEstado().name());
            }

            doc.add(table);
            doc.close();
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error exportando PDF de multas", e);
        }
    }
}
