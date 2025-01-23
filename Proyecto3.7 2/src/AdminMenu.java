import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.stream.Collectors;

public class AdminMenu {
    private JPanel adminPanel;
    private JComboBox<String> comboBoxUsuarios;
    private JButton eliminarUsuarioButton;
    private JButton backButton;
    private JTextArea logTextArea;
    private JLabel backgroundLabel;

    public AdminMenu(GestorUsuarios gestorUsuarios) {

        adminPanel = new JPanel(null);

        ImageIcon originalImage = new ImageIcon(getClass().getResource("/imagenes/fondo.jpg"));
        Image scaledImage = originalImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, 800, 600);
        adminPanel.add(backgroundLabel);
        configurarComponentes(gestorUsuarios);
        adminPanel.setComponentZOrder(backgroundLabel, adminPanel.getComponentCount() - 1);
    }

    private void configurarComponentes(GestorUsuarios gestorUsuarios) {
        JLabel lblUsuarioEliminar = new JLabel("Usuario a eliminar:");
        lblUsuarioEliminar.setBounds(50, 20, 150, 30);
        lblUsuarioEliminar.setForeground(Color.BLACK);
        adminPanel.add(lblUsuarioEliminar);

        comboBoxUsuarios = new JComboBox<>(
                gestorUsuarios.getUsuarios().stream()
                        .filter(usuario -> !usuario.equals("admin"))
                        .collect(Collectors.toList())
                        .toArray(new String[0])
        );
        comboBoxUsuarios.setBounds(200, 20, 200, 30);
        adminPanel.add(comboBoxUsuarios);

        eliminarUsuarioButton = new JButton("Eliminar usuario");
        eliminarUsuarioButton.setBounds(420, 20, 150, 30);
        eliminarUsuarioButton.setBackground(new Color(97, 137, 77));
        eliminarUsuarioButton.setForeground(Color.WHITE);
        eliminarUsuarioButton.setOpaque(true);
        eliminarUsuarioButton.setBorderPainted(false);
        adminPanel.add(eliminarUsuarioButton);

        logTextArea = new JTextArea();
        logTextArea.setBackground(new Color(209, 219, 190));
        logTextArea.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setBounds(50, 70, 700, 400);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        adminPanel.add(scrollPane);

        backButton = new JButton("Regresar");
        backButton.setBounds(300, 500, 150, 30);
        backButton.setBackground(new Color(97, 137, 77));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        adminPanel.add(backButton);

        configurarAcciones(gestorUsuarios);
    }

    private void configurarAcciones(GestorUsuarios gestorUsuarios) {
        eliminarUsuarioButton.addActionListener(e -> {
            String usuarioSeleccionado = (String) comboBoxUsuarios.getSelectedItem();
            if (usuarioSeleccionado != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        adminPanel,
                        "¿Seguro que desea eliminar al usuario " + usuarioSeleccionado + "?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    gestorUsuarios.eliminarUsuario(usuarioSeleccionado);
                    eliminarDatosUsuario(usuarioSeleccionado);

                    comboBoxUsuarios.setModel(new DefaultComboBoxModel<>(
                            gestorUsuarios.getUsuarios().stream()
                                    .filter(usuario -> !usuario.equals("admin"))
                                    .collect(Collectors.toList())
                                    .toArray(new String[0])
                    ));
                    logTextArea.append("Usuario eliminado: " + usuarioSeleccionado + "\n");
                    logTextArea.append("Datos de " + usuarioSeleccionado + " eliminados.\n");
                }
            }
        });

        backButton.addActionListener(e -> GestorFinanzasApp.showMainMenu());
    }

    private void eliminarDatosUsuario(String usuario) {
        File file = new File(usuario + "_data.dat");
        if (file.exists() && file.delete()) {
            logTextArea.append("Archivo de datos eliminado para el usuario: " + usuario + "\n");
        } else {
            logTextArea.append("No se encontraron datos para el usuario: " + usuario + "\n");
        }
    }

    public JPanel getPanel() {
        return adminPanel;
    }
}
