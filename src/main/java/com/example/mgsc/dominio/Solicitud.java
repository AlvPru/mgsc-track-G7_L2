package com.example.mgsc.dominio;
import java.util.Date;
public class Solicitud {
    private static int contadorId = 1;
    private int id;
    private String descripcion;
    private String estado;
    private Cliente clienteAsignado;
    private Tecnico tecnico;
    private Date fechaCreacion;
    private Date fechaCierre;


    public Solicitud(String descripcion,  Cliente cliente) {
        this.id = contadorId++;
        this.descripcion = descripcion;
        this.estado = "ABIERTA";
        this.clienteAsignado = cliente;
        this.tecnico = null;
        this.fechaCreacion = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getClienteAsignado() {
        return clienteAsignado;
    }

    public void setClienteAsignado(Cliente clienteAsignado) {
        this.clienteAsignado = clienteAsignado;
    }

    public Tecnico getTecnico() {
        if (tecnico == null) {
            throw new IllegalStateException("No se ha asignado un técnico a esta solicitud");
        }   
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Date getFechaCierre() {
        return fechaCierre;
    }
    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", clienteAsignado=" + clienteAsignado +
                ", tecnico=" + (tecnico != null ? tecnico.getNombre() : "No asignado") +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaCierre=" + fechaCierre +
                '}';
    }
}