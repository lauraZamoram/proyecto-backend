package mx.ipn.escom.labreservas.controller;

import mx.ipn.escom.labreservas.model.Rol;
import mx.ipn.escom.labreservas.repository.RolRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {
    private final RolRepository rolRepository;
    public RolController(RolRepository rolRepository) { this.rolRepository = rolRepository; }

    @GetMapping
    public List<Rol> listar() { return rolRepository.findAll(); }

    @PostMapping
    public Rol crear(@RequestBody Rol r) { return rolRepository.save(r); }

    @GetMapping("/{id}")
    public Rol obtener(@PathVariable Integer id) { return rolRepository.findById(id).orElse(null); }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) { rolRepository.deleteById(id); }
}
