package mx.ipn.escom.labreservas.repository;

import mx.ipn.escom.labreservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}
