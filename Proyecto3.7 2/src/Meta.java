import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Meta implements Serializable {
    private String nombre;
    private double montoObjetivo;
    private double montoActual;
    private ArrayList<LocalDate> fechasAportes; // Fechas de los aportes

    public Meta(String nombre, double montoObjetivo) {
        this.nombre = nombre;
        this.montoObjetivo = montoObjetivo;
        this.montoActual = 0.0;
        this.fechasAportes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public double getMontoObjetivo() {
        return montoObjetivo;
    }

    public double getMontoActual() {
        return montoActual;
    }

    public ArrayList<LocalDate> getFechasAportes() {
        return fechasAportes;
    }

    public void aportar(double monto) {
        montoActual += monto;
        fechasAportes.add(LocalDate.now()); // Registrar la fecha del aporte
    }

    public int getProgreso() {
        return (int) ((montoActual / montoObjetivo) * 100);
    }
}
