package com.workcontrol.modelo;

public class InformesModelo {
    private Float cantidad_material;

    public InformesModelo () {

    }

    public InformesModelo(Float cantidad_material) {
        this.cantidad_material = cantidad_material;
    }

    public Float getCantidad_material() {
        return cantidad_material;
    }

    public void setCantidad_material(Float cantidad_material) {
        this.cantidad_material = cantidad_material;
    }

    @Override
    public String toString() {
        return "InformesModelo{" +
                "cantidad_material=" + cantidad_material +
                '}';
    }
}
