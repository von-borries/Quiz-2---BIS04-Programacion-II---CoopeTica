package coopetica.presentacion;

import coopetica.modelo.Cuenta;
import coopetica.modelo.CuentaAhorro;
import coopetica.modelo.CuentaCorriente;

public class PruebaHerencia {

    public static void main(String[] args) {
        Cuenta a = new CuentaAhorro("001", "118450236", 350000, 0.045);
        Cuenta b = new CuentaCorriente("002", "118450236", 120000, 100000);

        System.out.println(a.retirar(400000));   // false
        System.out.println(b.retirar(200000));   // true
        System.out.println(a);
        System.out.println(b);
    }
}
