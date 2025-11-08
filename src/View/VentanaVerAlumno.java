package View;

import Model.GestorArchivos;
import Model.Alumno;
import Model.Docente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaVerAlumno extends JFrame implements ActionListener {

    private Docente docenteLogueado;
    private JPanel panelAlumnos;
    private JLabel lblTitulo;
    private JTable tablaAlumnos;
    private JScrollPane scrollPane;
    private JButton btnVolver;

    public VentanaVerAlumno(Docente docente) {
        this.docenteLogueado = docente;
        setTitle("Ver Alumnos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        crearPanelAlumnos();

        add(panelAlumnos);
        setVisible(true);
    }

    private void crearPanelAlumnos() {
        panelAlumnos = new JPanel(new BorderLayout());
        panelAlumnos.setBackground(new Color(237, 231, 246));

        lblTitulo = new JLabel("Alumnos Registrados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(81, 45, 168));
        panelAlumnos.add(lblTitulo, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Nombre", "Correo", "Legajo", "Promedio"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaAlumnos = new JTable(model);
        tablaAlumnos.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaAlumnos.setRowHeight(25);
        tablaAlumnos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaAlumnos.getTableHeader().setBackground(new Color(179, 157, 219));
        tablaAlumnos.getTableHeader().setForeground(Color.WHITE);

        cargarAlumnosEnTabla(model);

        scrollPane = new JScrollPane(tablaAlumnos);
        panelAlumnos.add(scrollPane, BorderLayout.CENTER);

        btnVolver = crearBotonPequeno("← Volver", 0, 0);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBoton.setBackground(new Color(237, 231, 246));
        panelBoton.add(btnVolver);
        panelAlumnos.add(panelBoton, BorderLayout.SOUTH);
        btnVolver.addActionListener(this);
    }

    private void cargarAlumnosEnTabla(DefaultTableModel model) {
        model.setRowCount(0);
        List<Alumno> alumnosRegistrados = GestorArchivos.getAlumnos();
        if (alumnosRegistrados.isEmpty()) {
            model.addRow(new Object[]{"No hay alumnos registrados.", "", "", "", ""});
            return;
        }
        for (Alumno a : alumnosRegistrados) {
            model.addRow(new Object[]{
                    a.getId(),
                    a.getNombre(),
                    a.getCorreo(),
                    a.getLegajo(),
                    String.format("%.2f", a.getPromedio())
            });
        }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVolver) {
            this.dispose();
        }
    }
}
