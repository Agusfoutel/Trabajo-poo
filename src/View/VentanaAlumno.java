package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Model.Alumno;
import Model.GestorArchivos; // Asegurarse que GestorArchivos esté importado para main de prueba

public class VentanaAlumno extends JFrame implements ActionListener {

    private JPanel panelAlumno;
    private JButton btnNotas, btnInscripcion, btnCursosAnotados, btnVolver;
    private JLabel lblTitulo, lblBienvenida;
    private Alumno alumnoLogueado;

    public VentanaAlumno(Alumno alumno) {
        this.alumnoLogueado = alumno;
        setTitle("Alumno - Plataforma de Cursos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        crearPanelAlumno();

        add(panelAlumno);
        setVisible(true);
    }

    private void crearPanelAlumno() {
        panelAlumno = new JPanel(null);
        panelAlumno.setBackground(new Color(237, 231, 246));

        lblTitulo = new JLabel("Panel del Alumno", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(81, 45, 168));
        lblTitulo.setBounds(200, 40, 300, 30);
        panelAlumno.add(lblTitulo);

        lblBienvenida = new JLabel("Bienvenido, " + alumnoLogueado.getNombre(), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblBienvenida.setForeground(new Color(106, 27, 154));
        lblBienvenida.setBounds(200, 80, 300, 20);
        panelAlumno.add(lblBienvenida);

        btnNotas = crearBoton("Notas", 70, 150);
        btnInscripcion = crearBoton("Inscripción de Cursos", 270, 150);
        btnCursosAnotados = crearBoton("Cursos Anotados", 470, 150);

        btnVolver = crearBotonPequeno("← Volver", 30, 20);

        panelAlumno.add(btnNotas);
        panelAlumno.add(btnInscripcion);
        panelAlumno.add(btnCursosAnotados);
        panelAlumno.add(btnVolver);

        btnNotas.addActionListener(this);
        btnInscripcion.addActionListener(this);
        btnCursosAnotados.addActionListener(this);
        btnVolver.addActionListener(this);
    }

    private JButton crearBoton(String texto, int x, int y) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, 170, 60);
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

        if (src == btnNotas) {
            new VentanaNotasAlumno(alumnoLogueado).setVisible(true);
        } else if (src == btnInscripcion) {
            new VentanaInscripcionCursos(alumnoLogueado).setVisible(true);
        } else if (src == btnCursosAnotados) {
            new VentanaCursosAnotados(alumnoLogueado).setVisible(true);
        } else if (src == btnVolver) {
            this.dispose();
            SwingUtilities.invokeLater(() -> new VentanaPrincipal());
        }
    }

    public static void main(String[] args) {
        Model.GestorArchivos.inicializarDatos();
        Alumno dummyAlumno = new Alumno("Test Alumno", 9999, "test@mail.com", "pass", java.time.LocalDate.now(), 9999);
        SwingUtilities.invokeLater(() -> new VentanaAlumno(dummyAlumno));
    }
}
