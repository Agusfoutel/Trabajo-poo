package View;

import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;


public class VentanaPago extends JDialog implements ActionListener {

    private Alumno alumnoLogueado;
    private Curso cursoSeleccionado;
    private double costoBaseCurso;

    private JPanel panelPago;
    private JLabel lblTitulo, lblCurso, lblCostoBase, lblMetodoPago, lblCuotas, lblMontoFinal;
    private JComboBox<MetodoPago> cbMetodoPago;
    private JComboBox<Integer> cbCuotas;
    private JButton btnConfirmar, btnCancelar;

    private VentanaInscripcionCursos ventanaPadre;

    public VentanaPago(JFrame owner, Alumno alumno, Curso curso, VentanaInscripcionCursos padre) {
        super(owner, "Realizar Pago", true);
        this.alumnoLogueado = alumno;
        this.cursoSeleccionado = curso;
        this.costoBaseCurso = curso.getCosto();
        this.ventanaPadre = padre;

        setSize(450, 400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        crearPanelPago();
        add(panelPago);

        actualizarMontoFinal();
        setVisible(true);
    }

    private void crearPanelPago() {
        panelPago = new JPanel(null);
        panelPago.setBackground(new Color(237, 231, 246));

        lblTitulo = new JLabel("Realizar Pago por Inscripción", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(81, 45, 168));
        lblTitulo.setBounds(50, 20, 350, 30);
        panelPago.add(lblTitulo);

        lblCurso = new JLabel("Curso: " + cursoSeleccionado.getNombre(), SwingConstants.LEFT);
        lblCurso.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblCurso.setBounds(50, 70, 350, 20);
        panelPago.add(lblCurso);

        lblCostoBase = new JLabel(String.format("Costo Base: %.2f USD", costoBaseCurso), SwingConstants.LEFT);
        lblCostoBase.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblCostoBase.setBounds(50, 100, 350, 20);
        panelPago.add(lblCostoBase);

        lblMetodoPago = new JLabel("Método de Pago:", SwingConstants.LEFT);
        lblMetodoPago.setBounds(50, 140, 150, 25);
        panelPago.add(lblMetodoPago);

        cbMetodoPago = new JComboBox<>(MetodoPago.values());
        cbMetodoPago.setBounds(200, 140, 180, 30);
        estilizarComboBox(cbMetodoPago);
        cbMetodoPago.addActionListener(this);
        panelPago.add(cbMetodoPago);

        lblCuotas = new JLabel("Cuotas:", SwingConstants.LEFT);
        lblCuotas.setBounds(50, 180, 150, 25);
        panelPago.add(lblCuotas);

        List<Integer> cuotasList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            cuotasList.add(i);
        }
        cbCuotas = new JComboBox<>(cuotasList.toArray(new Integer[0]));
        cbCuotas.setSelectedItem(1);
        cbCuotas.setBounds(200, 180, 180, 30);
        estilizarComboBox(cbCuotas);
        cbCuotas.addActionListener(this);
        panelPago.add(cbCuotas);

        lblMontoFinal = new JLabel("Monto Final: Calculando...", SwingConstants.CENTER);
        lblMontoFinal.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblMontoFinal.setForeground(new Color(106, 27, 154));
        lblMontoFinal.setBounds(50, 240, 350, 30);
        panelPago.add(lblMontoFinal);

        btnConfirmar = crearBotonGrande("Confirmar Pago", 80, 300);
        btnCancelar = crearBotonGrande("Cancelar", 250, 300);
        panelPago.add(btnConfirmar);
        panelPago.add(btnCancelar);

        btnConfirmar.addActionListener(this);
        btnCancelar.addActionListener(this);

        actualizarVisibilidadCuotas();
    }

    private void actualizarVisibilidadCuotas() {
        MetodoPago selectedMethod = (MetodoPago) cbMetodoPago.getSelectedItem();
        boolean isCreditCard = (selectedMethod == MetodoPago.TARJETA_CREDITO);
        lblCuotas.setVisible(isCreditCard);
        cbCuotas.setVisible(isCreditCard);
    }

