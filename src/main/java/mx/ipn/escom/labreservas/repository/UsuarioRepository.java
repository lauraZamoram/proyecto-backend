package mx.ipn.escom.labreservas.repository;

import mx.ipn.escom.labreservas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreo(String correo);
}
