package Model;

import java.time.LocalDate;

public class Inscripcion {
    private int idInscripcion;
    private boolean estado;
    private Curso curso;
    private Alumno alumno;
    private LocalDate fechaInscripcion;

    private int alumnoId;
    private int cursoCodigo;

    private MetodoPago metodoPago;
    private double montoPagado;
    private int cuotas;

    public Inscripcion(boolean estado, Curso curso, Alumno alumno, LocalDate fechaInscripcion,
                       MetodoPago metodoPago, double montoPagado, int cuotas) {
        this.estado = estado;
        this.curso = curso;
        this.alumno = alumno;
        this.fechaInscripcion = fechaInscripcion;
        this.idInscripcion = GestorArchivos.generarIdInscripcion();

        this.alumnoId = alumno.getId();
        this.cursoCodigo = curso.getCodigo();

        this.metodoPago = metodoPago;
        this.montoPagado = montoPagado;
        this.cuotas = cuotas;
    }

    public Inscripcion(int idInscripcion, boolean estado, int alumnoId, int cursoCodigo, LocalDate fechaInscripcion,
                       MetodoPago metodoPago, double montoPagado, int cuotas) {
        this.idInscripcion = idInscripcion;
        this.estado = estado;
        this.alumnoId = alumnoId;
        this.cursoCodigo = cursoCodigo;
        this.fechaInscripcion = fechaInscripcion;
        this.alumno = null;
        this.curso = null;

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

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
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