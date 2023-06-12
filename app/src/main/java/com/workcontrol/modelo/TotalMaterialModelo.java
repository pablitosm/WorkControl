package com.workcontrol.modelo;

public class TotalMaterialModelo {

    private Float cantidad_material;
    private Float fecha_extraccion;

    public TotalMaterialModelo() {

    }

    public TotalMaterialModelo(Float total_material, Float fecha_extaccion) {
        this.cantidad_material = total_material;
        this.fecha_extraccion = fecha_extaccion;
    }

    public Float getCantidad_material() {
        return cantidad_material;
    }

    public void setCantidad_material(Float cantidad_material) {
        this.cantidad_material = cantidad_material;
    }

    public Float getFecha_extraccion() {
        return fecha_extraccion;
    }

    public void setFecha_extraccion(Float fecha_extraccion) {
        this.fecha_extraccion = fecha_extraccion;
    }

    @Override
    public String toString() {
        return "TotalMaterialModelo{" +
                "cantidad_material=" + cantidad_material +
                ", fecha_extraccion=" + fecha_extraccion +
                '}';
    }
}
