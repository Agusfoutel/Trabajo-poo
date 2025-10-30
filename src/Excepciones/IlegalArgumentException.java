package Excepciones;

public class IlegalArgumentException extends Exception{
    public IlegalArgumentException(String mensaje) {
        super("el cvv debe tener 3 digitos");
    }
}
