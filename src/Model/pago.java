package Model;

import java.time.LocalDate;

public abstract class pago {
    protected int id;
    protected double montoBase;
    protected LocalDate fechaPago;
    protected String formaDePago;

    public pago(int id, double montoBase, LocalDate fechaPago, String formaDePago) {
        this.id = id;
        this.montoBase = montoBase;
        this.fechaPago = fechaPago;
        this.formaDePago = formaDePago;
    }

    public abstract double calcularMontoFinal();
}
