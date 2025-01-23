import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GastosMenu {
    private JPanel gastosPanel;
    private JTextField txtDescripcionGasto;
    private JTextField txtMontoEgreso;
    private JButton egresoButton;
    private JTextArea txtEgresos;
    private JButton backButton;
    private JLabel lblDescripcionGasto;
    private JLabel lblMontoGasto;
    private JTextField saldoDisponibleField;

    private ArrayList<Movimiento> listaEgresos;

    public GastosMenu(ArrayList<Movimiento> listaEgresos, double saldoDisponible) {
        this.listaEgresos = listaEgresos;

        gastosPanel = new JPanel();
        gastosPanel.setLayout(null);

        ImageIcon originalImage = new ImageIcon(getClass().getResource("/imagenes/fondo.jpg"));
        Image scaledImage = originalImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, 800, 600); // Tamaño del fondo
        gastosPanel.add(backgroundLabel);

        configurarComponentes();

        for (Component component : gastosPanel.getComponents()) {
            if (component != backgroundLabel) {
                gastosPanel.setComponentZOrder(component, 0);
            }
        }

        configureButtonActions();


        actualizarSaldoDisponible(saldoDisponible);
    }

    private void configurarComponentes() {

        JLabel lblSaldoDisponible = new JLabel("Saldo Disponible:");
        lblSaldoDisponible.setBounds(50, 10, 200, 30);
        lblSaldoDisponible.setForeground(Color.BLACK);
        gastosPanel.add(lblSaldoDisponible);

        saldoDisponibleField = new JTextField();
        saldoDisponibleField.setBounds(250, 10, 150, 30);
        saldoDisponibleField.setEditable(false);
        gastosPanel.add(saldoDisponibleField);

        lblMontoGasto = new JLabel("Registre el valor del gasto:");
        lblMontoGasto.setBounds(50, 50, 200, 30);
        lblMontoGasto.setForeground(Color.BLACK);
        gastosPanel.add(lblMontoGasto);

        lblDescripcionGasto = new JLabel("Describa el gasto:");
        lblDescripcionGasto.setBounds(50, 100, 200, 30);
        lblDescripcionGasto.setForeground(Color.BLACK);
        gastosPanel.add(lblDescripcionGasto);

        txtMontoEgreso = new JTextField();
        txtMontoEgreso.setBounds(250, 50, 300, 30);
        gastosPanel.add(txtMontoEgreso);

        txtDescripcionGasto = new JTextField();
        txtDescripcionGasto.setBounds(250, 100, 300, 30);
        gastosPanel.add(txtDescripcionGasto);

        egresoButton = new JButton("Registrar");
        egresoButton.setBounds(600, 50, 150, 30);
        egresoButton.setBackground(new Color(97, 137, 77));
        egresoButton.setForeground(Color.WHITE);
        egresoButton.setOpaque(true);
        egresoButton.setBorderPainted(false);
        gastosPanel.add(egresoButton);

        txtEgresos = new JTextArea();
        txtEgresos.setBackground(new Color(209, 219, 190));
        txtEgresos.setForeground(Color.BLACK);
        txtEgresos.setBorder(null);

        JScrollPane scrollPane = new JScrollPane(txtEgresos);
        scrollPane.setBounds(50, 150, 700, 300);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        gastosPanel.add(scrollPane);

        backButton = new JButton("Regresar");
        backButton.setBounds(325, 470, 150, 30);
        backButton.setBackground(new Color(97, 137, 77));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        gastosPanel.add(backButton);
    }

    private void configureButtonActions() {

        egresoButton.addActionListener(e -> {
            String descripcion = txtDescripcionGasto.getText();
            String montoStr = txtMontoEgreso.getText();
            try {
                double monto = Double.parseDouble(montoStr);
                if (monto > GestorFinanzasApp.getSaldoDisponible()) {
                    JOptionPane.showMessageDialog(gastosPanel, "Saldo insuficiente.");
                    return;
                }

                Movimiento nuevoGasto = new Movimiento(descripcion, -monto);
                listaEgresos.add(nuevoGasto);

                GestorFinanzasApp.setSaldoDisponible(GestorFinanzasApp.getSaldoDisponible() - monto);

                txtEgresos.append("Gasto: " + descripcion + " - $" + monto + "\n");


                actualizarSaldoDisponible(GestorFinanzasApp.getSaldoDisponible());

                txtDescripcionGasto.setText("");
                txtMontoEgreso.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gastosPanel, "Monto inválido. Inténtalo nuevamente.");
            }
        });

        backButton.addActionListener(e -> GestorFinanzasApp.showMainMenu());
    }

    private void actualizarSaldoDisponible(double saldo) {
        saldoDisponibleField.setText(String.format("$%.2f", saldo));
    }

    public JPanel getPanel() {
        return gastosPanel;
    }
}
