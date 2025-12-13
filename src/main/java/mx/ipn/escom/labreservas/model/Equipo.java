package mx.ipn.escom.labreservas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "equipo")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEquipo;

    @Column(nullable=false, length=30)
    private String estado;

    @Column(length=50)
    private String mac;

    @Column(length=100)
    private String numeroSerie;

    @Column(length=50)
    private String ip;

    @ManyToOne
    @JoinColumn(name = "laboratorio", nullable = false)
    private Laboratorio laboratorio;

    // getters y setters
    public Integer getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Integer idEquipo) { this.idEquipo = idEquipo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMac() { return mac; }
    public void setMac(String mac) { this.mac = mac; }
    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public Laboratorio getLaboratorio() { return laboratorio; }
    public void setLaboratorio(Laboratorio laboratorio) { this.laboratorio = laboratorio; }
}
