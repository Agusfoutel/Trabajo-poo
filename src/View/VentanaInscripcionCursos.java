package View;

import Model.GestorArchivos;
import Model.Alumno;
import Model.Curso;
import Model.Inscripcion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class VentanaInscripcionCursos extends JFrame implements ActionListener {

    private Alumno alumnoLogueado;
    private JPanel panelInscripcion;
    private JLabel lblTitulo;
    private JTable tablaCursos;
    private JScrollPane scrollPane;
    private JButton btnInscribirse, btnVolver;

    public VentanaInscripcionCursos(Alumno alumno) {
        this.alumnoLogueado = alumno;
        setTitle("Inscripción de Cursos para " + alumno.getNombre());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        crearPanelInscripcion();

        add(panelInscripcion);
        setVisible(true);
    }

    private void crearPanelInscripcion() {
        panelInscripcion = new JPanel(new BorderLayout());
        panelInscripcion.setBackground(new Color(237, 231, 246));

        lblTitulo = new JLabel("Cursos Disponibles", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(81, 45, 168));
        panelInscripcion.add(lblTitulo, BorderLayout.NORTH);

        String[] columnNames = {"Código", "Nombre del Curso", "Docente", "Cupo Máximo", "Inscriptos"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCursos = new JTable(model);
        tablaCursos.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaCursos.setRowHeight(25);
        tablaCursos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaCursos.getTableHeader().setBackground(new Color(179, 157, 219));
        tablaCursos.getTableHeader().setForeground(Color.WHITE);

        cargarCursosEnTabla(model);

        scrollPane = new JScrollPane(tablaCursos);
        panelInscripcion.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(new Color(237, 231, 246));
        btnInscribirse = crearBoton("Inscribirse", 0, 0);
        btnVolver = crearBotonPequeno("← Volver", 0, 0);
        panelBotones.add(btnInscribirse);
        panelBotones.add(btnVolver);
        panelInscripcion.add(panelBotones, BorderLayout.SOUTH);

        btnInscribirse.addActionListener(this);
        btnVolver.addActionListener(this);
    }

    private void cargarCursosEnTabla(DefaultTableModel model) {
        model.setRowCount(0);
        List<Curso> cursosDisponibles = GestorArchivos.getCursos();
        if (cursosDisponibles.isEmpty()) {
            model.addRow(new Object[]{"No hay cursos disponibles.", "", "", "", ""});
            return;
        }

        for (Curso c : cursosDisponibles) {
            boolean yaInscrito = alumnoLogueado.getHistorialCursos().stream()
                    .anyMatch(insc -> insc.getCurso().getCodigo() == c.getCodigo());
            if (!yaInscrito) {
                model.addRow(new Object[]{
                        c.getCodigo(),
                        c.getNombre(),
                        c.getDocente() != null ? c.getDocente().getNombre() : "N/A",
                        c.getCuposMax(),
                        c.getInscriptos().size()
                });
            }
        }
        if (model.getRowCount() == 0) {
            model.addRow(new Object[]{"No hay cursos disponibles para inscribir.", "", "", "", ""});
        }
    }

    private JButton crearBoton(String texto, int x, int y) {
        JButton boton = new JButton(texto);
        estilizarBoton(boton);
        return boton;
    }

    private JButton crearBotonPequeno(String texto, int x, int y) {
        JButton boton = new JButton(texto);
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
        if (e.getSource() == btnVolver) {
            this.dispose();
        } else if (e.getSource() == btnInscribirse) {
            int selectedRow = tablaCursos.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un curso para inscribirte.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int codigoCurso = (int) tablaCursos.getValueAt(selectedRow, 0);
            GestorArchivos.findCursoByCodigo(codigoCurso).ifPresentOrElse(
                    cursoSeleccionado -> {
                        if (cursoSeleccionado.getInscriptos().size() < cursoSeleccionado.getCuposMax()) {
                            Inscripcion nuevaInscripcion = new Inscripcion(true, cursoSeleccionado, alumnoLogueado, LocalDate.now());
                            alumnoLogueado.inscribirseEnCurso(nuevaInscripcion);
                            cursoSeleccionado.agregarInscripcion(nuevaInscripcion);
                            GestorArchivos.addInscripcion(nuevaInscripcion);
                            JOptionPane.showMessageDialog(this, "¡Te has inscrito en " + cursoSeleccionado.getNombre() + "!", "Inscripción Exitosa", JOptionPane.INFORMATION_MESSAGE);
                            cargarCursosEnTabla((DefaultTableModel) tablaCursos.getModel()); // Recargar
                        } else {
                            JOptionPane.showMessageDialog(this, "El curso " + cursoSeleccionado.getNombre() + " está lleno.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    },
                    () -> JOptionPane.showMessageDialog(this, "Error: Curso no encontrado.", "Error Interno", JOptionPane.ERROR_MESSAGE)
            );
        }
    }
}
