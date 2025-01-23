import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class GestorUsuarios {
    private HashMap<String, String> usuarios;
    private static final String FILE_PATH = "usuarios.dat";

    public GestorUsuarios() {
        cargarUsuarios();
    }

    public void registrarUsuario(String usuario, String contraseña) {
        if (usuarios.containsKey(usuario)) {
            throw new IllegalArgumentException("El usuario ya existe.");
        }
        usuarios.put(usuario, contraseña);
        guardarUsuarios();
    }

    public boolean validarUsuario(String usuario, String contraseña) {
        return usuarios.containsKey(usuario) && usuarios.get(usuario).equals(contraseña);
    }

    public Set<String> getUsuarios() { // Devuelve un conjunto de usuarios
        return usuarios.keySet();
    }

    public void eliminarUsuario(String usuario) { // Elimina un usuario
        if (usuarios.containsKey(usuario)) {
            usuarios.remove(usuario);
            guardarUsuarios();
        } else {
            throw new IllegalArgumentException("El usuario no existe.");
        }
    }

    public boolean isAdmin(String usuario) { // Verifica si el usuario es administrador
        return usuario.equals("admin");
    }

    private void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    private void cargarUsuarios() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            usuarios = (HashMap<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            usuarios = new HashMap<>();
        }
    }
}
