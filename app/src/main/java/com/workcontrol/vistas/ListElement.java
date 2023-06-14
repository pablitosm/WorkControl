package com.workcontrol.vistas;

import java.util.Date;

public class ListElement {
    public String color;

    public String estado_trabajo;
    public Date fecha_trabajo;
    public String minimo_paladas;
    public String nombre_maquina;
    public String nombre_operario;

    public ListElement() {

    }

    public ListElement(String color, String estado_trabajo, Date fecha_trabajo, String minimo_paladas, String nombre_maquina, String nombre_operario) {
        this.color = color;
        this.estado_trabajo = estado_trabajo;
        this.fecha_trabajo = fecha_trabajo;
        this.minimo_paladas = minimo_paladas;
        this.nombre_maquina = nombre_maquina;
        this.nombre_operario = nombre_operario;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEstado_trabajo() {
        return estado_trabajo;
    }

    public void setEstado_trabajo(String estado_trabajo) {
        this.estado_trabajo = estado_trabajo;
    }

    public Date getFecha_trabajo() {
        return fecha_trabajo;
    }

    public void setFecha_trabajo(Date fecha_trabajo) {
        this.fecha_trabajo = fecha_trabajo;
    }

    public String getMinimo_paladas() {
        return minimo_paladas;
    }

    public void setMinimo_paladas(String minimo_paladas) {
        this.minimo_paladas = minimo_paladas;
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

    @Override
    public String toString() {
        return "ListElement{" +
                "color='" + color + '\'' +
                ", estado_trabajo='" + estado_trabajo + '\'' +
                ", fecha_trabajo=" + fecha_trabajo +
                ", minimo_paladas='" + minimo_paladas + '\'' +
                ", nombre_maquina='" + nombre_maquina + '\'' +
                ", nombre_operario='" + nombre_operario + '\'' +
                '}';
    }
}
