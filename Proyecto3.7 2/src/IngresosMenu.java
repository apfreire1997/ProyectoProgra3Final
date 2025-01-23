import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IngresosMenu {
    private JPanel ingresosPanel;
    private JTextField txtDescripcionIngreso;
    private JTextField txtMontoIngreso;
    private JButton ingresoButton;
    private JTextArea txtIngresos;
    private JButton backButton;
    private JLabel lblDescripcionIngreso;
    private JLabel lblMontoIngreso;

    private ArrayList<Movimiento> listaIngresos;

    public IngresosMenu(ArrayList<Movimiento> listaIngresos, double saldoDisponible) {
        this.listaIngresos = listaIngresos;

        ingresosPanel = new JPanel();
        ingresosPanel.setLayout(null);

        ImageIcon originalImage = new ImageIcon(getClass().getResource("/imagenes/fondo.jpg"));
        Image scaledImage = originalImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, 800, 600);
        ingresosPanel.add(backgroundLabel);

        configurarComponentes();

        for (Component component : ingresosPanel.getComponents()) {
            if (component != backgroundLabel) {
                ingresosPanel.setComponentZOrder(component, 0);
            }
        }

        configureButtonActions();
    }

    private void configurarComponentes() {

        lblMontoIngreso = new JLabel("Registre el valor a ingresar:");
        lblMontoIngreso.setBounds(50, 50, 200, 30);
        lblMontoIngreso.setForeground(Color.BLACK);
        ingresosPanel.add(lblMontoIngreso);

        lblDescripcionIngreso = new JLabel("Describa el ingreso:");
        lblDescripcionIngreso.setBounds(50, 100, 200, 30);
        lblDescripcionIngreso.setForeground(Color.BLACK);
        ingresosPanel.add(lblDescripcionIngreso);

        txtMontoIngreso = new JTextField();
        txtMontoIngreso.setBounds(250, 50, 300, 30);
        ingresosPanel.add(txtMontoIngreso);

        txtDescripcionIngreso = new JTextField();
        txtDescripcionIngreso.setBounds(250, 100, 300, 30);
        ingresosPanel.add(txtDescripcionIngreso);

        ingresoButton = new JButton("Ingresar");
        ingresoButton.setBounds(600, 50, 150, 30);
        ingresoButton.setBackground(new Color(97, 137, 77));
        ingresoButton.setForeground(Color.WHITE);
        ingresoButton.setOpaque(true);
        ingresoButton.setBorderPainted(false);
        ingresosPanel.add(ingresoButton);

        txtIngresos = new JTextArea();
        txtIngresos.setBackground(new Color(209, 219, 190));
        txtIngresos.setForeground(Color.BLACK);
        txtIngresos.setBorder(null);

        JScrollPane scrollPane = new JScrollPane(txtIngresos);
        scrollPane.setBounds(50, 150, 700, 300);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        ingresosPanel.add(scrollPane);

        backButton = new JButton("Regresar");
        backButton.setBounds(325, 470, 150, 30);
        backButton.setBackground(new Color(97, 137, 77));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        ingresosPanel.add(backButton);
    }

    private void configureButtonActions() {

        ingresoButton.addActionListener(e -> {
            String descripcion = txtDescripcionIngreso.getText();
            String montoStr = txtMontoIngreso.getText();
            try {
                double monto = Double.parseDouble(montoStr);
                Movimiento nuevoIngreso = new Movimiento(descripcion, monto);
                listaIngresos.add(nuevoIngreso);

                GestorFinanzasApp.setSaldoDisponible(GestorFinanzasApp.getSaldoDisponible() + monto);

                txtIngresos.append("Ingreso: " + descripcion + " - $" + monto + "\n");

                txtDescripcionIngreso.setText("");
                txtMontoIngreso.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ingresosPanel, "Monto inválido. Inténtalo nuevamente.");
            }
        });

        backButton.addActionListener(e -> GestorFinanzasApp.showMainMenu());
    }

    public JPanel getPanel() {
        return ingresosPanel;
    }
}
