package mx.ipn.escom.labreservas.controller;

import mx.ipn.escom.labreservas.model.Usuario;
import mx.ipn.escom.labreservas.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    public UsuarioController(UsuarioRepository usuarioRepository) { this.usuarioRepository = usuarioRepository; }

    @GetMapping
    public List<Usuario> listar() { return usuarioRepository.findAll(); }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuario u) {
        if (usuarioRepository.existsByCorreo(u.getCorreo())) {
            return ResponseEntity.badRequest().body("Correo ya registrado");
        }
        return ResponseEntity.ok(usuarioRepository.save(u));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Integer id) {
        return usuarioRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) { usuarioRepository.deleteById(id); }
}
