package Model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GestorArchivos {
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<Curso> cursos = new ArrayList<>(); // Cambiado a CamelCase
    private static ArrayList<Inscripcion> inscripciones = new ArrayList<>(); // Cambiado a CamelCase
    private static ArrayList<Calificacion> calificaciones = new ArrayList<>(); // Cambiado a CamelCase

    private static final String RUTA_USUARIOS = "usuarios.json";
    private static final String RUTA_CURSOS = "cursos.json";
    private static final String RUTA_INSCRIPCIONES = "inscripciones.json"; // Nueva ruta
    private static final String RUTA_CALIFICACIONES = "calificaciones.json"; // Nueva ruta

    // --- Métodos de inicialización y reconstrucción ---
    public static void inicializarDatos() {
        System.out.println("Inicializando GestorArchivos...");

        usuarios.clear();
        usuarios.addAll(leerUsuarios());
        System.out.println("Usuarios cargados: " + usuarios.size());

        cursos.clear();
        cursos.addAll(leerCursos());
        System.out.println("Cursos cargados: " + cursos.size());

        inscripciones.clear();
        inscripciones.addAll(leerInscripciones()); // Cargar inscripciones
        System.out.println("Inscripciones cargadas: " + inscripciones.size());

        calificaciones.clear();
        calificaciones.addAll(leerCalificaciones()); // Cargar calificaciones
        System.out.println("Calificaciones cargadas: " + calificaciones.size());

        reconstruirRelaciones(); // Reconstruir todas las referencias entre objetos
    }

    // Método para generar IDs de Usuario
    public static String generarId(String tipo) {
        Random rand = new Random();
        int num = rand.nextInt(9000) + 1000;
        return String.valueOf(num);
    }

    // Método para generar IDs de Inscripcion
    public static int generarIdInscripcion() {
        Random rand = new Random();
        int id = rand.nextInt(99999) + 10000; // IDs de 5 dígitos
        // Asegurar que el ID sea único (simple para el TP)
        int finalId = id;
        while (inscripciones.stream().anyMatch(i -> i.getIdInscripcion() == finalId)) {
            id = rand.nextInt(99999) + 10000;
        }
        return id;
    }

    // --- Métodos de Persistencia ---

    public static void guardarUsuarios(List<Usuario> usuariosAGuardar) {
        try (FileWriter writer = new FileWriter(RUTA_USUARIOS)) {
            for (Usuario u : usuariosAGuardar) {
                String json = String.format(
                        "{\"id\":\"%s\",\"tipo\":\"%s\",\"nombre\":\"%s\",\"correo\":\"%s\",\"contrasena\":\"%s\",\"fechaRegistro\":\"%s\"}\n",
                        u.getId(), u.getTipo(), u.getNombre(), u.getCorreo(), u.getContrasena(), u.getFechaRegistro().toString()
                );
                writer.write(json);
            }
            System.out.println("Usuarios guardados con éxito en " + RUTA_USUARIOS);
        } catch (IOException e) {
            System.out.println("Error al guardar los usuarios: " + e.getMessage());
        }
    }

    public static void guardarUnSoloUsuario(String tipo, String nombre, String correo, String contrasena, String id) {
        int idInt = Integer.parseInt(id);
        Usuario nuevoUsuario;
        if ("alumno".equalsIgnoreCase(tipo)) {
            nuevoUsuario = new Alumno(nombre, idInt, correo, contrasena, LocalDate.now(), idInt); // Cambiado a CamelCase
        } else if ("docente".equalsIgnoreCase(tipo)) {
            nuevoUsuario = new Docente(nombre, idInt, correo, contrasena, LocalDate.now()); // Cambiado a CamelCase
        } else {
            System.err.println("Tipo de usuario desconocido: " + tipo);
            return;
        }
        addUsuario(nuevoUsuario);
        guardarUsuarios(usuarios);
    }

    public static List<Usuario> leerUsuarios() {
        ArrayList<String> lineasJson = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_USUARIOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineasJson.add(linea);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de usuarios (" + RUTA_USUARIOS + "), se creará al guardar un usuario.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }

        ArrayList<Usuario> usuariosCargados = new ArrayList<>();
        for (String jsonLine : lineasJson) {
            Usuario u = parseUsuarioFromJson(jsonLine);
            if (u != null) {
                usuariosCargados.add(u);
            }
        }
        return usuariosCargados;
    }

    public static void guardarCursos(List<Curso> cursosAGuardar) { // Cambiado a CamelCase
        try (FileWriter writer = new FileWriter(RUTA_CURSOS)) {
            for (Curso c : cursosAGuardar) { // Cambiado a CamelCase
                String json = String.format(
                        "{\"codigo\":%d,\"nombre\":\"%s\",\"cuposMax\":%d,\"docenteId\":%d}\n",
                        c.getCodigo(), c.getNombre(), c.getCuposMax(), c.getDocente().getId()
                );
                writer.write(json);
            }
            System.out.println("Cursos guardados con éxito en " + RUTA_CURSOS);
        } catch (IOException e) {
            System.out.println("Error al guardar los cursos: " + e.getMessage());
        }
    }

    public static List<Curso> leerCursos() { // Cambiado a CamelCase
        ArrayList<Curso> cursosCargados = new ArrayList<>(); // Cambiado a CamelCase
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_CURSOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Pattern codigoPattern = Pattern.compile("\"codigo\":(\\d+)");
                Pattern nombrePattern = Pattern.compile("\"nombre\":\"([^\"]+)\"");
                Pattern cuposPattern = Pattern.compile("\"cuposMax\":(\\d+)");
                Pattern docenteIdPattern = Pattern.compile("\"docenteId\":(\\d+)");

                Matcher mCodigo = codigoPattern.matcher(linea);
                Matcher mNombre = nombrePattern.matcher(linea);
                Matcher mCupos = cuposPattern.matcher(linea);
                Matcher mDocenteId = docenteIdPattern.matcher(linea);

                if (mCodigo.find() && mNombre.find() && mCupos.find() && mDocenteId.find()) {
                    int codigo = Integer.parseInt(mCodigo.group(1));
                    String nombre = mNombre.group(1);
                    int cuposMax = Integer.parseInt(mCupos.group(1));
                    int docenteId = Integer.parseInt(mDocenteId.group(1));

                    Optional<Docente> docenteOpt = usuarios.stream() // Cambiado a CamelCase
                            .filter(u -> u instanceof Docente && u.getId() == docenteId) // Cambiado a CamelCase
                            .map(u -> (Docente)u) // Cambiado a CamelCase
                            .findFirst();
                    if (docenteOpt.isPresent()) {
                        Curso c = new Curso(codigo, nombre, cuposMax, docenteOpt.get()); // Cambiado a CamelCase
                        cursosCargados.add(c);
                    } else {
                        System.err.println("Advertencia: Docente con ID " + docenteId + " no encontrado para el curso " + nombre);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de cursos (" + RUTA_CURSOS + "), se creará al guardar un curso.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de cursos: " + e.getMessage());
        }
        return cursosCargados;
    }

    // Nuevo: Guardar Inscripciones
    public static void guardarInscripciones(List<Inscripcion> inscripcionesAGuardar) { // Cambiado a CamelCase
        try (FileWriter writer = new FileWriter(RUTA_INSCRIPCIONES)) {
            for (Inscripcion i : inscripcionesAGuardar) { // Cambiado a CamelCase
                String json = String.format(
                        "{\"idInscripcion\":%d,\"estado\":%b,\"alumnoId\":%d,\"cursoCodigo\":%d,\"fechaInscripcion\":\"%s\"}\n",
                        i.getIdInscripcion(), i.isEstado(), i.getAlumnoId(), i.getCursoCodigo(), i.getFechaInscripcion().toString()
                );
                writer.write(json);
            }
            System.out.println("Inscripciones guardadas con éxito en " + RUTA_INSCRIPCIONES);
        } catch (IOException e) {
            System.out.println("Error al guardar las inscripciones: " + e.getMessage());
        }
    }

    // Nuevo: Leer Inscripciones
    public static List<Inscripcion> leerInscripciones() { // Cambiado a CamelCase
        ArrayList<Inscripcion> inscripcionesCargadas = new ArrayList<>(); // Cambiado a CamelCase
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_INSCRIPCIONES))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Pattern idInscripcionPattern = Pattern.compile("\"idInscripcion\":(\\d+)");
                Pattern estadoPattern = Pattern.compile("\"estado\":(true|false)");
                Pattern alumnoIdPattern = Pattern.compile("\"alumnoId\":(\\d+)");
                Pattern cursoCodigoPattern = Pattern.compile("\"cursoCodigo\":(\\d+)");
                Pattern fechaInscripcionPattern = Pattern.compile("\"fechaInscripcion\":\"([^\"]+)\"");

                Matcher mIdInscripcion = idInscripcionPattern.matcher(linea);
                Matcher mEstado = estadoPattern.matcher(linea);
                Matcher mAlumnoId = alumnoIdPattern.matcher(linea);
                Matcher mCursoCodigo = cursoCodigoPattern.matcher(linea);
                Matcher mFechaInscripcion = fechaInscripcionPattern.matcher(linea);

                if (mIdInscripcion.find() && mEstado.find() && mAlumnoId.find() && mCursoCodigo.find() && mFechaInscripcion.find()) {
                    int idInscripcion = Integer.parseInt(mIdInscripcion.group(1));
                    boolean estado = Boolean.parseBoolean(mEstado.group(1));
                    int alumnoId = Integer.parseInt(mAlumnoId.group(1));
                    int cursoCodigo = Integer.parseInt(mCursoCodigo.group(1));
                    LocalDate fechaInscripcion = LocalDate.parse(mFechaInscripcion.group(1));

                    inscripcionesCargadas.add(new Inscripcion(idInscripcion, estado, alumnoId, cursoCodigo, fechaInscripcion)); // Cambiado a CamelCase
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de inscripciones (" + RUTA_INSCRIPCIONES + "), se creará al guardar una inscripción.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de inscripciones: " + e.getMessage());
        }
        return inscripcionesCargadas;
    }

    // Nuevo: Guardar Calificaciones
    public static void guardarCalificaciones(List<Calificacion> calificacionesAGuardar) { // Cambiado a CamelCase
        try (FileWriter writer = new FileWriter(RUTA_CALIFICACIONES)) {
            for (Calificacion c : calificacionesAGuardar) { // Cambiado a CamelCase
                String json = String.format(
                        "{\"alumnoId\":%d,\"cursoCodigo\":%d,\"nota\":%.2f}\n",
                        c.getAlumnoId(), c.getCursoCodigo(), c.getNota()
                );
                writer.write(json);
            }
            System.out.println("Calificaciones guardadas con éxito en " + RUTA_CALIFICACIONES);
        } catch (IOException e) {
            System.out.println("Error al guardar las calificaciones: " + e.getMessage());
        }
    }

    // Nuevo: Leer Calificaciones
    public static List<Calificacion> leerCalificaciones() { // Cambiado a CamelCase
        ArrayList<Calificacion> calificacionesCargadas = new ArrayList<>(); // Cambiado a CamelCase
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_CALIFICACIONES))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Pattern alumnoIdPattern = Pattern.compile("\"alumnoId\":(\\d+)");
                Pattern cursoCodigoPattern = Pattern.compile("\"cursoCodigo\":(\\d+)");
                Pattern notaPattern = Pattern.compile("\"nota\":([\\d.]+)");

                Matcher mAlumnoId = alumnoIdPattern.matcher(linea);
                Matcher mCursoCodigo = cursoCodigoPattern.matcher(linea);
                Matcher mNota = notaPattern.matcher(linea);

                if (mAlumnoId.find() && mCursoCodigo.find() && mNota.find()) {
                    int alumnoId = Integer.parseInt(mAlumnoId.group(1));
                    int cursoCodigo = Integer.parseInt(mCursoCodigo.group(1));
                    double nota = Double.parseDouble(mNota.group(1));

                    calificacionesCargadas.add(new Calificacion(alumnoId, cursoCodigo, nota)); // Cambiado a CamelCase
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de calificaciones (" + RUTA_CALIFICACIONES + "), se creará al guardar una calificación.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de calificaciones: " + e.getMessage());
        }
        return calificacionesCargadas;
    }


    // --- Métodos de gestión en memoria (para la UI) ---

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    public static void addUsuario(Usuario u) {
        boolean exists = usuarios.stream().anyMatch(existingUser -> existingUser.getId() == u.getId());
        if (!exists) {
            usuarios.add(u);
            System.out.println("Usuario " + u.getNombre() + " (" + u.getTipo() + ") agregado a GestorArchivos (en memoria).");
        } else {
            System.out.println("Usuario " + u.getNombre() + " (" + u.getTipo() + ") ya existe en GestorArchivos (en memoria).");
        }
    }

    public static Optional<Usuario> findUsuarioById(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }

    public static List<Alumno> getAlumnos() { // Cambiado a CamelCase
        return usuarios.stream()
                .filter(u -> u instanceof Alumno) // Cambiado a CamelCase
                .map(u -> (Alumno) u) // Cambiado a CamelCase
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Docente> getDocentes() { // Cambiado a CamelCase
        return usuarios.stream()
                .filter(u -> u instanceof Docente) // Cambiado a CamelCase
                .map(u -> (Docente) u) // Cambiado a CamelCase
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Curso> getCursos() { // Cambiado a CamelCase
        return cursos;
    }

    public static void addCurso(Curso c) { // Cambiado a CamelCase
        boolean exists = cursos.stream().anyMatch(existingCurso -> existingCurso.getCodigo() == c.getCodigo());
        if (!exists) {
            cursos.add(c);
            System.out.println("Curso '" + c.getNombre() + "' agregado a GestorArchivos (en memoria).");
            guardarCursos(cursos);
        } else {
            System.out.println("Curso '" + c.getNombre() + "' ya existe en GestorArchivos (en memoria).");
        }
    }

    public static Optional<Curso> findCursoByCodigo(int codigo) { // Cambiado a CamelCase
        return cursos.stream()
                .filter(c -> c.getCodigo() == codigo)
                .findFirst();
    }

    public static List<Inscripcion> getInscripciones() { // Cambiado a CamelCase
        return inscripciones;
    }

    public static void addInscripcion(Inscripcion i) { // Cambiado a CamelCase
        inscripciones.add(i);
        System.out.println("Inscripción de " + i.getAlumno().getNombre() + " en " + i.getCurso().getNombre() + " agregada a GestorArchivos (en memoria).");
        guardarInscripciones(inscripciones); // Guardar inscripciones
    }

    public static List<Calificacion> getCalificaciones() { // Cambiado a CamelCase
        return calificaciones;
    }

    public static void addCalificacion(Calificacion c) { // Cambiado a CamelCase
        calificaciones.add(c);
        System.out.println("Calificación de " + c.getAlumno().getNombre() + " en " + c.getCurso().getNombre() + " agregada a GestorArchivos (en memoria).");
        guardarCalificaciones(calificaciones); // Guardar calificaciones
    }

    // --- Método para validar Login ---
    public static Usuario validarLogin(String nombre, String id, String tipo) {
        for (Usuario u : usuarios) {
            String usuarioIdString = String.valueOf(u.getId());
            if (u.getNombre().equals(nombre) && usuarioIdString.equals(id) && u.getTipo().equalsIgnoreCase(tipo)) {
                return u;
            }
        }
        return null;
    }

    // --- Métodos auxiliares internos ---

    private static Usuario parseUsuarioFromJson(String jsonLine) {
        Pattern idPattern = Pattern.compile("\"id\":\"([^\"]+)\"");
        Pattern tipoPattern = Pattern.compile("\"tipo\":\"([^\"]+)\"");
        Pattern nombrePattern = Pattern.compile("\"nombre\":\"([^\"]+)\"");
        Pattern correoPattern = Pattern.compile("\"correo\":\"([^\"]+)\"");
        Pattern contrasenaPattern = Pattern.compile("\"contrasena\":\"([^\"]+)\"");
        Pattern fechaRegistroPattern = Pattern.compile("\"fechaRegistro\":\"([^\"]+)\"");

        Matcher idMatcher = idPattern.matcher(jsonLine);
        Matcher tipoMatcher = tipoPattern.matcher(jsonLine);
        Matcher nombreMatcher = nombrePattern.matcher(jsonLine);
        Matcher correoMatcher = correoPattern.matcher(jsonLine);
        Matcher contrasenaMatcher = contrasenaPattern.matcher(jsonLine);
        Matcher fechaRegistroMatcher = fechaRegistroPattern.matcher(jsonLine);

        String idStr = idMatcher.find() ? idMatcher.group(1) : null;
        String tipo = tipoMatcher.find() ? tipoMatcher.group(1) : null;
        String nombre = nombreMatcher.find() ? nombreMatcher.group(1) : null;
        String correo = correoMatcher.find() ? correoMatcher.group(1) : null;
        String contrasena = contrasenaMatcher.find() ? contrasenaMatcher.group(1) : null;
        LocalDate fechaRegistro = fechaRegistroMatcher.find() ? LocalDate.parse(fechaRegistroMatcher.group(1)) : LocalDate.now();

        if (idStr == null || tipo == null || nombre == null || correo == null || contrasena == null) {
            System.err.println("Error al parsear línea JSON: faltan campos esenciales. Línea: " + jsonLine);
            return null;
        }

        int idInt = 0;
        try {
            idInt = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            System.err.println("Error: ID '" + idStr + "' no es un número. Asignando 0. Línea: " + jsonLine);
            return null;
        }

        if ("alumno".equalsIgnoreCase(tipo)) {
            return new Alumno(nombre, idInt, correo, contrasena, fechaRegistro, idInt); // Cambiado a CamelCase
        } else if ("docente".equalsIgnoreCase(tipo)) {
            return new Docente(nombre, idInt, correo, contrasena, fechaRegistro); // Cambiado a CamelCase
        }
        return null;
    }

    private static void reconstruirRelaciones() {
        // 1. Limpiar relaciones existentes en todos los objetos
        for (Usuario u : usuarios) {
            if (u instanceof Alumno) { // Cambiado a CamelCase
                ((Alumno) u).getHistorialCursos().clear(); // Cambiado a CamelCase
                ((Alumno) u).getNotas().clear(); // Cambiado a CamelCase
            } else if (u instanceof Docente) { // Cambiado a CamelCase
                ((Docente) u).getCursosDictados().clear(); // Cambiado a CamelCase
            }
        }
        for (Curso c : cursos) { // Cambiado a CamelCase
            c.getInscriptos().clear();
            c.getCalificacionesCurso().clear();
        }

        // 2. Reconstruir cursos a docentes (ya se hace en leerCursos, pero aseguramos)
        for (Curso c : cursos) { // Cambiado a CamelCase
            if (c.getDocente() != null) {
                Optional<Docente> docenteEnMemoria = usuarios.stream() // Cambiado a CamelCase
                        .filter(u -> u instanceof Docente && u.getId() == c.getDocente().getId()) // Cambiado a CamelCase
                        .map(u -> (Docente) u) // Cambiado a CamelCase
                        .findFirst();
                docenteEnMemoria.ifPresent(d -> {
                    if (!d.getCursosDictados().contains(c)) {
                        d.dictarCurso(c);
                    }
                });
            }
        }

        // 3. Reconstruir Inscripciones
        for (Inscripcion insc : inscripciones) { // Cambiado a CamelCase
            Optional<Alumno> alumnoOpt = usuarios.stream() // Cambiado a CamelCase
                    .filter(u -> u instanceof Alumno && u.getId() == insc.getAlumnoId()) // Cambiado a CamelCase
                    .map(u -> (Alumno) u) // Cambiado a CamelCase
                    .findFirst();
            Optional<Curso> cursoOpt = cursos.stream() // Cambiado a CamelCase
                    .filter(c -> c.getCodigo() == insc.getCursoCodigo()) // Cambiado a CamelCase
                    .findFirst();

            if (alumnoOpt.isPresent() && cursoOpt.isPresent()) {
                Alumno alumno = alumnoOpt.get(); // Cambiado a CamelCase
                Curso curso = cursoOpt.get(); // Cambiado a CamelCase
                insc.setAlumno(alumno);
                insc.setCurso(curso);
                alumno.inscribirseEnCurso(insc);
                curso.agregarInscripcion(insc);
            } else {
                System.err.println("Advertencia: No se pudo reconstruir inscripción ID " + insc.getIdInscripcion() + ". Alumno o Curso no encontrados.");
            }
        }

        // 4. Reconstruir Calificaciones
        for (Calificacion cal : calificaciones) { // Cambiado a CamelCase
            Optional<Alumno> alumnoOpt = usuarios.stream() // Cambiado a CamelCase
                    .filter(u -> u instanceof Alumno && u.getId() == cal.getAlumnoId()) // Cambiado a CamelCase
                    .map(u -> (Alumno) u) // Cambiado a CamelCase
                    .findFirst();
            Optional<Curso> cursoOpt = cursos.stream() // Cambiado a CamelCase
                    .filter(c -> c.getCodigo() == cal.getCursoCodigo()) // Cambiado a CamelCase
                    .findFirst();

            if (alumnoOpt.isPresent() && cursoOpt.isPresent()) {
                Alumno alumno = alumnoOpt.get(); // Cambiado a CamelCase
                Curso curso = cursoOpt.get(); // Cambiado a CamelCase
                cal.setAlumno(alumno);
                cal.setCurso(curso);
                alumno.agregarNota(cal);
                curso.addCalificacion(cal);
            } else {
                System.err.println("Advertencia: No se pudo reconstruir calificación para Alumno ID " + cal.getAlumnoId() + ", Curso ID " + cal.getCursoCodigo() + ". Alumno o Curso no encontrados.");
            }
        }
    }
}