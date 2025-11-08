package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Model.Docente;
import Model.GestorArchivos;
public class VentanaDocente extends JFrame implements ActionListener {

    private JPanel panelDocente;
    private JButton btnCrearCurso, btnPonerNota, btnVerAlumno, btnVolver;
    private JLabel lblTitulo, lblBienvenida;
    private Docente docenteLogueado;

    public VentanaDocente(Docente docente) {
        this.docenteLogueado = docente;
        setTitle("Docente - Plataforma de Cursos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        crearPanelDocente();

        add(panelDocente);
        setVisible(true);
    }

    private void crearPanelDocente() {
        panelDocente = new JPanel(null);
        panelDocente.setBackground(new Color(237, 231, 246));

        lblTitulo = new JLabel("Panel del Docente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(81, 45, 168));
        lblTitulo.setBounds(200, 40, 300, 30);
        panelDocente.add(lblTitulo);

        lblBienvenida = new JLabel("Bienvenido, " + docenteLogueado.getNombre(), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblBienvenida.setForeground(new Color(106, 27, 154));
        lblBienvenida.setBounds(200, 80, 300, 20);
        panelDocente.add(lblBienvenida);

        btnCrearCurso = crearBoton("Crear Curso", 70, 150);
        btnPonerNota = crearBoton("Poner Nota", 270, 150);
        btnVerAlumno = crearBoton("Ver Alumno", 470, 150);

        btnVolver = crearBotonPequeno("← Volver", 30, 20);

        panelDocente.add(btnCrearCurso);
        panelDocente.add(btnPonerNota);
        panelDocente.add(btnVerAlumno);
        panelDocente.add(btnVolver);

        btnCrearCurso.addActionListener(this);
        btnPonerNota.addActionListener(this);
        btnVerAlumno.addActionListener(this);
        btnVolver.addActionListener(this);
    }

    private JButton crearBoton(String texto, int x, int y) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, 160, 60);
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

        if (src == btnCrearCurso) {
            new VentanaCrearCurso(docenteLogueado).setVisible(true);
        } else if (src == btnPonerNota) {
            new VentanaPonerNota(docenteLogueado).setVisible(true);
        } else if (src == btnVerAlumno) {
            new VentanaVerAlumno(docenteLogueado).setVisible(true);
        } else if (src == btnVolver) {
            this.dispose();
            SwingUtilities.invokeLater(() -> new VentanaPrincipal());
        }
    }

    public static void main(String[] args) {
        Model.GestorArchivos.inicializarDatos();
        Docente dummyDocente = new Docente("Test Docente", 1234, "docente@mail.com", "pass", java.time.LocalDate.now());
        SwingUtilities.invokeLater(() -> new VentanaDocente(dummyDocente));
    }
}
