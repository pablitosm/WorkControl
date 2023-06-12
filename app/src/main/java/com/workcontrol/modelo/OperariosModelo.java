package com.workcontrol.modelo;

public class OperariosModelo {

    private String fecha_contrato;
    private String nombre_maquina;
    private String nombre_operario;
    private String numero_empleado;
    private String puesto_trabajo;
    private Float salario;

    public OperariosModelo() {
    }

    public OperariosModelo(String fecha_contrato, String nombre_maquina, String nombre_operario, String numero_empleado, String puesto_trabajo, Float salario) {
        this.fecha_contrato = fecha_contrato;
        this.nombre_maquina = nombre_maquina;
        this.nombre_operario = nombre_operario;
        this.numero_empleado = numero_empleado;
        this.puesto_trabajo = puesto_trabajo;
        this.salario = salario;
    }

    public String getFecha_contrato() {
        return fecha_contrato;
    }

    public void setFecha_contrato(String fecha_contrato) {
        this.fecha_contrato = fecha_contrato;
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

    public String getNumero_empleado() {
        return numero_empleado;
    }

    public void setNumero_empleado(String numero_empleado) {
        this.numero_empleado = numero_empleado;
    }

    public String getPuesto_trabajo() {
        return puesto_trabajo;
    }

    public void setPuesto_trabajo(String puesto_trabajo) {
        this.puesto_trabajo = puesto_trabajo;
    }

    public Float getSalario() {
        return salario;
    }

    public void setSalario(Float salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "OperariosModelo{" +
                "fecha_contrato='" + fecha_contrato + '\'' +
                ", nombre_maquina='" + nombre_maquina + '\'' +
                ", nombre_operario='" + nombre_operario + '\'' +
                ", numero_empleado='" + numero_empleado + '\'' +
                ", puesto_trabajo='" + puesto_trabajo + '\'' +
                ", salario='" + salario + '\'' +
                '}';
    }
}
