package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class docente extends usuario {
    private ArrayList<curso>cursosDictados;

    public docente(String nombre, int id, String correo, String contrasena, LocalDate fechaRegistro, ArrayList<curso> cursosDictados) {
        super(nombre, id, correo, contrasena, fechaRegistro);
        this.cursosDictados = new ArrayList<>();
    }

    public void dictarCurso(curso curso){
        this.cursosDictados.add(curso);
    }

    public void calificarAlumno(int legajo, int codigo, calificacion nota){
        for(curso curso: this.cursosDictados){
            for (inscripcion i :curso.getInscriptos()){
                if(i.getAlumno().getLegajo()==legajo){
                    i.getAlumno().agregarNota(nota);
                }
            }
        }
    }

    public double calcularPromedio(int legajo){
        double sumaNotas=0;
        ArrayList<calificacion> notas=new ArrayList<>();
        for (curso c: cursosDictados){
            for (inscripcion i :c.getInscriptos()){
                if (i.getAlumno().getLegajo() == legajo){
                    notas.addAll(i.getAlumno().getnotas());
                }
            }
        }
        for (calificacion n: notas){
            sumaNotas+=n.getNota();
            }
        return sumaNotas/notas.size();
    }
}
