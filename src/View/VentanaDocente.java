package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaDocente extends JFrame implements ActionListener {

    private JPanel panelDocente;
    private JButton btnCrearCurso, btnPonerNota, btnVerAlumno, btnVolver;
    private JLabel lblTitulo, lblBienvenida;

    public VentanaDocente() {
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

        lblBienvenida = new JLabel("Bienvenido, docente", SwingConstants.CENTER);
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

    // --- Reutilizo las mismas funciones de estilo que en VentanaPrincipal/VentanaAlumno ---
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
            // implementar creación de curso (abrir formulario, guardar con GestorJSON, etc.)
            JOptionPane.showMessageDialog(this, "Abrir: Crear Curso (a implementar).", "Crear Curso", JOptionPane.INFORMATION_MESSAGE);
        } else if (src == btnPonerNota) {
            // implementar poner notas (seleccionar alumno/curso, ingresar nota, guardar)
            JOptionPane.showMessageDialog(this, "Abrir: Poner Nota (a implementar).", "Poner Nota", JOptionPane.INFORMATION_MESSAGE);
        } else if (src == btnVerAlumno) {
            // mostrar listado o detalle de alumnos
            JOptionPane.showMessageDialog(this, "Abrir: Ver Alumno (a implementar).", "Ver Alumno", JOptionPane.INFORMATION_MESSAGE);
        } else if (src == btnVolver) {
            this.dispose();
            // si querés volver a VentanaPrincipal, podés descomentar:
            // SwingUtilities.invokeLater(() -> new VentanaPrincipal());
        }
    }

    // Aca se puede probar la ventana independientemente de las otras
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaDocente());
    }
}

