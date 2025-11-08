package View;

import Model.GestorArchivos;
import Model.Alumno;
import Model.Curso;
import Model.Docente;
import Model.Inscripcion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class VentanaPonerNota extends JFrame implements ActionListener {

    private Docente docenteLogueado;
    private JPanel panelPonerNota;
    private JLabel lblTitulo, lblCurso, lblAlumno, lblNota;
    private JComboBox<String> cbCursos;
    private JComboBox<String> cbAlumnos;
    private JTextField txtNota;
    private JButton btnAsignar, btnVolver;

    public VentanaPonerNota(Docente docente) {
        this.docenteLogueado = docente;
        setTitle("Poner Nota");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        crearPanelPonerNota();

        add(panelPonerNota);
        setVisible(true);
    }

    private void crearPanelPonerNota() {
        panelPonerNota = new JPanel(null);
        panelPonerNota.setBackground(new Color(237, 231, 246));

        lblTitulo = new JLabel("Asignar Calificación", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(81, 45, 168));
        lblTitulo.setBounds(100, 30, 300, 30);
        panelPonerNota.add(lblTitulo);

        lblCurso = new JLabel("Seleccionar Curso:");
        lblCurso.setBounds(50, 90, 150, 25);
        panelPonerNota.add(lblCurso);
        cbCursos = new JComboBox<>();
        cbCursos.setBounds(200, 90, 250, 30);
        estilizarComboBox(cbCursos);
        panelPonerNota.add(cbCursos);

        lblAlumno = new JLabel("Seleccionar Alumno:");
        lblAlumno.setBounds(50, 140, 150, 25);
        panelPonerNota.add(lblAlumno);
        cbAlumnos = new JComboBox<>();
        cbAlumnos.setBounds(200, 140, 250, 30);
        estilizarComboBox(cbAlumnos);
        panelPonerNota.add(cbAlumnos);

        lblNota = new JLabel("Nota (0-10):");
        lblNota.setBounds(50, 190, 150, 25);
        panelPonerNota.add(lblNota);
        txtNota = crearCampo("", 200, 190);
        panelPonerNota.add(txtNota);

        btnAsignar = crearBotonGrande("Asignar Nota", 150, 260);
        panelPonerNota.add(btnAsignar);

        cargarCursosDocente();
        cbCursos.addActionListener(this);
        actualizarAlumnosEnCurso();

        btnVolver = crearBotonPequeno("← Volver", 30, 20);
        panelPonerNota.add(btnVolver);

        btnAsignar.addActionListener(this);
        btnVolver.addActionListener(this);
    }


    private void cargarCursosDocente() {
        cbCursos.removeAllItems();
        List<Curso> cursosDelDocente = docenteLogueado.getCursosDictados();
        if (cursosDelDocente.isEmpty()) {
            cbCursos.addItem("No dictas cursos");
            btnAsignar.setEnabled(false);
            cbAlumnos.setEnabled(false);
            txtNota.setEnabled(false);
            return;
        }
        for (Curso c : cursosDelDocente) {
            cbCursos.addItem(c.getNombre() + " (Cod: " + c.getCodigo() + ")");
        }
        btnAsignar.setEnabled(true);
        cbAlumnos.setEnabled(true);
        txtNota.setEnabled(true);
    }

    private void actualizarAlumnosEnCurso() {
        cbAlumnos.removeAllItems();
        String selectedCourseItem = (String) cbCursos.getSelectedItem();
        if (selectedCourseItem == null || selectedCourseItem.equals("No dictas cursos")) {
            return;
        }

        Pattern p = Pattern.compile(".*\\(Cod: (\\d+)\\)");
        Matcher m = p.matcher(selectedCourseItem);
        int codigoCurso = -1;
        if (m.find()) {
            codigoCurso = Integer.parseInt(m.group(1));
        }

        if (codigoCurso != -1) {
            int finalCodigoCurso = codigoCurso;
            Optional<Curso> cursoOpt = docenteLogueado.getCursosDictados().stream()
                    .filter(c -> c.getCodigo() == finalCodigoCurso)
                    .findFirst();
            cursoOpt.ifPresent(c -> {
                if (c.getInscriptos().isEmpty()) {
                    cbAlumnos.addItem("No hay alumnos inscritos");
                    btnAsignar.setEnabled(false);
                } else {
                    for (Inscripcion insc : c.getInscriptos()) {
                        cbAlumnos.addItem(insc.getAlumno().getNombre() + " (Legajo: " + insc.getAlumno().getLegajo() + ")");
                    }
                    btnAsignar.setEnabled(true);
                }
            });
        }
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

    private void estilizarComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(81, 45, 168));
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ((JLabel)comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(186, 104, 200), 2, true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVolver) {
            this.dispose();
        } else if (e.getSource() == cbCursos) {
            actualizarAlumnosEnCurso();
        } else if (e.getSource() == btnAsignar) {
            String selectedCourseItem = (String) cbCursos.getSelectedItem();
            String selectedAlumnoItem = (String) cbAlumnos.getSelectedItem();
            String notaText = txtNota.getText();

            if (selectedCourseItem == null || selectedCourseItem.equals("No dictas cursos") ||
                    selectedAlumnoItem == null || selectedAlumnoItem.equals("No hay alumnos inscritos")) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un curso y un alumno.", "Error de Selección", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int codigoCurso = -1;
            Pattern pCurso = Pattern.compile(".*\\(Cod: (\\d+)\\)");
            Matcher mCurso = pCurso.matcher(selectedCourseItem);
            if (mCurso.find()) {
                codigoCurso = Integer.parseInt(mCurso.group(1));
            }

            int legajoAlumno = -1;
            Pattern pAlumno = Pattern.compile(".*\\(Legajo: (\\d+)\\)");
            Matcher mAlumno = pAlumno.matcher(selectedAlumnoItem);
            if (mAlumno.find()) {
                legajoAlumno = Integer.parseInt(mAlumno.group(1));
            }

            double notaValor;
            try {
                notaValor = Double.parseDouble(notaText);
                if (notaValor < 0 || notaValor > 10) {
                    throw new NumberFormatException("Nota fuera de rango.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa una nota válida entre 0 y 10.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (codigoCurso != -1 && legajoAlumno != -1) {
                docenteLogueado.calificarAlumno(legajoAlumno, codigoCurso, notaValor);
                JOptionPane.showMessageDialog(this, "Nota asignada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtNota.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al procesar la selección de curso o alumno.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
