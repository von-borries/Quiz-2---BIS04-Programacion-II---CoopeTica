package coopetica.presentacion;

import coopetica.logica.CuentaServicio;
import coopetica.modelo.Cuenta;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class VentanaCuentas extends JFrame {

    private CuentaServicio servicio;

    private JComboBox<String> cmbTipo = new JComboBox<>(
            new String[] { "Ahorro", "Corriente" });
    private JTextField txtNumero = new JTextField(12);
    private JTextField txtCedula = new JTextField(12);
    private JTextField txtMonto = new JTextField(12);
    private JTextField txtExtra = new JTextField(12);
    private JLabel lblExtra = new JLabel("Tasa de interes (ej. 0.045):");
    private JTextArea areaSalida = new JTextArea(12, 50);

    public VentanaCuentas() throws IOException {
        super("CoopeTica - Cuentas");
        servicio = new CuentaServicio();

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("Tipo de cuenta:"));     form.add(cmbTipo);
        form.add(new JLabel("Numero de cuenta:"));   form.add(txtNumero);
        form.add(new JLabel("Cedula del asociado:")); form.add(txtCedula);
        form.add(new JLabel("Monto:"));              form.add(txtMonto);
        form.add(lblExtra);                          form.add(txtExtra);

        JButton btnAbrir = new JButton("Abrir");
        JButton btnDepositar = new JButton("Depositar");
        JButton btnRetirar = new JButton("Retirar");
        JButton btnListar = new JButton("Listar");
        JButton btnReporte = new JButton("Reporte");

        JPanel botones = new JPanel();
        botones.add(btnAbrir);
        botones.add(btnDepositar);
        botones.add(btnRetirar);
        botones.add(btnListar);
        botones.add(btnReporte);

        areaSalida.setEditable(false);

        JPanel norte = new JPanel(new BorderLayout());
        norte.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        norte.add(form, BorderLayout.CENTER);
        norte.add(botones, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(norte, BorderLayout.NORTH);
        add(new JScrollPane(areaSalida), BorderLayout.CENTER);

        // El texto de la ultima fila cambia segun el tipo elegido
        cmbTipo.addActionListener(e -> {
            if (cmbTipo.getSelectedIndex() == 0) {
                lblExtra.setText("Tasa de interes (ej. 0.045):");
            } else {
                lblExtra.setText("Limite de sobregiro:");
            }
        });

        btnAbrir.addActionListener(e -> ejecutar(() -> {
            String tipo = cmbTipo.getSelectedIndex() == 0 ? "A" : "C";
            return servicio.abrir(tipo, txtNumero.getText().trim(),
                    txtCedula.getText().trim(), txtMonto.getText().trim(),
                    txtExtra.getText().trim());
        }));
        btnDepositar.addActionListener(e -> ejecutar(() ->
                servicio.depositar(txtNumero.getText().trim(), txtMonto.getText().trim())));
        btnRetirar.addActionListener(e -> ejecutar(() ->
                servicio.retirar(txtNumero.getText().trim(), txtMonto.getText().trim())));
        btnListar.addActionListener(e -> listar());
        btnReporte.addActionListener(e -> reporte());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        listar();
    }

    private interface Accion { String correr() throws IOException; }

    private void ejecutar(Accion accion) {
        try {
            String resultado = accion.correr();
            if (resultado.equals("OK")) {
                listar();
            } else {
                JOptionPane.showMessageDialog(this, resultado,
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error de archivo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listar() {
        try {
            areaSalida.setText("");
            for (Cuenta c : servicio.listar()) {
                areaSalida.append(c + "\n");
            }
        } catch (IOException ex) {
            areaSalida.setText("Error de archivo: " + ex.getMessage());
        }
    }

    private void reporte() {
        try {
            double[] t = servicio.totalesPorTipo();
            areaSalida.setText("Total en ahorro: " + t[0] + "\n"
                    + "Total en corriente: " + t[1] + "\n"
                    + "Total depositado: " + servicio.totalDepositado() + "\n");
        } catch (IOException ex) {
            areaSalida.setText("Error de archivo: " + ex.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                new VentanaCuentas().setVisible(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
