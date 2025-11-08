package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Alumno extends Usuario {
    private int legajo;
    private List<Inscripcion> historialCursos;
    private double promedio;
    private List<Calificacion> notas;

    public Alumno(String nombreAlumno, int id, String correo, String contrasena, LocalDate fechaRegistro, int legajo) {
        super(nombreAlumno, id, correo, contrasena, fechaRegistro, "alumno");
        this.legajo = legajo;
        this.promedio = 0.0;
        this.historialCursos = new ArrayList<>();
        this.notas = new ArrayList<>();
    }

    public void agregarNota(Calificacion nota){
        this.notas.add(nota);
        actualizarPromedio();
    }

    public List<Calificacion> getNotas(){
        return this.notas;
    }

    public void inscribirseEnCurso(Inscripcion inscripcion){
        this.historialCursos.add(inscripcion);
    }

    public void desinscribirseDeCurso(Inscripcion inscripcion){
        this.historialCursos.remove(inscripcion);
    }

    public int getLegajo() {
        return legajo;
    }

    public List<Inscripcion> getHistorialCursos() {
        return historialCursos;
    }

    public double getPromedio() {
        return promedio;
    }

    private void actualizarPromedio() {
        if (notas.isEmpty()) {
            this.promedio = 0.0;
            return;
        }
        double suma = 0;
        for (Calificacion nota : notas) {
            suma += nota.getNota();
        }
        this.promedio = suma / notas.size();
    }
}
