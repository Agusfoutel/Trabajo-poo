package Model;

public class calificacion {
    private alumno alumno;
    private curso curso;
    private double nota;

    public calificacion(alumno alumno, curso curso, double nota) {
        this.alumno = alumno;
        this.curso = curso;
        this.nota = nota;
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }
    public curso getCurso() {
        return curso;
    }
    public void setCurso(curso curso) {
        this.curso = curso;
    }
    public alumno getAlumno() {
        return alumno;
    }
    public void setAlumno(alumno alumno) {
        this.alumno = alumno;
    }
}
