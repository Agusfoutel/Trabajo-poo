package Model;

import java.time.LocalDate;

public class inscripcion {
    private boolean estado;
    private curso curso;
    private alumno alumno;
    private LocalDate fechaInscripcion;

    public inscripcion(boolean estado, curso curso, alumno alumno, LocalDate fechaInscripcion) {
        this.estado = estado;
        this.curso = curso;
        this.alumno = alumno;
        this.fechaInscripcion = fechaInscripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public curso getCurso() {
        return curso;
    }

    public alumno getAlumno() {
        return alumno;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }
}
