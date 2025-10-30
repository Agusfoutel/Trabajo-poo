package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class alumno extends usuario {
    private int legajo;
    private String nombreAlumno;
    private ArrayList<inscripcion> historialCursos;
    private double promedio;
    private ArrayList<calificacion>notas;

    public alumno(String nombreAlumno, int legajo, ArrayList<inscripcion> historialCursos, double promedio,  String nombre, int id, String correo, String contrasena, LocalDate fechaRegistro) {
        super(nombre, id, correo, contrasena, fechaRegistro);
        this.legajo = legajo;
        this.promedio = promedio;
        this.nombre=nombreAlumno;
        this.historialCursos=new ArrayList<>();
        this.notas= new ArrayList<>();
    }

    public void agregarNota(calificacion nota){
        this.notas.add(nota);
    }

    public ArrayList<calificacion> getnotas(){
        return this.notas;
    }

    public void inscribirseEnCurso(inscripcion inscripcion){
        this.historialCursos.add(inscripcion);
    }

    public void desinscribirseDeCurso(inscripcion inscripcion){
        this.historialCursos.remove(inscripcion);
    }

    public int getLegajo() {
        return legajo;
    }

    public ArrayList<inscripcion> getHistorialCursos() {
        return historialCursos;
    }


    public String getNombre() {
        return nombre;
    }
}
