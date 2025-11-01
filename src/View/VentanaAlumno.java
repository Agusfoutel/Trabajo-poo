package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaAlumno extends JFrame implements ActionListener {

    private JPanel panelAlumno;
    private JButton btnNotas, btnInscripcion, btnCursosAnotados, btnVolver;
    private JLabel lblTitulo, lblBienvenida;

    public VentanaAlumno() {
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

        lblBienvenida = new JLabel("Bienvenido, alumno", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblBienvenida.setForeground(new Color(106, 27, 154));
        lblBienvenida.setBounds(200, 80, 300, 20);
        panelAlumno.add(lblBienvenida);

        btnNotas = crearBoton("Notas", 70, 150);
        btnInscripcion = crearBoton("Inscripción de Cursos", 270, 150);
        btnCursosAnotados = crearBoton("Cursos Anotados", 470, 150);

        // botón volver pequeño similar al otro archivo
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
            // aquí podés llamar a la lógica para mostrar notas (por ejemplo abrir otra ventana o panel)
            JOptionPane.showMessageDialog(this, "Abrir: Notas del alumno (a implementar).", "Notas", JOptionPane.INFORMATION_MESSAGE);
        } else if (src == btnInscripcion) {
            // abrir formulario de inscripción de cursos
            JOptionPane.showMessageDialog(this, "Abrir: Inscripción de Cursos (a implementar).", "Inscripción", JOptionPane.INFORMATION_MESSAGE);
        } else if (src == btnCursosAnotados) {
            // mostrar cursos en los que el alumno está anotado
            JOptionPane.showMessageDialog(this, "Abrir: Cursos Anotados (a implementar).", "Cursos Anotados", JOptionPane.INFORMATION_MESSAGE);
        } else if (src == btnVolver) {
            // cerrar esta ventana y (opcionalmente) volver a la principal
            this.dispose();
            // si querés volver a VentanaPrincipal, descomenta la línea siguiente:
            // SwingUtilities.invokeLater(() -> new VentanaPrincipal());
        }
    }

    // Aca se puede probar la ventana independientemente de las otras
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaAlumno());
    }
}

