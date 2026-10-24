package aplicacion;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import modelo.Empleado;
import modelo.EmpleadoFijo;
import modelo.EmpleadoPorHora;

/**
 * Controla la selección del mes, los menús, la captura de datos y el reporte.
 */
public class Main {

    // Arreglo simple: la cantidad de meses es fija y está predefinida.
    private static final String[] meses = {
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    // ArrayList: la cantidad de empleados crece conforme se ingresan.
    // Guarda los dos tipos de empleado en una sola colección (polimorfismo).
    private static final ArrayList<Empleado> empleados = new ArrayList<Empleado>();

    private static final Scanner teclado = new Scanner(System.in);

    // Atributo (estático): se puede usar desde cualquier método de Main.
    private static int numeroMes;

    public static void main(String[] args) {
        limpiarPantalla();
        System.out.println("CÁLCULO DE NÓMINA");
        System.out.println();
        numeroMes = leerEnteroEnRango("Ingrese el mes a trabajar (1-12): ", 1, 12);

        boolean salir = false;
        while (!salir) {
            limpiarPantalla();
            mostrarMenuPrincipal();
            int opcion = leerEnteroEnRango("Seleccione una opción: ", 1, 3);
            switch (opcion) {
                case 1:
                    ingresarNomina();
                    break;
                case 2:
                    imprimirNomina();
                    break;
                default:
                    salir = true;
                    break;
            }
        }

        limpiarPantalla();
        System.out.println("Gracias por usar el programa de nómina. ¡Hasta pronto!");
        teclado.close();
    }

    // ------------------------------------------------------------------
    // Pantallas y menús
    // ------------------------------------------------------------------

    private static void mostrarMenuPrincipal() {
        System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
        System.out.println();
        System.out.println("1 - Ingreso de nómina");
        System.out.println("2 - Impresión de nómina");
        System.out.println("3 - Salir");
        System.out.println();
    }

    // 'subtitulo' es un PARÁMETRO: solo existe dentro de este método.
    private static void mostrarTitulo(String subtitulo) {
        System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
        System.out.println(subtitulo);
        System.out.println();
    }

    // ------------------------------------------------------------------
    // Opción 1: ingreso de nómina
    // ------------------------------------------------------------------

    private static void ingresarNomina() {
        boolean ingresarOtro = true;
        while (ingresarOtro) {
            limpiarPantalla();
            mostrarTitulo("INGRESO DE EMPLEADO A NÓMINA");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println();
            System.out.println("1 - Empleado fijo");
            System.out.println("2 - Empleado por hora");
            System.out.println();
            int tipo = leerEnteroEnRango("Seleccione el tipo de empleado: ", 1, 2);

            limpiarPantalla();
            // Variable local: solo existe dentro de este bloque while.
            Empleado nuevo;
            if (tipo == 1) {
                nuevo = capturarEmpleadoFijo();
            } else {
                nuevo = capturarEmpleadoPorHora();
            }

            // Solo se agrega (y el contador sube) cuando todos los datos fueron válidos.
            empleados.add(nuevo);

            System.out.println();
            System.out.println("Empleado agregado correctamente.");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println();
            System.out.println("¿Desea ingresar otro empleado?");
            System.out.println("1 - Sí, ingresar otro empleado");
            System.out.println("2 - Volver al menú principal");
            System.out.println();
            int respuesta = leerEnteroEnRango("Seleccione una opción: ", 1, 2);
            ingresarOtro = (respuesta == 1);
        }
    }

    private static Empleado capturarEmpleadoFijo() {
        mostrarTitulo("INGRESO DE EMPLEADO FIJO");
        String dpi = leerDpi();
        String nombre = leerNombre();
        double sueldoMensual = leerMonto("Sueldo mensual (Q): ");
        double bonificacion = leerMonto("Bonificación de este mes (Q): ");

        // dpi, nombre, sueldoMensual y bonificacion son los ARGUMENTOS del constructor.
        Empleado empleado = new EmpleadoFijo(dpi, nombre, sueldoMensual, bonificacion);

        System.out.println();
        System.out.println("Pago del mes = sueldo mensual + bonificación");
        System.out.println("Pago de " + empleado.getNombre() + " = Q "
                + formatearMonto(empleado.calcularPago()));
        return empleado;
    }

    private static Empleado capturarEmpleadoPorHora() {
        mostrarTitulo("INGRESO DE EMPLEADO POR HORA");
        String dpi = leerDpi();
        String nombre = leerNombre();
        double pagoPorHora = leerMonto("Pago por hora (Q): ");
        double horasTrabajadas = leerMonto("Horas trabajadas este mes: ");

        Empleado empleado = new EmpleadoPorHora(dpi, nombre, pagoPorHora, horasTrabajadas);

        System.out.println();
        System.out.println("Pago del mes = pago por hora x horas trabajadas");
        System.out.println("Pago de " + empleado.getNombre() + " = Q "
                + formatearMonto(empleado.calcularPago()));
        return empleado;
    }

    // ------------------------------------------------------------------
    // Opción 2: impresión de nómina (reporte en pantalla)
    // ------------------------------------------------------------------

    private static void imprimirNomina() {
        limpiarPantalla();
        System.out.println("NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
        System.out.println();

        double total = 0;

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados en la nómina de este mes.");
        } else {
            System.out.printf(Locale.US, "%-15s %-22s %-10s %10s%n",
                    "DPI", "Nombre", "Tipo", "Pago (Q)");

            // Recorrido del ArrayList con referencias de tipo Empleado (polimorfismo):
            // cada objeto ejecuta su propio calcularPago().
            for (Empleado empleado : empleados) {
                double pago = empleado.calcularPago();
                total += pago;
                System.out.printf(Locale.US, "%-15s %-22s %-10s %10.2f%n",
                        empleado.getDpi(), empleado.getNombre(), empleado.obtenerTipo(), pago);
            }
        }

        System.out.println();
        System.out.println("Total de trabajadores: " + empleados.size());
        System.out.println("Total de nómina: Q " + formatearMonto(total));
        System.out.println();
        System.out.print("Presione Enter para volver al menú principal...");
        teclado.nextLine();
    }

    // ------------------------------------------------------------------
    // Lectura y validación de datos
    // ------------------------------------------------------------------

    // Devuelve un entero entre minimo y maximo; repite la pregunta si el dato es inválido.
    private static int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String texto = teclado.nextLine().trim();
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // se muestra el mensaje de error más abajo
            }
            System.out.println("Entrada inválida. Debe ingresar un número entero entre "
                    + minimo + " y " + maximo + ".");
        }
    }

    private static String leerDpi() {
        while (true) {
            System.out.print("DPI: ");
            String dpi = teclado.nextLine().trim();
            if (Empleado.esDpiValido(dpi)) {
                return dpi;
            }
            System.out.println("Error: el DPI debe tener exactamente 13 dígitos numéricos.");
        }
    }

    private static String leerNombre() {
        while (true) {
            System.out.print("Nombre: ");
            String nombre = teclado.nextLine();
            if (Empleado.esNombreValido(nombre)) {
                return nombre.trim();
            }
            System.out.println("Error: el nombre no puede estar vacío.");
        }
    }

    // Devuelve un número finito mayor o igual a 0 (acepta decimales con punto).
    private static double leerMonto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = teclado.nextLine().trim();
            if (!texto.matches("[+-]?(\\d+\\.?\\d*|\\.\\d+)([eE][+-]?\\d+)?")) {
                System.out.println("Error: ingrese un valor numérico (por ejemplo 25 o 120.5).");
                continue;
            }
            double valor = Double.parseDouble(texto);
            if (Empleado.esMontoValido(valor)) {
                return valor;
            }
            System.out.println("Error: el valor debe ser un número finito mayor o igual a 0.");
        }
    }

    // ------------------------------------------------------------------
    // Utilidades
    // ------------------------------------------------------------------

    private static String formatearMonto(double monto) {
        return String.format(Locale.US, "%.2f", monto);
    }

    // Si la consola del IDE no puede borrarse, se simula con líneas en blanco.
    private static void limpiarPantalla() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }
}
