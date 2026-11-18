package coopetica.modelo;

public class CuentaCorriente extends Cuenta {

    private double limiteSobregiro;

    public CuentaCorriente(String numero, String cedulaAsociado,
                           double saldo, double limiteSobregiro) {
        super(numero, cedulaAsociado, saldo);
        this.limiteSobregiro = limiteSobregiro;
    }

    public double getLimiteSobregiro() { return limiteSobregiro; }

    @Override
    public boolean retirar(double monto) {
        if (monto <= getSaldo() + limiteSobregiro) {
            setSaldo(getSaldo() - monto);
            return true;
        }
        return false;
    }

    @Override
    public String getTipo() {
        return "Corriente";
    }

    @Override
    public String toString() {
        return super.toString() + " - sobregiro " + limiteSobregiro;
    }
}
