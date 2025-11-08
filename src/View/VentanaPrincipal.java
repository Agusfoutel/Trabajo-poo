package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Model.GestorArchivos; // Ahora GestorArchivos es el central
import Model.Usuario;
import Model.Alumno;
import Model.Docente;
import java.time.LocalDate;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private JPanel panelInicio, panelRegistro, panelLogin;
    private JButton btnRegistrarse, btnIniciar, btnVolverRegistro, btnAlumnoRegistro, btnDocenteRegistro, btnRegistrar;
    private JButton btnVolverLogin, btnAlumnoLogin, btnDocenteLogin, btnIngresarLogin;
    private JTextField txtNombreRegistro, txtCorreoRegistro;
    private JPasswordField txtContrasenaRegistro;
    private JTextField txtNombreLogin, txtIdLogin;
    private JLabel lblTitulo, lblSubtituloRegistro, lblMensajeRegistro, lblIdGenerado;
    private JLabel lblSubtituloLogin, lblMensajeLogin;
    private String tipoUsuarioRegistro = "";
    private String tipoUsuarioLogin = "";
    private boolean modoRegistro = true;

    public VentanaPrincipal() {
        setTitle("Plataforma de Cursos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Inicializar GestorArchivos al inicio de la aplicación
        GestorArchivos.inicializarDatos();

        crearPanelInicio();
        crearPanelRegistro();
        crearPanelLogin();

        add(panelInicio);
        setVisible(true);
    }

    private void crearPanelInicio() {
        panelInicio = new JPanel(null);
        panelInicio.setBackground(new Color(237, 231, 246));

        lblTitulo = new JLabel("Bienvenido a la Plataforma");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(81, 45, 168));
        lblTitulo.setBounds(200, 100, 400, 30);
        panelInicio.add(lblTitulo);

        btnRegistrarse = crearBoton("Registrarse", 240, 180);
        btnIniciar = crearBoton("Iniciar sesión", 240, 260);
        panelInicio.add(btnRegistrarse);
        panelInicio.add(btnIniciar);

        btnRegistrarse.addActionListener(this);
        btnIniciar.addActionListener(this);
    }

    private void crearPanelRegistro() {
        panelRegistro = new JPanel(null);
        panelRegistro.setBackground(new Color(237, 231, 246));

        btnVolverRegistro = crearBotonPequeno("Volver", 30, 20);
        panelRegistro.add(btnVolverRegistro);
        btnVolverRegistro.addActionListener(this);

        lblSubtituloRegistro = new JLabel("Seleccioná tu rol", SwingConstants.CENTER);
        lblSubtituloRegistro.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblSubtituloRegistro.setForeground(new Color(81, 45, 168));
        lblSubtituloRegistro.setBounds(200, 60, 300, 25);
        panelRegistro.add(lblSubtituloRegistro);

        btnAlumnoRegistro = crearBoton("Alumno", 180, 110);
        btnDocenteRegistro = crearBoton("Docente", 370, 110);
        panelRegistro.add(btnAlumnoRegistro);
        panelRegistro.add(btnDocenteRegistro);
        btnAlumnoRegistro.addActionListener(this);
        btnDocenteRegistro.addActionListener(this);

        txtNombreRegistro = crearCampo("Ingrese su nombre", 200, 180);
        txtCorreoRegistro = crearCampo("Ingrese su correo", 200, 230);
        txtContrasenaRegistro = new JPasswordField("Ingrese su contraseña");
        txtContrasenaRegistro.setBounds(200, 280, 300, 35);
        estilizarCampo(txtContrasenaRegistro);

        btnRegistrar = crearBotonGrande("Registrar", 250, 340);

        lblMensajeRegistro = new JLabel("", SwingConstants.CENTER);
        lblMensajeRegistro.setBounds(100, 400, 500, 30);
        lblMensajeRegistro.setForeground(new Color(81, 45, 168));

        lblIdGenerado = new JLabel("", SwingConstants.CENTER);
        lblIdGenerado.setBounds(100, 430, 500, 30);
        lblIdGenerado.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblIdGenerado.setForeground(new Color(106, 27, 154));

        panelRegistro.add(txtNombreRegistro);
        panelRegistro.add(txtCorreoRegistro);
        panelRegistro.add(txtContrasenaRegistro);
        panelRegistro.add(btnRegistrar);
        panelRegistro.add(lblMensajeRegistro);
        panelRegistro.add(lblIdGenerado);

        btnRegistrar.addActionListener(this);

        txtNombreRegistro.setVisible(false);
        txtCorreoRegistro.setVisible(false);
        txtContrasenaRegistro.setVisible(false);
        btnRegistrar.setVisible(false);
        lblMensajeRegistro.setVisible(false);
        lblIdGenerado.setVisible(false);
    }

    private void crearPanelLogin() {
        panelLogin = new JPanel(null);
        panelLogin.setBackground(new Color(237, 231, 246));

        btnVolverLogin = crearBotonPequeno("← Volver", 30, 20);
        panelLogin.add(btnVolverLogin);
        btnVolverLogin.addActionListener(this);

        lblSubtituloLogin = new JLabel("Seleccioná tu rol para ingresar", SwingConstants.CENTER);
        lblSubtituloLogin.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblSubtituloLogin.setForeground(new Color(81, 45, 168));
        lblSubtituloLogin.setBounds(200, 70, 300, 30);
        panelLogin.add(lblSubtituloLogin);

        btnAlumnoLogin = crearBoton("Alumno", 180, 130);
        btnDocenteLogin = crearBoton("Docente", 370, 130);
        panelLogin.add(btnAlumnoLogin);
        panelLogin.add(btnDocenteLogin);
        btnAlumnoLogin.addActionListener(this);
        btnDocenteLogin.addActionListener(this);

        txtNombreLogin = crearCampo("Ingrese su nombre", 200, 210);
        txtIdLogin = crearCampo("Ingrese su ID", 200, 260);
        btnIngresarLogin = crearBotonGrande("Ingresar", 250, 320);

        lblMensajeLogin = new JLabel("", SwingConstants.CENTER);
        lblMensajeLogin.setBounds(100, 380, 500, 30);
        lblMensajeLogin.setForeground(new Color(81, 45, 168));

        panelLogin.add(txtNombreLogin);
        panelLogin.add(txtIdLogin);
        panelLogin.add(btnIngresarLogin);
        panelLogin.add(lblMensajeLogin);

        btnIngresarLogin.addActionListener(this);

        txtNombreLogin.setVisible(false);
        txtIdLogin.setVisible(false);
        btnIngresarLogin.setVisible(false);
        lblMensajeLogin.setVisible(false);
    }

    // Estilos - Mismos que los tuyos
    private JButton crearBoton(String texto, int x, int y) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, 150, 60);
        estilizarBoton(boton);
        return boton;
    }

    private JButton crearBotonGrande(String texto, int x, int y) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, 200, 45);
        estilizarBoton(boton);
        return boton;
    }

    private JButton crearBotonPequeno(String texto, int x, int y) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, 100, 30);
        boton.setBackground(new Color(206, 147, 216));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setBorder(BorderFactory.createLineBorder(new Color(186, 104, 200), 1, true));
        boton.setFocusPainted(false);
        return boton;
    }

    private JTextField crearCampo(String texto, int x, int y) {
        JTextField campo = new JTextField(texto);
        campo.setBounds(x, y, 300, 35);
        estilizarCampo(campo);
        return campo;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setBackground(Color.WHITE);
        campo.setForeground(new Color(81, 45, 168));
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createLineBorder(new Color(186, 104, 200), 2, true));
        campo.setHorizontalAlignment(JTextField.CENTER);
    }

    private void estilizarBoton(JButton boton) {
        boton.setBackground(new Color(179, 157, 219));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createLineBorder(new Color(186, 104, 200), 1, true));
        boton.setFocusPainted(false);
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(206, 147, 216));
            }
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(179, 157, 219));
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnRegistrarse) {
            modoRegistro = true;
            cambiarPanel(panelRegistro);
            txtNombreRegistro.setText("Ingrese su nombre");
            txtCorreoRegistro.setText("Ingrese su correo");
            txtContrasenaRegistro.setText("Ingrese su contraseña");
            lblMensajeRegistro.setText("");
            lblIdGenerado.setText("");
            txtNombreRegistro.setVisible(false);
            txtCorreoRegistro.setVisible(false);
            txtContrasenaRegistro.setVisible(false);
            btnRegistrar.setVisible(false);
            lblMensajeRegistro.setVisible(false);
            lblIdGenerado.setVisible(false);
            btnAlumnoRegistro.setVisible(true);
            btnDocenteRegistro.setVisible(true);
            lblSubtituloRegistro.setText("Seleccioná tu rol");
        } else if (src == btnIniciar) {
            modoRegistro = false;
            cambiarPanel(panelLogin);
            txtNombreLogin.setText("Ingrese su nombre");
            txtIdLogin.setText("Ingrese su ID");
            lblMensajeLogin.setText("");
            txtNombreLogin.setVisible(false);
            txtIdLogin.setVisible(false);
            btnIngresarLogin.setVisible(false);
            lblMensajeLogin.setVisible(false);
            btnAlumnoLogin.setVisible(true);
            btnDocenteLogin.setVisible(true);
            lblSubtituloLogin.setText("Seleccioná tu rol para ingresar");
        }

        // --- Lógica del Panel de Registro ---
        if (modoRegistro && (src == btnAlumnoRegistro || src == btnDocenteRegistro)) {
            tipoUsuarioRegistro = (src == btnAlumnoRegistro) ? "alumno" : "docente";

            btnAlumnoRegistro.setVisible(false);
            btnDocenteRegistro.setVisible(false);
            lblSubtituloRegistro.setText("Registrando " + tipoUsuarioRegistro);

            txtNombreRegistro.setVisible(true);
            txtCorreoRegistro.setVisible(true);
            txtContrasenaRegistro.setVisible(true);
            btnRegistrar.setVisible(true);
            lblMensajeRegistro.setText("Completá tus datos para registrarte.");
            lblMensajeRegistro.setVisible(true);
        }

        if (modoRegistro && src == btnRegistrar) registrarUsuario();

        // --- Lógica del Panel de Login ---
        if (!modoRegistro && (src == btnAlumnoLogin || src == btnDocenteLogin)) {
            tipoUsuarioLogin = (src == btnAlumnoLogin) ? "alumno" : "docente";

            btnAlumnoLogin.setVisible(false);
            btnDocenteLogin.setVisible(false);
            lblSubtituloLogin.setText("Ingresando como " + tipoUsuarioLogin);

            txtNombreLogin.setVisible(true);
            txtIdLogin.setVisible(true);
            btnIngresarLogin.setVisible(true);
            lblMensajeLogin.setText("Completá tus datos para ingresar.");
            lblMensajeLogin.setVisible(true);
        }

        if (!modoRegistro && src == btnIngresarLogin) iniciarSesion();

        // --- Botones Volver ---
        if (src == btnVolverRegistro || src == btnVolverLogin) {
            cambiarPanel(panelInicio);
            lblMensajeRegistro.setText("");
            lblIdGenerado.setText("");
            lblMensajeLogin.setText("");
        }
    }

    private void cambiarPanel(JPanel nuevo) {
        getContentPane().removeAll();
        add(nuevo);
        revalidate();
        repaint();
    }

    private void registrarUsuario() {
        String nombre = txtNombreRegistro.getText();
        String correo = txtCorreoRegistro.getText();
        String contrasena = new String(txtContrasenaRegistro.getPassword());

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() ||
                nombre.equals("Ingrese su nombre") || correo.equals("Ingrese su correo") || contrasena.equals("Ingrese su contraseña")) {
            lblMensajeRegistro.setText("Completá todos los campos.");
            return;
        }

        String id = GestorArchivos.generarId(tipoUsuarioRegistro); // Genera ID numérico
        GestorArchivos.guardarUnSoloUsuario(tipoUsuarioRegistro, nombre, correo, contrasena, id); // Guarda en JSON y en memoria

        lblMensajeRegistro.setText("Registro exitoso.");
        lblIdGenerado.setText("Tu ID es: " + id);
        lblIdGenerado.setVisible(true);

        txtNombreRegistro.setText("Ingrese su nombre");
        txtCorreoRegistro.setText("Ingrese su correo");
        txtContrasenaRegistro.setText("Ingrese su contraseña");
    }

    private void iniciarSesion() {
        String nombre = txtNombreLogin.getText();
        String id = txtIdLogin.getText();
        String tipo = tipoUsuarioLogin;

        if (nombre.isEmpty() || id.isEmpty() || nombre.equals("Ingrese su nombre") || id.equals("Ingrese su ID")) {
            lblMensajeLogin.setText("Completá todos los campos.");
            return;
        }

        Usuario usuarioLogueado = GestorArchivos.validarLogin(nombre, id, tipo); // Valida contra la lista en memoria

        if (usuarioLogueado != null) {
            lblMensajeLogin.setText("Bienvenido " + tipo + ".");
            this.dispose();
            if ("alumno".equals(tipo) && usuarioLogueado instanceof Alumno) {
                SwingUtilities.invokeLater(() -> new VentanaAlumno((Alumno) usuarioLogueado));
            } else if ("docente".equals(tipo) && usuarioLogueado instanceof Docente) {
                SwingUtilities.invokeLater(() -> new VentanaDocente((Docente) usuarioLogueado));
            }
        } else {
            lblMensajeLogin.setText("Credenciales incorrectas o ID no válido.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}
