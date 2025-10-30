package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class GestorJSON {

    private static final String RUTA_ARCHIVO = "usuarios.json";

    //Generar ID automático tanto para alumnos como para dococentes
    public static String generarId(String tipo) {
        Random rand = new Random();
        if (tipo.equalsIgnoreCase("alumno")) {
            // 4 letras mayúsculas aleatorias
            String letras = "";
            for (int i = 0; i < 4; i++) {
                letras += (char) (rand.nextInt(26) + 'A');
            }
            return letras;
        } else {
            // 4 números aleatorios
            int num = rand.nextInt(9000) + 1000;
            return String.valueOf(num);
        }
    }

    //Guardar usuario en JSON
    public static void guardarUsuario(String tipo, String nombre, String correo, String contrasena, String id) {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO, true)) {
            String json = String.format(
                    "{\"id\":\"%s\",\"tipo\":\"%s\",\"nombre\":\"%s\",\"correo\":\"%s\",\"contrasena\":\"%s\"}\n",
                    id, tipo, nombre, correo, contrasena
            );
            writer.write(json);
            System.out.println("Usuario guardado con éxito en " + RUTA_ARCHIVO);
        } catch (IOException e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
        }
    }

    //Leer todos los usuarios
    public static ArrayList<String> leerUsuarios() {
        ArrayList<String> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                usuarios.add(linea);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo, se creará al guardar un usuario.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return usuarios;
    }

    //Validar login por nombre, tipo e ID
    public static boolean validarLogin(String nombre, String id, String tipo) {
        for (String linea : leerUsuarios()) {
            if (linea.contains("\"nombre\":\"" + nombre + "\"")
                    && linea.contains("\"id\":\"" + id + "\"")
                    && linea.contains("\"tipo\":\"" + tipo + "\"")) {
                return true;
            }
        }
        return false;
    }

}
