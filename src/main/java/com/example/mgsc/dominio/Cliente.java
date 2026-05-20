package com.example.mgsc.dominio;
 
public class Cliente extends Persona {
    private String email;
    private TipoCliente tipo;

    public Cliente(String nombre, String dni, String email, TipoCliente tipo) {
        super(nombre, dni);
        this.email = email;
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoCliente getTipo() {
        return tipo;
    }


    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public boolean esPremium() {
        return TipoCliente.PREMIUM.equals(this.tipo);
    }

    public void cambiarEmail(String nuevoEmail) {
        this.email = nuevoEmail;
    }

    public String getDescripcion() {
        return String.format("Cliente: %s, Email: %s, Tipo: %s", getNombre(), email, tipo);
    }

}