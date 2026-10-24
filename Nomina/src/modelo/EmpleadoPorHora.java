package modelo;

/**
 * Empleado que cobra por hora trabajada en el mes.
 * Hereda DPI y nombre de Empleado.
 */
public class EmpleadoPorHora extends Empleado {

    private double pagoPorHora;
    private double horasTrabajadas;

    public EmpleadoPorHora(String dpi, String nombre, double pagoPorHora, double horasTrabajadas) {
        super(dpi, nombre); // inicializa los datos comunes en la clase base
        validarMonto(pagoPorHora, "pago por hora");
        validarMonto(horasTrabajadas, "horas trabajadas");
        this.pagoPorHora = pagoPorHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    public double getPagoPorHora() {
        return pagoPorHora;
    }

    public void setPagoPorHora(double pagoPorHora) {
        validarMonto(pagoPorHora, "pago por hora");
        this.pagoPorHora = pagoPorHora;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(double horasTrabajadas) {
        validarMonto(horasTrabajadas, "horas trabajadas");
        this.horasTrabajadas = horasTrabajadas;
    }

    // Pago del mes = pago por hora x horas trabajadas
    @Override
    public double calcularPago() {
        return pagoPorHora * horasTrabajadas;
    }

    @Override
    public String obtenerTipo() {
        return "Por hora";
    }
}
