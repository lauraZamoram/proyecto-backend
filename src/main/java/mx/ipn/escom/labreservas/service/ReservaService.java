package mx.ipn.escom.labreservas.service;

import mx.ipn.escom.labreservas.model.Laboratorio;
import mx.ipn.escom.labreservas.model.Reserva;
import mx.ipn.escom.labreservas.model.Usuario;
import mx.ipn.escom.labreservas.repository.LaboratorioRepository;
import mx.ipn.escom.labreservas.repository.ReservaRepository;
import mx.ipn.escom.labreservas.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.core.io.ByteArrayResource;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LaboratorioRepository laboratorioRepository;
    private final JavaMailSender mailSender;

    public ReservaService(
        ReservaRepository reservaRepository,
        UsuarioRepository usuarioRepository,
        LaboratorioRepository laboratorioRepository,
        JavaMailSender mailSender
    ) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.laboratorioRepository = laboratorioRepository;
        this.mailSender = mailSender;
    }

    public Reserva crearReserva(Reserva r) {

        Usuario usuario = usuarioRepository.findById(
            r.getUsuario().getIdUsuario()
        ).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Laboratorio laboratorio = laboratorioRepository.findById(
            r.getLaboratorio().getIdLaboratorio()
        ).orElseThrow(() -> new RuntimeException("Laboratorio no encontrado"));

        r.setUsuario(usuario);
        r.setLaboratorio(laboratorio);

        Reserva saved = reservaRepository.save(r);

        try {
            byte[] pdfBytes = generarReporteReservaPdf(saved.getIdReserva());
            System.out.println("PDF generado, tamaño bytes: " + pdfBytes.length);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    "UTF-8"
                );

            helper.setFrom("lzamora@mstecs.com");
            helper.setTo(usuario.getCorreo());
            helper.setSubject("Confirmación de reserva #" + saved.getIdReserva());

            helper.setText(
                "Hola " + usuario.getNombre() + ",\n\n" +
                "Tu reserva fue registrada correctamente.\n\n" +
                "Laboratorio: " + laboratorio.getNombre() + "\n" +
                "Inicio: " + saved.getFechaHoraInicio() + "\n" +
                "Fin: " + saved.getFechaHoraFin() + "\n\n" +
                "Reporte de la reserva en PDF: ",
                false
            );

            helper.addAttachment(
                "reserva_" + saved.getIdReserva() + ".pdf",
                new ByteArrayResource(pdfBytes)
            );

            mailSender.send(message);

        } catch (Exception e) {
            System.err.println("Error enviando correo con PDF: " + e.getMessage());
        }

        return saved;
    }

    // =====================================================
    // GENERAR PDF DE RESERVA
    // =====================================================
    public byte[] generarReporteReservaPdf(Integer idReserva) throws IOException {

        Reserva r = reservaRepository.findById(idReserva)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        PDPageContentStream cs = new PDPageContentStream(doc, page);

        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 14);
        cs.newLineAtOffset(50, 700);
        cs.showText("Reporte de Reserva");
        cs.newLineAtOffset(0, -25);

        cs.setFont(PDType1Font.HELVETICA, 12);
        cs.showText("ID Reserva: " + r.getIdReserva());
        cs.newLineAtOffset(0, -20);

        cs.showText("Usuario: " + r.getUsuario().getNombre() + " " + r.getUsuario().getApellidos());
        cs.newLineAtOffset(0, -20);

        cs.showText("Correo: " + r.getUsuario().getCorreo());
        cs.newLineAtOffset(0, -20);

        cs.showText("Laboratorio: " + r.getLaboratorio().getNombre());
        cs.newLineAtOffset(0, -20);

        cs.showText("Inicio: " + r.getFechaHoraInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        cs.newLineAtOffset(0, -20);

        cs.showText("Fin: " + r.getFechaHoraFin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        cs.endText();
        cs.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        doc.save(baos);
        doc.close();

        return baos.toByteArray();
    }
}
