import java.io.Serializable;
import java.time.LocalDate;

public class Movimiento implements Serializable {
    private String descripcion;
    private double monto;
    private LocalDate fecha;

    public Movimiento(String descripcion, double monto) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.fecha = LocalDate.now();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
