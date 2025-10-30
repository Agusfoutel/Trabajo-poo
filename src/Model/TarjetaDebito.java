package Model;

import java.time.LocalDate;

public class TarjetaDebito extends pago {
    private int nroTarjeta;
    private LocalDate fechaVencimiento;
    private int cvv;

    public TarjetaDebito(int id, double montoBase, LocalDate fechaPago, String formaDePago, int nroTarjeta, LocalDate fechaVencimiento, int cvv) {
        super(id, montoBase, fechaPago, formaDePago);
        this.nroTarjeta = nroTarjeta;
        this.fechaVencimiento = fechaVencimiento;
        this.cvv = cvv;
    }

    public double calcularMontoFinal(){
        return this.montoBase;
    }

    public void setCvv(int cvv) {
        if ( cvv > 99 && cvv<999) {
            throw new IllegalArgumentException("El cvv debe tener 3 digitos");
        }
        this.cvv = cvv;
    }
}
