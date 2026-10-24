package modelo;

/**
 * Empleado con sueldo mensual fijo más una bonificación del mes.
 * Hereda DPI y nombre de Empleado.
 */
public class EmpleadoFijo extends Empleado {

    private double sueldoMensual;
    private double bonificacion;

    public EmpleadoFijo(String dpi, String nombre, double sueldoMensual, double bonificacion) {
        super(dpi, nombre); // inicializa los datos comunes en la clase base
        validarMonto(sueldoMensual, "sueldo mensual");
        validarMonto(bonificacion, "bonificación");
        this.sueldoMensual = sueldoMensual;
        this.bonificacion = bonificacion;
    }

    public double getSueldoMensual() {
        return sueldoMensual;
    }

    public void setSueldoMensual(double sueldoMensual) {
        validarMonto(sueldoMensual, "sueldo mensual");
        this.sueldoMensual = sueldoMensual;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(double bonificacion) {
        validarMonto(bonificacion, "bonificación");
        this.bonificacion = bonificacion;
    }

    // Pago del mes = sueldo mensual + bonificación
    @Override
    public double calcularPago() {
        return sueldoMensual + bonificacion;
    }

    @Override
    public String obtenerTipo() {
        return "Fijo";
    }
}
