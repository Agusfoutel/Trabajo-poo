package Model;

import java.time.LocalDate;

public class Efectivo  extends pago{

    public Efectivo(int id, double montoBase, LocalDate fechaPago, String formaDePago) {
        super(id, montoBase, fechaPago, formaDePago);
    }

    public double calcularMontoFinal(){
        return this.montoBase-(montoBase/100*10);
    }
}
