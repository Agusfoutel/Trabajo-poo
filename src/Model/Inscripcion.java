package Model;

import java.time.LocalDate;

public class Inscripcion { // Cambiado a CamelCase
    private int idInscripcion; // Nuevo para persistencia
    private boolean estado;
    private Curso curso; // Cambiado a CamelCase
    private Alumno alumno; // Cambiado a CamelCase
    private LocalDate fechaInscripcion;

    // Campos auxiliares para la persistencia (IDs en lugar de objetos completos)
    private int alumnoId;
    private int cursoCodigo;

    public Inscripcion(boolean estado, Curso curso, Alumno alumno, LocalDate fechaInscripcion) { // Cambiado a CamelCase
        this.estado = estado;
        this.curso = curso;
        this.alumno = alumno;
        this.fechaInscripcion = fechaInscripcion;
        // Generar un ID simple para la inscripción
        this.idInscripcion = GestorArchivos.generarIdInscripcion();

        // Almacenar IDs para la persistencia
        this.alumnoId = alumno.getId();
        this.cursoCodigo = curso.getCodigo();
    }

    // Constructor para cargar desde JSON
    public Inscripcion(int idInscripcion, boolean estado, int alumnoId, int cursoCodigo, LocalDate fechaInscripcion) {
        this.idInscripcion = idInscripcion;
        this.estado = estado;
        this.alumnoId = alumnoId;
        this.cursoCodigo = cursoCodigo;
        this.fechaInscripcion = fechaInscripcion;
        // Los objetos Alumno y Curso se asignarán durante la reconstrucción
        this.alumno = null;
        this.curso = null;
    }


    public int getIdInscripcion() {
        return idInscripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) { // Setter para reconstrucción
        this.curso = curso;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) { // Setter para reconstrucción
        this.alumno = alumno;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public int getCursoCodigo() {
        return cursoCodigo;
    }
}
