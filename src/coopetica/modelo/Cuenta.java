package coopetica.modelo;

public class Cuenta {

    private String numero;
    private String cedulaAsociado;
    private double saldo;

    public Cuenta(String numero, String cedulaAsociado, double saldo) {
        this.numero = numero;
        this.cedulaAsociado = cedulaAsociado;
        this.saldo = saldo;
    }

    public String getNumero()         { return numero; }
    public String getCedulaAsociado() { return cedulaAsociado; }
    public double getSaldo()          { return saldo; }

    protected void setSaldo(double saldo) { this.saldo = saldo; }

    public void depositar(double monto) {
        saldo = saldo + monto;
    }

    public boolean retirar(double monto) {
        if (monto <= saldo) {
            saldo = saldo - monto;
            return true;
        }
        return false;
    }

    public String getTipo() {
        return "Cuenta";
    }

    @Override
    public String toString() {
        return getTipo() + " " + numero + " - asociado " + cedulaAsociado
             + " - saldo " + saldo;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Cuenta)) {
            return false;
        }
        Cuenta c = (Cuenta) otro;
        return this.numero.equals(c.numero);
    }
}
