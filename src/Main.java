
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<CuentaBancaria> listaCuentas = new ArrayList<>();

        // Cuentas iniciales de ejemplo
        listaCuentas.add(new CuentaBancaria("Marian", "001", 1500.0));
        listaCuentas.add(new CuentaBancaria("Juan Pablo", "002", 500.0));
        listaCuentas.add(new CuentaBancaria("Joela", "003", 500.0));


        String[] opciones = {
                "Ver Cuentas",
                "Depositar",
                "Retirar",
                "Transferencia",
                "Ver Historial",
                "Salir"
        };

        boolean salir = false;

        while (!salir) {

            // menu principal con botones bb
            int seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido a BANCO JAGUAR\nSeleccione una operación:",
                    "Sistema Bancario - Menú",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (seleccion) {
                case 0: // para see all accounts
                    StringBuilder info = new StringBuilder("/////// LISTA DE CLIENTES //////\n");
                    for (CuentaBancaria c : listaCuentas) {
                        info.append("Cliente: ").append(c.getCliente())
                                .append(" | Cuenta: ").append(c.getNumeroCuenta())
                                .append(" | Saldo: C$").append(c.getSaldo()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, info.toString());
                    break;

                case 1: // para depositar pq ando pelado
                    int idxDep = seleccionarCuenta(listaCuentas, "seleccionar para DEPOSITAR");
                    if (idxDep != -1) {
                        String montoStr = JOptionPane.showInputDialog("Ingrese el monto a depositar:");
                        if (montoStr != null) {
                            listaCuentas.get(idxDep).depositar(Double.parseDouble(montoStr));
                            JOptionPane.showMessageDialog(null, "¡Depósito exitoso!");
                        }
                    }
                    break;

                case 2: // retirar para la ruta
                    int idxRet = seleccionarCuenta(listaCuentas, "seleccionar para RETIRAR");
                    if (idxRet != -1) {
                        String montoStr = JOptionPane.showInputDialog("Ingrese el monto a retirar:");
                        if (montoStr != null) {
                            double m = Double.parseDouble(montoStr);
                            if (listaCuentas.get(idxRet).retirar(m)) {
                                JOptionPane.showMessageDialog(null, "Retiro completado.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Saldo insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    break;

                case 3: // para que me transfiera un sugar
                    int origen = seleccionarCuenta(listaCuentas, "ORIGEN de la transferencia");
                    int destino = seleccionarCuenta(listaCuentas, "DESTINO de la transferencia");

                    if (origen != -1 && destino != -1 && origen != destino) {
                        String montoStr = JOptionPane.showInputDialog("Monto a transferir:");
                        if (montoStr != null) {
                            double m = Double.parseDouble(montoStr);
                            if (listaCuentas.get(origen).transferir(listaCuentas.get(destino), m)) {
                                JOptionPane.showMessageDialog(null, "Transferencia exitosa de C$" + m);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error: Saldo insuficiente.");
                            }
                        }
                    } else if (origen == destino && origen != -1) {
                        JOptionPane.showMessageDialog(null, "No puedes transferir a la misma cuenta.");
                    }
                    break;

                case 4: // ver el historial porsiakaso
                    int idxHis = seleccionarCuenta(listaCuentas, "ver el HISTORIAL");
                    if (idxHis != -1) {
                        StringBuilder movs = new StringBuilder("MOVIMIENTOS DE " + listaCuentas.get(idxHis).getCliente().toUpperCase() + ":\n");
                        for (String m : listaCuentas.get(idxHis).getHistorial()) {
                            movs.append("- ").append(m).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, movs.toString());
                    }
                    break;

                case 5: case -1: // salir pq me quede sin saldo (le debo al banco)
                    JOptionPane.showMessageDialog(null, "Gracias por usar Banco Jaguar.");
                    salir = true;
                    break;
            }
        }
    }

    // metodo para no repetir códigos
    private static int seleccionarCuenta(ArrayList<CuentaBancaria> lista, String accion) {
        String[] clientes = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            clientes[i] = (i) + ". " + lista.get(i).getCliente();
        }

        String seleccion = (String) JOptionPane.showInputDialog(
                null,
                "Elija una cuenta para " + accion + ":",
                "Seleccionar Cuenta",
                JOptionPane.QUESTION_MESSAGE,
                null,
                clientes,
                clientes[0]
        );

        if (seleccion != null) {
            return Character.getNumericValue(seleccion.charAt(0));
        }
        return -1;
    }
}


// incluí cuentas de ejemplo para que el sistema sea funcional desde el principio.
// esto va a permitir demostrar de inmediato las operaciones complejas como las
// transferencias entre cuentas y el historial de movimientos, sin que usted tenga
// que esperar a que se registre manualmente cada cliente, así pues lo hice para más fácil.