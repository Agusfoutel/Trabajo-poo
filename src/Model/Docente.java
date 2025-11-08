package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List; // Usamos List para las colecciones

public class Docente extends Usuario {
    private List<Curso> cursosDictados;

    public Docente(String nombre, int id, String correo, String contrasena, LocalDate fechaRegistro) {
        super(nombre, id, correo, contrasena, fechaRegistro, "docente");
        this.cursosDictados = new ArrayList<>();
    }

    public void dictarCurso(Curso curso){
        this.cursosDictados.add(curso);
    }

    public void calificarAlumno(int legajo, int codigoCurso, double notaValor){
        for(Curso curso: this.cursosDictados){
            if(curso.getCodigo() == codigoCurso){
                for (Inscripcion i : curso.getInscriptos()){
                    if(i.getAlumno().getLegajo() == legajo){
                        Calificacion nuevaNota = new Calificacion(i.getAlumno(), curso, notaValor);
                        i.getAlumno().agregarNota(nuevaNota);
                        curso.addCalificacion(nuevaNota); // Añadir al curso
                        GestorArchivos.addCalificacion(nuevaNota); // Añadir al gestor global en memoria
                        System.out.println("Nota " + notaValor + " asignada a alumno " + i.getAlumno().getNombre() + " en curso " + curso.getNombre());
                        return;
                    }
                }
            }
        }
        System.out.println("No se pudo asignar la nota. Alumno o curso no encontrado/inscrito.");
    }

    public double calcularPromedioAlumnoEnCursosDictados(int legajo){
        double sumaNotas=0;
        int countNotas = 0;
        for (Curso c: cursosDictados){
            for (Inscripcion i :c.getInscriptos()){
                if (i.getAlumno().getLegajo() == legajo){
                    for (Calificacion n: i.getAlumno().getNotas()){
                        if (n.getCurso().equals(c)) {
                            sumaNotas += n.getNota();
                            countNotas++;
                        }
                    }
                }
            }
        }
        return countNotas > 0 ? sumaNotas / countNotas : 0.0;
    }

    public List<Curso> getCursosDictados() {
        return cursosDictados;
    }
}
