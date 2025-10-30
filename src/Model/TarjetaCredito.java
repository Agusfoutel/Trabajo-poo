package Model;

import java.time.LocalDate;

public class TarjetaCredito extends pago {
    private int nroTarjeta;
    private LocalDate fechaVencimiento;
    private int cvv;
    private int cuotas;

    public TarjetaCredito(int id, double montoBase, LocalDate fechaPago, String formaDePago, int nroTarjeta, LocalDate fechaVencimiento, int cvv, int cuotas) {
        super(id, montoBase, fechaPago, formaDePago);
        this.nroTarjeta = nroTarjeta;
        this.fechaVencimiento = fechaVencimiento;
        this.cvv = cvv;
        this.cuotas = cuotas;
    }

    public double calcularMontoFinal(){
        return this.montoBase + (montoBase/100*20);
    }
}
