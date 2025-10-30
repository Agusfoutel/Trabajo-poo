package Model;

import java.util.ArrayList;

public class curso {
    private int codigo;
    private String nombre;
    private int cuposMax;
    private docente docente;
    private ArrayList<inscripcion> inscriptos;

    public curso(int codigo, String nombre, int cuposMax, docente docente, ArrayList<inscripcion> inscriptos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cuposMax = cuposMax;
        this.docente = docente;
        this.inscriptos = inscriptos;
    }

    public void VerAlumnos(){
        for (int i =0; i < this.inscriptos.size(); i++){
            System.out.println(this.inscriptos.get(i).getAlumno().getNombre());

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

    public docente getDocente() {
        return docente;
    }

    public ArrayList<inscripcion> getInscriptos() {
        return inscriptos;
    }
}

