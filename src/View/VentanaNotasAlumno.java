package View;

import Model.Alumno;
import Model.Calificacion;
import Model.GestorArchivos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaNotasAlumno extends JFrame implements ActionListener {

    private Alumno alumnoLogueado;
    private JPanel panelNotas;
    private JLabel lblTitulo;
    private JTable tablaNotas;
    private JScrollPane scrollPane;
    private JButton btnVolver;

    public VentanaNotasAlumno(Alumno alumno) {
        this.alumnoLogueado = alumno;
        setTitle("Notas de " + alumno.getNombre());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        crearPanelNotas();

        add(panelNotas);
        setVisible(true);
    }

    private void crearPanelNotas() {
        panelNotas = new JPanel(new BorderLayout());
        panelNotas.setBackground(new Color(237, 231, 246));

        lblTitulo = new JLabel("Mis Notas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(81, 45, 168));
        panelNotas.add(lblTitulo, BorderLayout.NORTH);

        String[] columnNames = {"Curso", "Nota"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tablaNotas = new JTable(model);
        tablaNotas.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaNotas.setRowHeight(25);
        tablaNotas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaNotas.getTableHeader().setBackground(new Color(179, 157, 219));
        tablaNotas.getTableHeader().setForeground(Color.WHITE);

        cargarNotasEnTabla(model);

        scrollPane = new JScrollPane(tablaNotas);
        panelNotas.add(scrollPane, BorderLayout.CENTER);

        btnVolver = crearBotonPequeno("← Volver", 0, 0);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBoton.setBackground(new Color(237, 231, 246));
        panelBoton.add(btnVolver);
        panelNotas.add(panelBoton, BorderLayout.SOUTH);
        btnVolver.addActionListener(this);
    }

    private void cargarNotasEnTabla(DefaultTableModel model) {
        List<Calificacion> notas = alumnoLogueado.getNotas();
        if (notas.isEmpty()) {
            model.addRow(new Object[]{"No hay notas registradas", ""});
            return;
        }
        for (Calificacion nota : notas) {
            model.addRow(new Object[]{nota.getCurso().getNombre(), nota.getNota()});
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
