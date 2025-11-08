package Model;

public class Calificacion {
    private Alumno alumno;
    private Curso curso;
    private double nota;

    private int alumnoId;
    private int cursoCodigo;

    public Calificacion(Alumno alumno, Curso curso, double nota) {
        this.alumno = alumno;
        this.curso = curso;
        this.nota = nota;
        this.alumnoId = alumno.getId();
        this.cursoCodigo = curso.getCodigo();
    }

    public Calificacion(int alumnoId, int cursoCodigo, double nota) {
        this.alumnoId = alumnoId;
        this.cursoCodigo = cursoCodigo;
        this.nota = nota;
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

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public int getCursoCodigo() {
        return cursoCodigo;
    }
}