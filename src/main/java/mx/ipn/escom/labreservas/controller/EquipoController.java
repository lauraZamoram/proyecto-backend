package mx.ipn.escom.labreservas.controller;

import mx.ipn.escom.labreservas.model.Equipo;
import mx.ipn.escom.labreservas.repository.EquipoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/equipos")
public class EquipoController {
    private final EquipoRepository equipoRepository;
    public EquipoController(EquipoRepository equipoRepository) { this.equipoRepository = equipoRepository; }

    @GetMapping
    public List<Equipo> listar() { return equipoRepository.findAll(); }

    @PostMapping
    public Equipo crear(@RequestBody Equipo e) { return equipoRepository.save(e); }

    @GetMapping("/{id}")
    public Equipo obtener(@PathVariable Integer id) { return equipoRepository.findById(id).orElse(null); }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) { equipoRepository.deleteById(id); }
}
