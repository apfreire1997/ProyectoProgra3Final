import javax.swing.*;
import java.awt.*;

public class LoginForm {
    private JPanel panelLogin;
    private JTextField txtUsuario;
    private JPasswordField pswdContrasenia;
    private JButton loginButton;
    private JButton crearCuentaButton;
    private JLabel backgroundLabel;
    private JLabel lblUsuario;
    private JLabel lblContrasenia;

    public LoginForm() {


        panelLogin = new JPanel(null);

        ImageIcon originalImage = new ImageIcon(getClass().getResource("/imagenes/billetes copia.jpg"));
        Image scaledImage = originalImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, 800, 600); //


        panelLogin.add(backgroundLabel);


        lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(200, 250, 100, 30);
        lblContrasenia = new JLabel("Contraseña:");
        lblContrasenia.setBounds(200, 300, 100, 30);


        txtUsuario = new JTextField();
        txtUsuario.setBounds(300, 250, 200, 30);

        pswdContrasenia = new JPasswordField();
        pswdContrasenia.setBounds(300, 300, 200, 30);
        loginButton = new JButton("Iniciar Sesión");
        loginButton.setBounds(300, 350, 200, 30);

        crearCuentaButton = new JButton("Crear Cuenta");
        crearCuentaButton.setBounds(300, 400, 200, 30);


        panelLogin.add(lblUsuario);
        panelLogin.add(lblContrasenia);
        panelLogin.add(txtUsuario);
        panelLogin.add(pswdContrasenia);
        panelLogin.add(loginButton);
        panelLogin.add(crearCuentaButton);


        panelLogin.setComponentZOrder(backgroundLabel, panelLogin.getComponentCount() - 1);


        loginButton.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String contraseña = new String(pswdContrasenia.getPassword());


            if (usuario.equals("admin") && contraseña.equals("1234")) {
                JOptionPane.showMessageDialog(panelLogin, "Inicio de sesión exitoso como Administrador.");
                GestorFinanzasApp.iniciarSesion(usuario);
                GestorFinanzasApp.showMainMenu(); // Ir al MainMenu
            } else if (GestorFinanzasApp.getGestorUsuarios().validarUsuario(usuario, contraseña)) {
                JOptionPane.showMessageDialog(panelLogin, "Inicio de sesión exitoso.");
                GestorFinanzasApp.iniciarSesion(usuario);
                GestorFinanzasApp.showMainMenu(); // Ir al MainMenu
            } else {
                JOptionPane.showMessageDialog(panelLogin, "Usuario o contraseña incorrectos.");
            }
        });

        crearCuentaButton.addActionListener(e -> {
            String nuevoUsuario = txtUsuario.getText();
            String nuevaContrasenia = new String(pswdContrasenia.getPassword());

            if (nuevoUsuario.isEmpty() || nuevaContrasenia.isEmpty()) {
                JOptionPane.showMessageDialog(panelLogin, "Complete los campos.");
                return;
            }

            try {
                GestorFinanzasApp.getGestorUsuarios().registrarUsuario(nuevoUsuario, nuevaContrasenia);
                JOptionPane.showMessageDialog(panelLogin, "Cuenta creada exitosamente.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(panelLogin, ex.getMessage());
            }
        });
        loginButton.setBackground(new Color(97, 137, 77));
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setBorderPainted(false);

        crearCuentaButton.setBackground(new Color(97, 137, 77));
        crearCuentaButton.setForeground(Color.WHITE);
        crearCuentaButton.setOpaque(true);
        crearCuentaButton.setContentAreaFilled(true);
        crearCuentaButton.setBorderPainted(false);


    }

    public JPanel getPanel() {
        return panelLogin;
    }
}
