package coopetica.presentacion;

import coopetica.logica.CuentaServicio;
import java.io.IOException;

public class PruebaExtensiones {

    public static void main(String[] args) throws IOException {
        CuentaServicio servicio = new CuentaServicio();

        System.out.println(servicio.abrir("A", "010", "118450236", "100000", "0.045"));
        System.out.println(servicio.abrir("C", "011", "118450236", "50000", "80000"));

        System.out.println("Deposito 25000 en 010: " + servicio.depositar("010", "25000"));
        System.out.println("Deposito -5 en 010: " + servicio.depositar("010", "-5"));
        System.out.println("Deposito en cuenta inexistente: " + servicio.depositar("999", "100"));

        double[] t = servicio.totalesPorTipo();
        System.out.println("Total en ahorro: " + t[0]);
        System.out.println("Total en corriente: " + t[1]);
    }
}
