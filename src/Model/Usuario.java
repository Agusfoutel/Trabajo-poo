package Model;

import java.time.LocalDate;

public abstract class Usuario implements IAutenticable{
    protected String nombre;
    protected int id;
    protected String correo;
    protected String contrasena;
    protected LocalDate fechaRegistro;
    protected String tipo;

    public Usuario(String nombre, int id, String correo, String contrasena, LocalDate fechaRegistro, String tipo) {
        this.nombre = nombre;
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fechaRegistro = fechaRegistro;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public boolean autenticar(String correo, String contrasena){
        return this.correo.equals(correo) && this.contrasena.equals(contrasena);
    }
}
