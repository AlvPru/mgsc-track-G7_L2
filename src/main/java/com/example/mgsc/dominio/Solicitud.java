package com.example.mgsc.dominio;

public class Solicitud {
    private int id;
    private String descripcion;
    private String estado;
    private Cliente cliente;
    private Tecnico tecnico;

    public Solicitud(int id, String descripcion, String estado, Cliente cliente, Tecnico tecnico) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.cliente = cliente;
        this.tecnico = tecnico;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }
}