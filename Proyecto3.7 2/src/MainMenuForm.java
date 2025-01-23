import javax.swing.*;
import java.awt.*;

public class MainMenuForm {
    private JPanel mainPanel;
    private JButton gastosButton;
    private JButton metasButton;
    private JButton adminMenuButton;
    private JButton ingresosButton;
    private JButton resumenButton;
    private JButton logoutButton;

    public MainMenuForm() {

        mainPanel.setLayout(null);


        ImageIcon originalImage = new ImageIcon(getClass().getResource("/imagenes/dinero.jpg"));
        Image scaledImage = originalImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, 800, 600);
        mainPanel.add(backgroundLabel);

        JLabel titleLabel = new JLabel("MENU PRINCIPAL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(300, 50, 200, 30);
        mainPanel.add(titleLabel);

        gastosButton = new JButton("Menu Gastos");
        gastosButton.setBounds(200, 150, 200, 40);

        ingresosButton = new JButton("Menu Ingresos");
        ingresosButton.setBounds(400, 150, 200, 40);

        metasButton = new JButton("Metas");
        metasButton.setBounds(200, 220, 200, 40);

        resumenButton = new JButton("Resumen Cuentas");
        resumenButton.setBounds(400, 220, 200, 40);

        adminMenuButton = new JButton("Menu Administrador");
        adminMenuButton.setBounds(300, 290, 200, 40);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(300, 360, 200, 40);

        configureButtonStyles();

        mainPanel.add(gastosButton);
        mainPanel.add(ingresosButton);
        mainPanel.add(metasButton);
        mainPanel.add(resumenButton);


        if (GestorFinanzasApp.getUsuarioActual().equals("admin")) {
            mainPanel.add(adminMenuButton);
        }

        mainPanel.add(logoutButton);


        mainPanel.setComponentZOrder(backgroundLabel, mainPanel.getComponentCount() - 1);


        configureButtonActions();
    }

    private void configureButtonStyles() {

        Color buttonBackgroundColor = new Color(97, 137, 77);
        Color buttonTextColor = Color.WHITE;

        JButton[] buttons = {gastosButton, metasButton, adminMenuButton, ingresosButton, resumenButton, logoutButton};

        for (JButton button : buttons) {
            button.setBackground(buttonBackgroundColor);
            button.setForeground(buttonTextColor);
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setBorderPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 14));
        }
    }

    private void configureButtonActions() {

        gastosButton.addActionListener(e -> GestorFinanzasApp.showGastosMenu());


        ingresosButton.addActionListener(e -> GestorFinanzasApp.showIngresosMenu());


        metasButton.addActionListener(e -> GestorFinanzasApp.showMetasMenu());


        resumenButton.addActionListener(e -> GestorFinanzasApp.showResumenMenu());


        adminMenuButton.addActionListener(e -> GestorFinanzasApp.showAdminMenu());


        logoutButton.addActionListener(e -> {
            GestorFinanzasApp.cerrarSesion();
            GestorFinanzasApp.showLoginScreen();
        });
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}
