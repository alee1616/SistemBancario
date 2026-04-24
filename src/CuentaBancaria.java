
import java.util.ArrayList;
import java.util.List;

public class CuentaBancaria {

    // atributos privados enncapsulamiento
    private String cliente;
    private String numeroCuenta;
    private double saldo;
    private List<String> historial; // mejoraaa un historial de movimientos

    public CuentaBancaria(String cliente, String numeroCuenta, double saldoInicial) {
        this.cliente = cliente;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
        this.historial = new ArrayList<>();
        registrarMovimiento("Cuenta creada con saldo: C$" + saldoInicial);
    }

    // metodo privado para el historial only dentro de la clase
    private void registrarMovimiento(String mensaje) {
        historial.add(mensaje);
    }

    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            registrarMovimiento("Depósito: +C$" + monto);
        }
    }

    public boolean retirar(double monto) {

        //  monto debe ser mayor a 0 y menor o igual al saldo actual
        if (monto > 0 && monto <= saldo) {
            saldo -= monto;
            registrarMovimiento("Retiro: -C$" + monto);
            return true; // si cumple, deja retirar
        }
        return false; // si este no cumple (monto > saldo), bloquea el retiro
    }

    // mejora de transferencia
    public boolean transferir(CuentaBancaria destino, double monto) {
        if (this.retirar(monto)) {
            destino.depositar(monto);
            this.registrarMovimiento("Transferencia enviada a " + destino.getCliente() + ": -C$" + monto);
            destino.registrarMovimiento("Transferencia recibida de " + this.cliente + ": +C$" + monto);
            return true;
        }
        return false;
    }

    // getters para acceder a los atributos privados
    public String getCliente() { return cliente; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
    public List<String> getHistorial() { return historial; }
}