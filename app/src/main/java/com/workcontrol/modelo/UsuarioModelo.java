package com.workcontrol.modelo;

public class UsuarioModelo {

    private String dni;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasegna;

    public UsuarioModelo () {

    }

    public UsuarioModelo(String dni, String nombre, String apellido, String correo, String contrasegna) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasegna = contrasegna;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasegna() {
        return contrasegna;
    }

    public void setContrasegna(String contrasegna) {
        this.contrasegna = contrasegna;
    }

    @Override
    public String toString() {
        return "Usuario{" + "dni='" + dni + '\'' + ", nombre='" + nombre + '\'' + ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' + ", contrasegna='" + contrasegna + '\'' + '}';
    }
}
