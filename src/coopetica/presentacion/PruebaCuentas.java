package coopetica.presentacion;

import coopetica.logica.CuentaServicio;
import coopetica.modelo.Cuenta;
import java.io.IOException;

public class PruebaCuentas {

    public static void main(String[] args) throws IOException {
        CuentaServicio servicio = new CuentaServicio();

        System.out.println(servicio.abrir("A", "001", "118450236", "350000", "0.045"));
        System.out.println(servicio.abrir("C", "002", "118450236", "120000", "100000"));
        System.out.println(servicio.abrir("A", "001", "206730415", "50000", "0.045"));

        System.out.println("--- Todas las cuentas ---");
        for (Cuenta c : servicio.listar()) { System.out.println(c); }

        System.out.println("Ahorro 001 retira 400000: " + servicio.retirar("001", "400000"));
        System.out.println("Corriente 002 retira 200000: " + servicio.retirar("002", "200000"));

        System.out.println("--- Despues de los retiros ---");
        for (Cuenta c : servicio.listar()) { System.out.println(c); }

        System.out.println("Total depositado: " + servicio.totalDepositado());
    }
}
