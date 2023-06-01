package com.workcontrol.modelo;

public class TurnosModelo {

    private int numero_cargas;
    private String nombre_maquina;
    private String nombre_operario;
    private String fecha_inicio;
    private String fecha_fin;
    private String turno;

    public TurnosModelo() {

    }

    public TurnosModelo(int numero_cargas, String nombre_maquina, String nombre_operario, String fecha_inicio, String fecha_fin, String turno) {
        this.numero_cargas = numero_cargas;
        this.nombre_maquina = nombre_maquina;
        this.nombre_operario = nombre_operario;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.turno = turno;
    }

    public int getNumero_cargas() {
        return numero_cargas;
    }

    public void setNumero_cargas(int numero_cargas) {
        this.numero_cargas = numero_cargas;
    }

    public String getNombre_maquina() {
        return nombre_maquina;
    }

    public void setNombre_maquina(String nombre_maquina) {
        this.nombre_maquina = nombre_maquina;
    }

    public String getNombre_operario() {
        return nombre_operario;
    }

    public void setNombre_operario(String nombre_operario) {
        this.nombre_operario = nombre_operario;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Turnos{" +
                "numero_cargas=" + numero_cargas +
                ", nombre_maquina='" + nombre_maquina + '\'' +
                ", nombre_operario='" + nombre_operario + '\'' +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_fin=" + fecha_fin +
                ", turno='" + turno + '\'' +
                '}';
    }
}