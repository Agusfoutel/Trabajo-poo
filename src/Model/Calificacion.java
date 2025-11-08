package Model;

public class Calificacion { // Cambiado a CamelCase
    private Alumno alumno; // Cambiado a CamelCase
    private Curso curso; // Cambiado a CamelCase
    private double nota;

    // Campos auxiliares para la persistencia
    private int alumnoId;
    private int cursoCodigo;

    public Calificacion(Alumno alumno, Curso curso, double nota) { // Cambiado a CamelCase
        this.alumno = alumno;
        this.curso = curso;
        this.nota = nota;
        // Almacenar IDs para la persistencia
        this.alumnoId = alumno.getId();
        this.cursoCodigo = curso.getCodigo();
    }

    // Constructor para cargar desde JSON
    public Calificacion(int alumnoId, int cursoCodigo, double nota) {
        this.alumnoId = alumnoId;
        this.cursoCodigo = cursoCodigo;
        this.nota = nota;
        // Los objetos Alumno y Curso se asignarán durante la reconstrucción
        this.alumno = null;
        this.curso = null;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
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

    public int getAlumnoId() {
        return alumnoId;
    }

    public int getCursoCodigo() {
        return cursoCodigo;
    }
}