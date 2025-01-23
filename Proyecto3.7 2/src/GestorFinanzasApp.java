import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class GestorFinanzasApp {
    private static JFrame frame;
    private static final GestorUsuarios gestorUsuarios = new GestorUsuarios();
    private static ArrayList<Movimiento> listaIngresos = new ArrayList<>();
    private static ArrayList<Movimiento> listaEgresos = new ArrayList<>();
    private static ArrayList<Meta> listaMetas = new ArrayList<>();
    private static double saldoDisponible = 0.0;
    private static String usuarioActual;

    public static void main(String[] args) {
        frame = new JFrame("USAVER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        showLoginScreen();
        frame.setVisible(true);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showLoginScreen() {
        LoginForm loginForm = new LoginForm();
        frame.setContentPane(loginForm.getPanel());

        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    public static void showMainMenu() {
        MainMenuForm mainMenuForm = new MainMenuForm();
        frame.setContentPane(mainMenuForm.getPanel());
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    public static void showIngresosMenu() {
        IngresosMenu ingresosMenu = new IngresosMenu(listaIngresos, saldoDisponible);
        frame.setContentPane(ingresosMenu.getPanel());
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showGastosMenu() {
        GastosMenu gastosMenu = new GastosMenu(listaEgresos, saldoDisponible);
        frame.setContentPane(gastosMenu.getPanel());
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showMetasMenu() {
        MetasMenu metasMenu = new MetasMenu(listaMetas, saldoDisponible);
        frame.setContentPane(metasMenu.getPanel());
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showResumenMenu() {
        ResumenMenu resumenMenu = new ResumenMenu(listaIngresos, listaEgresos, listaMetas, saldoDisponible);
        frame.setContentPane(resumenMenu.getPanel());
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void showAdminMenu() {
        AdminMenu adminMenu = new AdminMenu(gestorUsuarios);
        frame.setContentPane(adminMenu.getPanel());
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static double getSaldoDisponible() {
        return saldoDisponible;
    }

    public static void setSaldoDisponible(double nuevoSaldo) {
        saldoDisponible = nuevoSaldo;
    }

    public static GestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }

    public static String getUsuarioActual() {
        return usuarioActual;
    }

    public static void iniciarSesion(String usuario) {
        usuarioActual = usuario;
        cargarDatosUsuario();
    }

    public static void cerrarSesion() {
        guardarDatosUsuario();
        usuarioActual = null;
        listaIngresos.clear();
        listaEgresos.clear();
        listaMetas.clear();
        saldoDisponible = 0.0;
    }

    private static void guardarDatosUsuario() {
        if (usuarioActual == null) return;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usuarioActual + "_data.dat"))) {
            oos.writeObject(listaIngresos);
            oos.writeObject(listaEgresos);
            oos.writeObject(listaMetas);
            oos.writeDouble(saldoDisponible);
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }


    private static void cargarDatosUsuario() {
        if (usuarioActual == null) return;
        File file = new File(usuarioActual + "_data.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                listaIngresos = (ArrayList<Movimiento>) ois.readObject();
                listaEgresos = (ArrayList<Movimiento>) ois.readObject();
                listaMetas = (ArrayList<Meta>) ois.readObject();
                saldoDisponible = ois.readDouble();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar datos: " + e.getMessage());
            }
        } else {
            listaIngresos = new ArrayList<>();
            listaEgresos = new ArrayList<>();
            listaMetas = new ArrayList<>();
            saldoDisponible = 0.0;
        }
    }

    public static ArrayList<Meta> getListaMetas() {
        return listaMetas;
    }
    public static void setListaMetas(ArrayList<Meta> nuevasMetas) {
        listaMetas = nuevasMetas;
    }


}
