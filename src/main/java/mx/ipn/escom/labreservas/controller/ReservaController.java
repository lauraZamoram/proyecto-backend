package mx.ipn.escom.labreservas.controller;

import mx.ipn.escom.labreservas.model.Reserva;
import mx.ipn.escom.labreservas.repository.ReservaRepository;
import mx.ipn.escom.labreservas.service.ReservaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final ReservaRepository reservaRepository;

    public ReservaController(ReservaService reservaService, ReservaRepository reservaRepository) {
        this.reservaService = reservaService;
        this.reservaRepository = reservaRepository;
    }

    @PostMapping
    public ResponseEntity<Reserva> crear(@RequestBody Reserva r) {
        Reserva saved = reservaService.crearReserva(r);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtener(@PathVariable Integer id) {
        return reservaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/reporte")
    public ResponseEntity<byte[]> descargarReporte(@PathVariable Integer id) throws IOException {
        byte[] pdf = reservaService.generarReporteReservaPdf(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reserva_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) { reservaRepository.deleteById(id); }
}
