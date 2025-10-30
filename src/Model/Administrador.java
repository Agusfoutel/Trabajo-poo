package Model;


import java.util.ArrayList; // antes tenías java.lang.reflect.Array que está mal

public class Administrador {
    private ArrayList<usuario> usuarios;

    public Administrador() {
        this.usuarios = new ArrayList<>();
    }

    public void agregarUsuario(usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void eliminarUsuario(usuario usuario) {
        this.usuarios.remove(usuario);
    }

    public ArrayList<usuario> getUsuarios() {
        return usuarios;
    }
}