    private void actualizarMontoFinal() {
        MetodoPago selectedMethod = (MetodoPago) cbMetodoPago.getSelectedItem();
        double montoCalculado = costoBaseCurso;
        int cuotas = (int) cbCuotas.getSelectedItem();

        if (selectedMethod == null) {
            lblMontoFinal.setText("Monto Final: Seleccione método");
            return;
        }

        switch (selectedMethod) {
            case EFECTIVO:
                Efectivo pagoEfectivo = new Efectivo(0, costoBaseCurso, LocalDate.now(), "Efectivo");
                montoCalculado = pagoEfectivo.calcularMontoFinal();
                cuotas = 1;
                break;
            case TARJETA_DEBITO:
                TarjetaDebito pagoDebito = new TarjetaDebito(0, costoBaseCurso, LocalDate.now(), "Débito", 0, LocalDate.now(), 0);
                montoCalculado = pagoDebito.calcularMontoFinal();
                cuotas = 1;
                break;
            case TARJETA_CREDITO:
                TarjetaCredito pagoCredito = new TarjetaCredito(0, costoBaseCurso, LocalDate.now(), "Crédito", 0, LocalDate.now(), 0, cuotas);
                montoCalculado = pagoCredito.calcularMontoFinal();
                break;
        }
        lblMontoFinal.setText(String.format("Monto Final: %.2f USD", montoCalculado));
    }


    private JButton crearBotonGrande(String texto, int x, int y) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, 150, 40);
        estilizarBoton(boton);
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

    private void estilizarComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(81, 45, 168));
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ((JLabel)comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(186, 104, 200), 2, true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancelar) {
            this.dispose();
        } else if (e.getSource() == btnConfirmar) {
            realizarInscripcionConPago();
        } else if (e.getSource() == cbMetodoPago) {
            actualizarVisibilidadCuotas();
            actualizarMontoFinal();
        } else if (e.getSource() == cbCuotas) {
            actualizarMontoFinal();
        }
    }

    private void realizarInscripcionConPago() {
        MetodoPago selectedMetodoPago = (MetodoPago) cbMetodoPago.getSelectedItem();
        int cuotas = (int) cbCuotas.getSelectedItem();
        double montoFinal = Double.parseDouble(
                lblMontoFinal.getText()
                        .replace("Monto Final: ", "")
                        .replace(" USD", "")
                        .replace(",", ".")
        );

        if (selectedMetodoPago == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un método de pago.", "Error de Pago", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selectedMetodoPago == MetodoPago.EFECTIVO || selectedMetodoPago == MetodoPago.TARJETA_DEBITO) {
            cuotas = 1;
        }

        Inscripcion nuevaInscripcion = new Inscripcion(true, cursoSeleccionado, alumnoLogueado, LocalDate.now(),
                selectedMetodoPago, montoFinal, cuotas);

        alumnoLogueado.inscribirseEnCurso(nuevaInscripcion);
        cursoSeleccionado.agregarInscripcion(nuevaInscripcion);
        GestorArchivos.addInscripcion(nuevaInscripcion);

        JOptionPane.showMessageDialog(this,
                "¡Inscripción exitosa en " + cursoSeleccionado.getNombre() + "!\nMonto pagado: " + String.format("%.2f USD", montoFinal) +
                        "\nMétodo: " + selectedMetodoPago + (selectedMetodoPago == MetodoPago.TARJETA_CREDITO ? " en " + cuotas + " cuotas" : ""),
                "Inscripción Confirmada", JOptionPane.INFORMATION_MESSAGE);

        this.dispose();

        if (ventanaPadre != null) {
            ventanaPadre.cargarCursosEnTabla((DefaultTableModel) ventanaPadre.getTablaCursos().getModel());
        }
    }
}