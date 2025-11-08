package Model;

import java.util.ArrayList;
import java.util.List; // Usamos List para las colecciones

public class Curso {
    private int codigo;
    private String nombre;
    private int cuposMax;
    private Docente docente;
    private List<Inscripcion> inscriptos;
    private List<Calificacion> calificacionesCurso;

    public Curso(int codigo, String nombre, int cuposMax, Docente docente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cuposMax = cuposMax;
        this.docente = docente;
        this.inscriptos = new ArrayList<>();
        this.calificacionesCurso = new ArrayList<>();
    }

    public void agregarInscripcion(Inscripcion insc) {
        if (inscriptos.size() < cuposMax) {
            this.inscriptos.add(insc);
            System.out.println("Alumno " + insc.getAlumno().getNombre() + " inscrito en curso " + this.nombre);
        } else {
            System.out.println("El curso " + this.nombre + " está lleno.");
        }
    }

    public void VerAlumnos(){
        System.out.println("Alumnos inscriptos en " + this.nombre + ":");
        if (this.inscriptos.isEmpty()) {
            System.out.println("No hay alumnos inscriptos.");
            return;
        }
        for (Inscripcion insc : this.inscriptos){
            System.out.println("- " + insc.getAlumno().getNombre() + " (ID: " + insc.getAlumno().getId() + ")");
        }
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCuposMax() {
        return cuposMax;
    }

    public Docente getDocente() {
        return docente;
    }

    public List<Inscripcion> getInscriptos() {
        return inscriptos;
    }

    public List<Calificacion> getCalificacionesCurso() {
        return calificacionesCurso;
    }

    public void addCalificacion(Calificacion cal) {
        this.calificacionesCurso.add(cal);
    }
}
