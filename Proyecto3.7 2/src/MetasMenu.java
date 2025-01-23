import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MetasMenu {
    private JPanel metasPanel;
    private JTextField txtDefinicionMeta;
    private JTextField txtMontoMeta;
    private JButton definirMetaButton;
    private JComboBox<String> comboBoxMetas;
    private JTextField txtMontoAportarMeta;
    private JButton aportarMetaButton;
    private JTextArea textAreaMetas;
    private JProgressBar progressBarMeta;
    private JTextField saldoDisponibleField;
    private JTextField aporteSugeridoField;
    private JButton backButton;
    private JLabel backgroundLabel;

    private ArrayList<Meta> listaMetas;

    public MetasMenu(ArrayList<Meta> listaMetas, double saldoDisponible) {
        this.listaMetas = GestorFinanzasApp.getListaMetas(); // Obtener la lista global

        metasPanel = new JPanel(null);

        ImageIcon originalImage = new ImageIcon(getClass().getResource("/imagenes/fondo.jpg"));
        Image scaledImage = originalImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, 800, 600);
        metasPanel.add(backgroundLabel);

        configurarComponentes();

        metasPanel.setComponentZOrder(backgroundLabel, metasPanel.getComponentCount() - 1);

        actualizarSaldoDisponible(GestorFinanzasApp.getSaldoDisponible());
        cargarMetasEnInterfaz();
        configurarAcciones();
    }

    private void configurarComponentes() {
        JLabel lblNombreMeta = new JLabel("Nombre de la meta:");
        lblNombreMeta.setBounds(50, 20, 200, 30);
        lblNombreMeta.setForeground(Color.BLACK);
        metasPanel.add(lblNombreMeta);

        txtDefinicionMeta = new JTextField();
        txtDefinicionMeta.setBounds(250, 20, 200, 30);
        metasPanel.add(txtDefinicionMeta);

        JLabel lblMontoMeta = new JLabel("Monto meta:");
        lblMontoMeta.setBounds(50, 60, 200, 30);
        lblMontoMeta.setForeground(Color.BLACK);
        metasPanel.add(lblMontoMeta);

        txtMontoMeta = new JTextField();
        txtMontoMeta.setBounds(250, 60, 200, 30);
        metasPanel.add(txtMontoMeta);

        definirMetaButton = new JButton("Definir meta");
        definirMetaButton.setBounds(500, 40, 150, 30);
        definirMetaButton.setBackground(new Color(97, 137, 77));
        definirMetaButton.setForeground(Color.WHITE);
        definirMetaButton.setOpaque(true);
        definirMetaButton.setBorderPainted(false);
        metasPanel.add(definirMetaButton);

        JLabel lblMetas = new JLabel("Metas:");
        lblMetas.setBounds(50, 110, 200, 30);
        lblMetas.setForeground(Color.BLACK);
        metasPanel.add(lblMetas);

        comboBoxMetas = new JComboBox<>();
        comboBoxMetas.setBounds(250, 110, 200, 30);
        metasPanel.add(comboBoxMetas);

        JLabel lblMontoAportar = new JLabel("Aporte a la meta:");
        lblMontoAportar.setBounds(50, 150, 200, 30);
        lblMontoAportar.setForeground(Color.BLACK);
        metasPanel.add(lblMontoAportar);

        txtMontoAportarMeta = new JTextField();
        txtMontoAportarMeta.setBounds(250, 150, 200, 30);
        metasPanel.add(txtMontoAportarMeta);

        aportarMetaButton = new JButton("Aportar a la meta");
        aportarMetaButton.setBounds(500, 150, 150, 30);
        aportarMetaButton.setBackground(new Color(97, 137, 77));
        aportarMetaButton.setForeground(Color.WHITE);
        aportarMetaButton.setOpaque(true);
        aportarMetaButton.setBorderPainted(false);
        metasPanel.add(aportarMetaButton);

        textAreaMetas = new JTextArea();
        textAreaMetas.setBackground(new Color(209, 219, 190));
        textAreaMetas.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(textAreaMetas);
        scrollPane.setBounds(50, 200, 700, 150);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        metasPanel.add(scrollPane);

        progressBarMeta = new JProgressBar(0, 100);
        progressBarMeta.setBounds(50, 370, 700, 30);
        progressBarMeta.setUI(new CustomProgressBarUI());
        progressBarMeta.setStringPainted(true);
        metasPanel.add(progressBarMeta);


        JLabel lblSaldoDisponible = new JLabel("Saldo disponible:");
        lblSaldoDisponible.setBounds(50, 420, 200, 30);
        lblSaldoDisponible.setForeground(Color.BLACK);
        metasPanel.add(lblSaldoDisponible);

        saldoDisponibleField = new JTextField();
        saldoDisponibleField.setBounds(250, 420, 200, 30);
        saldoDisponibleField.setEditable(false);
        metasPanel.add(saldoDisponibleField);

        JLabel lblAporteSugerido = new JLabel("Aportación recomendada:");
        lblAporteSugerido.setBounds(50, 460, 200, 30);
        lblAporteSugerido.setForeground(Color.BLACK);
        metasPanel.add(lblAporteSugerido);

        aporteSugeridoField = new JTextField();
        aporteSugeridoField.setBounds(250, 460, 200, 30);
        aporteSugeridoField.setEditable(false);
        metasPanel.add(aporteSugeridoField);

        backButton = new JButton("Regresar");
        backButton.setBounds(325, 510, 150, 30);
        backButton.setBackground(new Color(97, 137, 77));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        metasPanel.add(backButton);
    }

    private void configurarAcciones() {
        definirMetaButton.addActionListener(e -> {
            String nombre = txtDefinicionMeta.getText();
            String montoStr = txtMontoMeta.getText();
            try {
                double monto = Double.parseDouble(montoStr);
                Meta nuevaMeta = new Meta(nombre, monto);
                listaMetas.add(nuevaMeta);
                GestorFinanzasApp.setListaMetas(listaMetas);
                comboBoxMetas.addItem(nombre);
                textAreaMetas.append("Meta: " + nombre + " - Objetivo: $" + monto + "\n");
                txtDefinicionMeta.setText("");
                txtMontoMeta.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(metasPanel, "Monto inválido.");
            }
        });

        aportarMetaButton.addActionListener(e -> {
            String nombre = (String) comboBoxMetas.getSelectedItem();
            String montoStr = txtMontoAportarMeta.getText();
            try {
                double monto = Double.parseDouble(montoStr);
                for (Meta meta : listaMetas) {
                    if (meta.getNombre().equals(nombre)) {
                        if (monto > GestorFinanzasApp.getSaldoDisponible()) {
                            JOptionPane.showMessageDialog(metasPanel, "Saldo insuficiente.");
                            return;
                        }
                        meta.aportar(monto);
                        GestorFinanzasApp.setSaldoDisponible(GestorFinanzasApp.getSaldoDisponible() - monto);
                        actualizarBarraProgreso(meta);
                        textAreaMetas.append("Aporte realizado: $" + monto + " a la meta: " + nombre + "\n");
                        actualizarSaldoDisponible(GestorFinanzasApp.getSaldoDisponible());
                        actualizarAporteSugerido(meta);
                        break;
                    }
                }
                txtMontoAportarMeta.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(metasPanel, "Monto inválido.");
            }
        });

        comboBoxMetas.addActionListener(e -> {
            String nombre = (String) comboBoxMetas.getSelectedItem();
            for (Meta meta : listaMetas) {
                if (meta.getNombre().equals(nombre)) {
                    actualizarBarraProgreso(meta);
                    actualizarAporteSugerido(meta);
                }
            }
        });

        backButton.addActionListener(e -> GestorFinanzasApp.showMainMenu());
    }

    private void cargarMetasEnInterfaz() {
        comboBoxMetas.removeAllItems();
        textAreaMetas.setText("");
        for (Meta meta : listaMetas) {
            comboBoxMetas.addItem(meta.getNombre());
            textAreaMetas.append("Meta: " + meta.getNombre() + " - Objetivo: $"
                    + meta.getMontoObjetivo() + " - Ahorro: $" + meta.getMontoActual() + "\n");
        }
    }

    private void actualizarSaldoDisponible(double saldo) {
        saldoDisponibleField.setText(String.format("%.2f", saldo));
    }

    private void actualizarBarraProgreso(Meta meta) {
        int progreso = meta.getProgreso();
        progressBarMeta.setValue(progreso);
        progressBarMeta.setString(progreso + "%");
    }


    private void actualizarAporteSugerido(Meta meta) {
        double saldoDisponible = GestorFinanzasApp.getSaldoDisponible();
        double montoRestante = meta.getMontoObjetivo() - meta.getMontoActual();
        double aporteSugerido = Math.min(montoRestante * 0.5, saldoDisponible * 0.8);
        aporteSugeridoField.setText(String.format("%.2f", aporteSugerido));
    }

    public JPanel getPanel() {
        return metasPanel;
    }
}