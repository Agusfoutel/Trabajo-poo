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

    // ¡NUEVOS ATRIBUTOS PARA EL PAGO!
    private MetodoPago metodoPago;
    private double montoPagado;
    private int cuotas; // Cuotas, 1 por defecto para efectivo/débito

    public Inscripcion(boolean estado, Curso curso, Alumno alumno, LocalDate fechaInscripcion,
                       MetodoPago metodoPago, double montoPagado, int cuotas) { // ¡Constructor Actualizado!
        this.estado = estado;
        this.curso = curso;
        this.alumno = alumno;
        this.fechaInscripcion = fechaInscripcion;
        // Generar un ID simple para la inscripción
        this.idInscripcion = GestorArchivos.generarIdInscripcion();

        // Almacenar IDs para la persistencia
        this.alumnoId = alumno.getId();
        this.cursoCodigo = curso.getCodigo();

        // ¡Inicializar nuevos atributos de pago!
        this.metodoPago = metodoPago;
        this.montoPagado = montoPagado;
        this.cuotas = cuotas;
    }

    // Constructor para cargar desde JSON (¡ACTUALIZADO!)
    public Inscripcion(int idInscripcion, boolean estado, int alumnoId, int cursoCodigo, LocalDate fechaInscripcion,
                       MetodoPago metodoPago, double montoPagado, int cuotas) {
        this.idInscripcion = idInscripcion;
        this.estado = estado;
        this.alumnoId = alumnoId;
        this.cursoCodigo = cursoCodigo;
        this.fechaInscripcion = fechaInscripcion;
        // Los objetos Alumno y Curso se asignarán durante la reconstrucción
        this.alumno = null;
        this.curso = null;

        // ¡Inicializar nuevos atributos de pago!
        this.metodoPago = metodoPago;
        this.montoPagado = montoPagado;
        this.cuotas = cuotas;
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

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public int getCuotas() {
        return cuotas;
    }
}