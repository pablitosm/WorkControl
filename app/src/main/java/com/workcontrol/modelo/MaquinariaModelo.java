package com.workcontrol.modelo;

public class MaquinariaModelo {

    private String fecha_fabricacion;
    private String horas_uso;
    private String matricula;
    private String nombre_maquina;
    private String potencia;

    public MaquinariaModelo() {

    }

    public MaquinariaModelo(String fecha_fabricacion, String horas_uso, String matricula, String nombre_maquina, String potencia) {
        this.fecha_fabricacion = fecha_fabricacion;
        this.horas_uso = horas_uso;
        this.matricula = matricula;
        this.nombre_maquina = nombre_maquina;
        this.potencia = potencia;
    }

    public String getFecha_fabricacion() {
        return fecha_fabricacion;
    }

    public void setFecha_fabricacion(String fecha_fabricacion) {
        this.fecha_fabricacion = fecha_fabricacion;
    }

    public String getHoras_uso() {
        return horas_uso;
    }

    public void setHoras_uso(String horas_uso) {
        this.horas_uso = horas_uso;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre_maquina() {
        return nombre_maquina;
    }

    public void setNombre_maquina(String nombre_maquina) {
        this.nombre_maquina = nombre_maquina;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    @Override
    public String toString() {
        return "MaquinariaModelo{" +
                "fecha_fabricacion='" + fecha_fabricacion + '\'' +
                ", horas_uso='" + horas_uso + '\'' +
                ", matricula='" + matricula + '\'' +
                ", nombre_maquina='" + nombre_maquina + '\'' +
                ", potencia='" + potencia + '\'' +
                '}';
    }
}
