package modelo;

/**
 * Clase abstracta: concentra los datos comunes (DPI y nombre) de todos los
 * empleados. No se pueden crear objetos directamente con new Empleado(...).
 */
public abstract class Empleado {

    // Atributos (estado del objeto). Son private: encapsulación.
    private String dpi;
    private String nombre;

    /**
     * Constructor con parámetros (dpi y nombre son PARÁMETROS; los valores que
     * llegan desde Main al hacer new son los ARGUMENTOS). Con this se distingue
     * el atributo del parámetro. Un constructor no declara tipo de retorno.
     */
    public Empleado(String dpi, String nombre) {
        validarDpi(dpi);
        validarNombre(nombre);
        this.dpi = dpi;
        this.nombre = nombre.trim();
    }

    // ---------- Métodos abstractos: cada clase hija define su versión ----------

    public abstract double calcularPago();

    public abstract String obtenerTipo();

    // ---------- Getters y setters (los setters conservan las validaciones) ----------

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        validarDpi(dpi);
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarNombre(nombre);
        this.nombre = nombre.trim();
    }

    // ---------- Validaciones reutilizables (también las usa Main al capturar) ----------

    public static boolean esDpiValido(String dpi) {
        return dpi != null && dpi.matches("\\d{13}");
    }

    public static boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    public static boolean esMontoValido(double valor) {
        return Double.isFinite(valor) && valor >= 0;
    }

    private static void validarDpi(String dpi) {
        if (!esDpiValido(dpi)) {
            throw new IllegalArgumentException("El DPI debe tener exactamente 13 dígitos.");
        }
    }

    private static void validarNombre(String nombre) {
        if (!esNombreValido(nombre)) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    protected static void validarMonto(double valor, String campo) {
        if (!esMontoValido(valor)) {
            throw new IllegalArgumentException(
                    "El valor de " + campo + " debe ser un número finito mayor o igual a 0.");
        }
    }
}
