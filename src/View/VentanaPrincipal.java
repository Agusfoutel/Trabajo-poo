package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Model.GestorJSON;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private JPanel panelInicio, panelRegistro, panelLogin;
    private JButton btnRegistrarse, btnIniciar, btnVolver, btnAlumno, btnDocente, btnRegistrar, btnIngresar;
    private JTextField txtNombre, txtCorreo, txtId;
    private JPasswordField txtContrasena;
    private JLabel lblTitulo, lblSubtitulo, lblMensaje, lblIdGenerado;
    private String tipoUsuario = "";
    private boolean modoRegistro = true;

    public VentanaPrincipal() {
        setTitle("Plataforma de Cursos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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

        btnVolver = crearBotonPequeno("Volver", 30, 20);
        panelRegistro.add(btnVolver);
        btnVolver.addActionListener(this);

        lblSubtitulo = new JLabel("Seleccioná tu rol");
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblSubtitulo.setForeground(new Color(81, 45, 168));
        lblSubtitulo.setBounds(260, 60, 300, 25);
        panelRegistro.add(lblSubtitulo);

        btnAlumno = crearBoton("Alumno", 180, 110);
        btnDocente = crearBoton("Docente", 370, 110);
        panelRegistro.add(btnAlumno);
        panelRegistro.add(btnDocente);
        btnAlumno.addActionListener(this);
        btnDocente.addActionListener(this);

        txtNombre = crearCampo("Ingrese su nombre", 200, 180);
        txtCorreo = crearCampo("Ingrese su correo", 200, 230);
        txtContrasena = new JPasswordField("Ingrese su contraseña");
        txtContrasena.setBounds(200, 280, 300, 35);
        estilizarCampo(txtContrasena);

        btnRegistrar = crearBotonGrande("Registrar", 250, 340);

        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setBounds(100, 400, 500, 30);
        lblMensaje.setForeground(new Color(81, 45, 168));

        lblIdGenerado = new JLabel("", SwingConstants.CENTER);
        lblIdGenerado.setBounds(100, 430, 500, 30);
        lblIdGenerado.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblIdGenerado.setForeground(new Color(106, 27, 154));

        panelRegistro.add(txtNombre);
        panelRegistro.add(txtCorreo);
        panelRegistro.add(txtContrasena);
        panelRegistro.add(btnRegistrar);
        panelRegistro.add(lblMensaje);
        panelRegistro.add(lblIdGenerado);

        btnRegistrar.addActionListener(this);

        // Ocultos al principio
        txtNombre.setVisible(false);
        txtCorreo.setVisible(false);
        txtContrasena.setVisible(false);
        btnRegistrar.setVisible(false);
    }

    private void crearPanelLogin() {
        panelLogin = new JPanel(null);
        panelLogin.setBackground(new Color(237, 231, 246));

        JButton btnVolverLogin = crearBotonPequeno("← Volver", 30, 20);
        panelLogin.add(btnVolverLogin);
        btnVolverLogin.addActionListener(this);

        JLabel lblRol = new JLabel("Seleccioná tu rol para ingresar");
        lblRol.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblRol.setForeground(new Color(81, 45, 168));
        lblRol.setBounds(220, 70, 400, 30);
        panelLogin.add(lblRol);

        JButton btnAlumnoLogin = crearBoton("Alumno", 180, 130);
        JButton btnDocenteLogin = crearBoton("Docente", 370, 130);
        panelLogin.add(btnAlumnoLogin);
        panelLogin.add(btnDocenteLogin);

        btnAlumnoLogin.addActionListener(this);
        btnDocenteLogin.addActionListener(this);

        txtNombre = crearCampo("Ingrese su nombre", 200, 210);
        txtId = crearCampo("Ingrese su ID", 200, 260);
        btnIngresar = crearBotonGrande("Ingresar", 250, 320);

        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setBounds(100, 380, 500, 30);
        lblMensaje.setForeground(new Color(81, 45, 168));

        panelLogin.add(txtNombre);
        panelLogin.add(txtId);
        panelLogin.add(btnIngresar);
        panelLogin.add(lblMensaje);

        txtNombre.setVisible(false);
        txtId.setVisible(false);
        btnIngresar.setVisible(false);
    }

    //Estilos
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

        //inicio
        if (src == btnRegistrarse) {
            modoRegistro = true;
            cambiarPanel(panelRegistro);
        } else if (src == btnIniciar) {
            modoRegistro = false;
            cambiarPanel(panelLogin);
        }

        //registro
        if (src == btnAlumno || src == btnDocente) {
            tipoUsuario = (src == btnAlumno) ? "alumno" : "docente";

            // Ocultar opciones
            btnAlumno.setVisible(false);
            btnDocente.setVisible(false);
            lblSubtitulo.setText("Registrando " + tipoUsuario);

            // Mostrar campos
            txtNombre.setVisible(true);
            txtCorreo.setVisible(true);
            txtContrasena.setVisible(true);
            btnRegistrar.setVisible(true);
            lblMensaje.setText("Completá tus datos para registrarte.");
        }

        if (src == btnRegistrar) registrarUsuario();
        if (src == btnIngresar) iniciarSesion();
        if (src == btnVolver) cambiarPanel(panelInicio);
    }

    private void cambiarPanel(JPanel nuevo) {
        getContentPane().removeAll();
        add(nuevo);
        revalidate();
        repaint();
    }

    //Registro
    private void registrarUsuario() {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String contrasena = new String(txtContrasena.getPassword());

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            lblMensaje.setText("Completá todos los campos.");
            return;
        }

        String id = Model.GestorJSON.generarId(tipoUsuario);
        Model.GestorJSON.guardarUsuario(tipoUsuario, nombre, correo, contrasena, id);

        lblMensaje.setText("Registro exitoso.");
        lblIdGenerado.setText("Tu ID es: " + id);
    }

    private void iniciarSesion() {
        String nombre = txtNombre.getText();
        String id = txtId.getText();

        if (Model.GestorJSON.validarLogin(nombre, id, tipoUsuario)) {
            lblMensaje.setText("Bienvenido " + tipoUsuario + ".");
        } else {
            lblMensaje.setText("ID incorrecto o tipo no válido.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}
