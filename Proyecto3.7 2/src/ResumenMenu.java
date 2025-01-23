import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ResumenMenu {
    private JPanel resumenPanel;
    private JComboBox<String> comboBoxMeses;
    private JComboBox<String> comboBoxCategorias;
    private JButton mostrarResumenButton;
    private JTextArea resumenCuentasTextArea;
    private JButton backButton;
    private JLabel backgroundLabel;
    private JLabel lblMeses;
    private JLabel lblCategorias;

    private ArrayList<Movimiento> listaIngresos;
    private ArrayList<Movimiento> listaEgresos;
    private ArrayList<Meta> listaMetas;
    private double saldoDisponible;

    public ResumenMenu(ArrayList<Movimiento> listaIngresos, ArrayList<Movimiento> listaEgresos, ArrayList<Meta> listaMetas, double saldoDisponible) {
        this.listaIngresos = listaIngresos;
        this.listaEgresos = listaEgresos;
        this.listaMetas = listaMetas;
        this.saldoDisponible = saldoDisponible;

        resumenPanel = new JPanel(null);


        ImageIcon originalImage = new ImageIcon(getClass().getResource("/imagenes/fondo.jpg"));
        Image scaledImage = originalImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, 800, 600);
        resumenPanel.add(backgroundLabel);


        configurarComponentes();

        resumenPanel.setComponentZOrder(backgroundLabel, resumenPanel.getComponentCount() - 1);

        configurarAcciones();
    }

    private void configurarComponentes() {
        lblMeses = new JLabel("Mes:");
        lblMeses.setBounds(200, 150, 100, 30);
        lblMeses.setForeground(Color.BLACK);
        resumenPanel.add(lblMeses);

        comboBoxMeses = new JComboBox<>(new String[]{
                "Todos", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        });
        comboBoxMeses.setBounds(300, 150, 200, 30);
        resumenPanel.add(comboBoxMeses);

        lblCategorias = new JLabel("Categorías:");
        lblCategorias.setBounds(200, 200, 100, 30);
        lblCategorias.setForeground(Color.BLACK);
        resumenPanel.add(lblCategorias);

        comboBoxCategorias = new JComboBox<>(new String[]{"Todos", "Ingresos", "Egresos", "Metas"});
        comboBoxCategorias.setBounds(300, 200, 200, 30);
        resumenPanel.add(comboBoxCategorias);

        mostrarResumenButton = new JButton("Calcular resumen");
        mostrarResumenButton.setBounds(300, 250, 200, 30);
        mostrarResumenButton.setBackground(new Color(97, 137, 77));
        mostrarResumenButton.setForeground(Color.WHITE);
        mostrarResumenButton.setOpaque(true);
        mostrarResumenButton.setContentAreaFilled(true);
        mostrarResumenButton.setBorderPainted(false);
        resumenPanel.add(mostrarResumenButton);

        resumenCuentasTextArea = new JTextArea();
        resumenCuentasTextArea.setEditable(false);
        resumenCuentasTextArea.setOpaque(true);
        resumenCuentasTextArea.setBackground(new Color(209, 219, 190));
        resumenCuentasTextArea.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(resumenCuentasTextArea);
        scrollPane.setBounds(50, 300, 700, 150);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        resumenPanel.add(scrollPane);

        backButton = new JButton("Regresar");
        backButton.setBounds(300, 470, 200, 30);
        backButton.setBackground(new Color(97, 137, 77));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setContentAreaFilled(true);
        backButton.setBorderPainted(false);
        resumenPanel.add(backButton);
    }

    private void configurarAcciones() {
        mostrarResumenButton.addActionListener(e -> mostrarResumen());
        backButton.addActionListener(e -> GestorFinanzasApp.showMainMenu());
    }

    private void mostrarResumen() {
        resumenCuentasTextArea.setText(""); // Limpiar el área de texto
        String mesSeleccionado = (String) comboBoxMeses.getSelectedItem();
        String categoriaSeleccionada = (String) comboBoxCategorias.getSelectedItem();

        int mes = comboBoxMeses.getSelectedIndex(); // Índice del mes (0 = Todos, 1 = Enero, etc.)

        switch (categoriaSeleccionada) {
            case "Todos":
                mostrarIngresos(mes);
                mostrarEgresos(mes);
                mostrarMetas();
                break;
            case "Ingresos":
                mostrarIngresos(mes);
                break;
            case "Egresos":
                mostrarEgresos(mes);
                break;
            case "Metas":
                mostrarMetas();
                break;
        }

        resumenCuentasTextArea.append("\nSaldo Disponible: $" + String.format("%.2f", saldoDisponible));
    }

    private void mostrarIngresos(int mes) {
        resumenCuentasTextArea.append("Ingresos:\n");
        listaIngresos.stream()
                .filter(mov -> mes == 0 || mov.getFecha().getMonthValue() == mes)
                .forEach(mov -> resumenCuentasTextArea.append(
                        "- " + mov.getDescripcion() + ": $" + mov.getMonto() + " (" + mov.getFecha() + ")\n"));
    }

    private void mostrarEgresos(int mes) {
        resumenCuentasTextArea.append("\nEgresos:\n");
        listaEgresos.stream()
                .filter(mov -> mes == 0 || mov.getFecha().getMonthValue() == mes)
                .forEach(mov -> resumenCuentasTextArea.append(
                        "- " + mov.getDescripcion() + ": $" + mov.getMonto() + " (" + mov.getFecha() + ")\n"));

        resumenCuentasTextArea.append("\nAportes a Metas:\n");
        for (Meta meta : listaMetas) {
            if (!meta.getFechasAportes().isEmpty()) {
                resumenCuentasTextArea.append("- Meta: " + meta.getNombre() + "\n");
                for (int i = 0; i < meta.getFechasAportes().size(); i++) {
                    double aporte = meta.getMontoActual() / meta.getFechasAportes().size();
                    saldoDisponible -= aporte;
                    resumenCuentasTextArea.append("  Aporte: $" + String.format("%.2f", aporte) + ", Fecha: " + meta.getFechasAportes().get(i) + ", Saldo Restante: $" + String.format("%.2f", saldoDisponible) + "\n");
                }
            }
        }
    }

    private void mostrarMetas() {
        resumenCuentasTextArea.append("\nMetas:\n");
        listaMetas.forEach(meta -> {
            resumenCuentasTextArea.append("- " + meta.getNombre() + ": Objetivo $" + meta.getMontoObjetivo() +
                    ", Ahorro $" + meta.getMontoActual() +
                    ", Progreso: " + meta.getProgreso() + "%\n");
            resumenCuentasTextArea.append("  Fechas de aportes:\n");
            meta.getFechasAportes().forEach(fecha -> resumenCuentasTextArea.append("    - " + fecha + "\n"));
        });
    }

    public JPanel getPanel() {
        return resumenPanel;
    }
}
