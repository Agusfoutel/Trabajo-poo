package View;

import Model.Alumno;
import Model.Inscripcion;
import Model.GestorArchivos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaCursosAnotados extends JFrame implements ActionListener {

    private Alumno alumnoLogueado;
    private JPanel panelCursos;
    private JLabel lblTitulo;
    private JTable tablaCursos;
    private JScrollPane scrollPane;
    private JButton btnVolver;

    public VentanaCursosAnotados(Alumno alumno) {
        this.alumnoLogueado = alumno;
        setTitle("Cursos Anotados de " + alumno.getNombre());
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        crearPanelCursos();

        add(panelCursos);
        setVisible(true);
    }

    private void crearPanelCursos() {
        panelCursos = new JPanel(new BorderLayout());
        panelCursos.setBackground(new Color(237, 231, 246));

        lblTitulo = new JLabel("Mis Cursos Anotados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(81, 45, 168));
        panelCursos.add(lblTitulo, BorderLayout.NORTH);

        String[] columnNames = {"Código", "Nombre del Curso", "Docente", "Fecha Inscripción"};
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
        panelCursos.add(scrollPane, BorderLayout.CENTER);

        btnVolver = crearBotonPequeno("← Volver", 0, 0);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBoton.setBackground(new Color(237, 231, 246));
        panelBoton.add(btnVolver);
        panelCursos.add(panelBoton, BorderLayout.SOUTH);
        btnVolver.addActionListener(this);
    }

    private void cargarCursosEnTabla(DefaultTableModel model) {
        model.setRowCount(0);
        List<Inscripcion> historial = alumnoLogueado.getHistorialCursos();
        if (historial.isEmpty()) {
            model.addRow(new Object[]{"No estás inscrito en ningún curso.", "", "", ""});
            return;
        }
        for (Inscripcion insc : historial) {
            model.addRow(new Object[]{
                    insc.getCurso().getCodigo(),
                    insc.getCurso().getNombre(),
                    insc.getCurso().getDocente() != null ? insc.getCurso().getDocente().getNombre() : "N/A",
                    insc.getFechaInscripcion().toString()
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
