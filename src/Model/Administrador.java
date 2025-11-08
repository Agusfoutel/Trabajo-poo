package Model;


import java.util.ArrayList; // antes tenías java.lang.reflect.Array que está mal

public class Administrador {
    private ArrayList<Usuario> usuarios;

    public Administrador() {
        this.usuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}
