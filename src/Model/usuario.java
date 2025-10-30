package Model;

import java.time.LocalDate;

public abstract class usuario {
    protected String nombre;
    protected int id;
    protected String correo;
    protected String contrasena;
    protected LocalDate fechaRegistro;

    public usuario(String nombre, int id, String correo, String contrasena, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() {
        return id;
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

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public boolean autenticar(String correo, String contrasena){
        return this.correo.equals(correo) && this.contrasena.equals(contrasena);
    }
}
