package View;

import Model.GestorArchivos;
import Model.Curso;
import Model.Docente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class VentanaCrearCurso extends JFrame implements ActionListener {

    private Docente docenteLogueado;
    private JPanel panelCrearCurso;
    private JLabel lblTitulo, lblNombre, lblDescripcion, lblCupo, lblCosto; // ¡Añadir lblCosto!
    private JTextField txtNombre, txtDescripcion, txtCupo, txtCosto; // ¡Añadir txtCosto!
    private JButton btnCrear, btnVolver;



    public VentanaCrearCurso(Docente docente) {
        this.docenteLogueado = docente;
        setTitle("Crear Nuevo Curso");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        crearPanelCrearCurso();

        add(panelCrearCurso);
        setVisible(true);
    }

    private void crearPanelCrearCurso() {
        panelCrearCurso = new JPanel(null);
        panelCrearCurso.setBackground(new Color(237, 231, 246));

        lblTitulo = new JLabel("Crear Nuevo Curso", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(81, 45, 168));
        lblTitulo.setBounds(100, 30, 300, 30);
        panelCrearCurso.add(lblTitulo);

        lblNombre = new JLabel("Nombre del Curso:");
        lblNombre.setBounds(50, 90, 150, 25);
        panelCrearCurso.add(lblNombre);
        txtNombre = crearCampo("", 200, 90);
        panelCrearCurso.add(txtNombre);

        lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setBounds(50, 140, 150, 25);
        panelCrearCurso.add(lblDescripcion);
        txtDescripcion = crearCampo("", 200, 140);
        panelCrearCurso.add(txtDescripcion);

        lblCupo = new JLabel("Cupo Máximo:");
        lblCupo.setBounds(50, 190, 150, 25);
        panelCrearCurso.add(lblCupo);
        txtCupo = crearCampo("", 200, 190);
        panelCrearCurso.add(txtCupo);

        // ¡NUEVO CAMPO PARA EL COSTO!
        lblCosto = new JLabel("Costo del Curso (USD):");
        lblCosto.setBounds(50, 230, 150, 25); // Ajustar posición
        panelCrearCurso.add(lblCosto);
        txtCosto = crearCampo("", 200, 230); // Ajustar posición
        panelCrearCurso.add(txtCosto);


        btnCrear = crearBotonGrande("Crear Curso", 150, 290); // Ajustar posición del botón
        panelCrearCurso.add(btnCrear);


        btnVolver = crearBotonPequeno("← Volver", 30, 20);
        panelCrearCurso.add(btnVolver);

        btnCrear.addActionListener(this);
        btnVolver.addActionListener(this);
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
        campo.setBounds(x, y, 250, 30);
        estilizarCampo(campo);
        return campo;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setBackground(Color.WHITE);
        campo.setForeground(new Color(81, 45, 168));
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createLineBorder(new Color(186, 104, 200), 2, true));
        campo.setHorizontalAlignment(JTextField.LEFT);
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
        if (e.getSource() == btnVolver) {
            this.dispose();
        } else if (e.getSource() == btnCrear) {
            String nombreCurso = txtNombre.getText();
            String descripcionCurso = txtDescripcion.getText();
            int cupoMaximo;
            double costoCurso;
            try {
                cupoMaximo = Integer.parseInt(txtCupo.getText());
                if (cupoMaximo <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un número válido para el cupo máximo.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try { // ¡Parsear el costo!
                costoCurso = Double.parseDouble(txtCosto.getText());
                if (costoCurso < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un número válido para el costo del curso.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nombreCurso.isEmpty() || descripcionCurso.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int codigoCurso = new Random().nextInt(9000) + 1000;
            while (GestorArchivos.findCursoByCodigo(codigoCurso).isPresent()) {
                codigoCurso = new Random().nextInt(9000) + 1000;
            }

            Curso nuevoCurso = new Curso(codigoCurso, nombreCurso, cupoMaximo, docenteLogueado, costoCurso);

            GestorArchivos.addCurso(nuevoCurso); // Añadir a GestorArchivos (en memoria y a disco)
            docenteLogueado.dictarCurso(nuevoCurso);

            JOptionPane.showMessageDialog(this, "Curso '" + nombreCurso + "' creado con éxito. Código: " + codigoCurso, "Curso Creado", JOptionPane.INFORMATION_MESSAGE);

            txtNombre.setText("");
            txtDescripcion.setText("");
            txtCupo.setText("");
            txtCosto.setText("");
        }
    }
}
