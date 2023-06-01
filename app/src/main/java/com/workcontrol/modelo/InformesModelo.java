package com.workcontrol.modelo;

public class InformesModelo {
    private Number cantidad_material;

    public InformesModelo () {

    }

    public InformesModelo(Number cantidad_material) {
        this.cantidad_material = cantidad_material;
    }

    public Number getCantidad_material() {
        return cantidad_material;
    }

    public void setCantidad_material(Number cantidad_material) {
        this.cantidad_material = cantidad_material;
    }

    @Override
    public String toString() {
        return "InformesModelo{" +
                "cantidad_material=" + cantidad_material +
                '}';
    }
}
