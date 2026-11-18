package coopetica.logica;

import coopetica.datos.CuentaArchivoDAO;
import coopetica.modelo.Cuenta;
import coopetica.modelo.CuentaAhorro;
import coopetica.modelo.CuentaCorriente;
import java.io.IOException;
import java.util.ArrayList;

public class CuentaServicio {

    private CuentaArchivoDAO dao;

    public CuentaServicio() throws IOException {
        this.dao = new CuentaArchivoDAO();
    }

    public ArrayList<Cuenta> listar() throws IOException {
        return dao.listar();
    }

    public Cuenta buscar(String numero) throws IOException {
        for (Cuenta c : dao.listar()) {
            if (c.getNumero().equals(numero)) { return c; }
        }
        return null;
    }

    public String abrir(String tipo, String numero, String cedula,
                        String montoTexto, String extraTexto)
                        throws IOException {
        if (numero.isEmpty() || cedula.isEmpty()) {
            return "El numero de cuenta y la cedula son obligatorios.";
        }
        double monto, extra;
        try {
            monto = Double.parseDouble(montoTexto);
            extra = Double.parseDouble(extraTexto);
        } catch (NumberFormatException e) {
            return "El monto y el dato adicional deben ser numeros.";
        }
        if (monto < 0) { return "El monto inicial no puede ser negativo."; }
        if (buscar(numero) != null) {
            return "Ya existe una cuenta con ese numero.";
        }
        ArrayList<Cuenta> cuentas = dao.listar();
        if (tipo.equals("A")) {
            cuentas.add(new CuentaAhorro(numero, cedula, monto, extra));
        } else {
            cuentas.add(new CuentaCorriente(numero, cedula, monto, extra));
        }
        dao.guardarTodas(cuentas);
        return "OK";
    }

    public String retirar(String numero, String montoTexto)
                         throws IOException {
        double monto;
        try {
            monto = Double.parseDouble(montoTexto);
        } catch (NumberFormatException e) {
            return "El monto debe ser un numero.";
        }
        if (monto <= 0) { return "El monto debe ser mayor que cero."; }
        ArrayList<Cuenta> cuentas = dao.listar();
        for (Cuenta c : cuentas) {
            if (c.getNumero().equals(numero)) {
                if (c.retirar(monto)) {
                    dao.guardarTodas(cuentas);
                    return "OK";
                }
                return "Fondos insuficientes.";
            }
        }
        return "No existe esa cuenta.";
    }

    public double totalDepositado() throws IOException {
        double total = 0;
        for (Cuenta c : dao.listar()) {
            total = total + c.getSaldo();
        }
        return total;
    }

    public ArrayList<Cuenta> listarPorAsociado(String cedula)
                                              throws IOException {
        ArrayList<Cuenta> propias = new ArrayList<>();
        for (Cuenta c : dao.listar()) {
            if (c.getCedulaAsociado().equals(cedula)) {
                propias.add(c);
            }
        }
        return propias;
    }
}
