package mx.ipn.escom.labreservas.controller;

import mx.ipn.escom.labreservas.model.Laboratorio;
import mx.ipn.escom.labreservas.repository.LaboratorioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/laboratorios")
public class LaboratorioController {
    private final LaboratorioRepository laboratorioRepository;
    public LaboratorioController(LaboratorioRepository laboratorioRepository) { this.laboratorioRepository = laboratorioRepository; }

    @GetMapping
    public List<Laboratorio> listar() { return laboratorioRepository.findAll(); }

    @PostMapping
    public Laboratorio crear(@RequestBody Laboratorio l) { return laboratorioRepository.save(l); }

    @GetMapping("/{id}")
    public Laboratorio obtener(@PathVariable Integer id) { return laboratorioRepository.findById(id).orElse(null); }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) { laboratorioRepository.deleteById(id); }
}
