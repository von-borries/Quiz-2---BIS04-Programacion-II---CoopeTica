# CoopeTica - Modulo de cuentas

Proyecto NetBeans `C3_Progra2_Semana8` (Java 25, Ant).

Proyecto Programado de BIS04 Programacion II, Universidad Latina de Costa Rica.
Hito de la Semana 10: jerarquia de cuentas y manejo de listas de objetos.

Que hace

Maneja dos tipos de cuenta que heredan de una clase padre:

| Clase | Lo propio |
|---|---|
| `Cuenta` | numero, cedula del asociado, saldo, depositar, retirar, equals |
| `CuentaAhorro` | tasa de interes y calculo del interes mensual. No permite sobregiro |
| `CuentaCorriente` | limite de sobregiro. Sobrescribe `retirar` |

Estructura (cuatro capas)

```
src/coopetica/
  modelo/         Cuenta, CuentaAhorro, CuentaCorriente
  datos/          CuentaArchivoDAO         (archivo datos/cuentas.txt)
  logica/         CuentaServicio           (validaciones y reglas)
  presentacion/   PruebaHerencia, PruebaCuentas, PruebaExtensiones, VentanaCuentas
             (Main y VentanaPrincipal son del modulo de asociados)
```

Formato del archivo `datos/cuentas.txt`

La primera letra indica el tipo de cuenta y el ultimo dato cambia segun el tipo:

```
A,001,118450236,350000.0,0.045      <- A = ahorro, ultimo dato: tasa de interes
C,002,118450236,-80000.0,100000.0   <- C = corriente, ultimo dato: limite de sobregiro
```

`guardarTodas` reescribe el archivo completo en cada cambio, por eso siempre recibe la lista completa.

Como compilar y ejecutar

En NetBeans: abra el proyecto y use *Run* (F6) para lanzar `Main`, que abre la ventana de asociados. El boton **Cuentas** abre la ventana de cuentas. Para las pruebas por consola, clic derecho sobre `PruebaHerencia`, `PruebaCuentas` o `PruebaExtensiones` y *Run File* (Shift+F6).

Desde terminal (en la raiz del proyecto, JDK 11 o superior):

bash
mkdir -p out
javac -d out $(find src -name '*.java')

java -cp out coopetica.presentacion.PruebaHerencia
java -cp out coopetica.presentacion.PruebaCuentas
java -cp out coopetica.presentacion.PruebaExtensiones
java -cp out coopetica.presentacion.Main


Los archivos de datos se guardan en `datos/` (`asociados.txt` y `cuentas.txt`), relativo a la raiz del proyecto.

Importante: `PruebaCuentas` espera que `datos/cuentas.txt` no exista o este vacio. Si se corre dos veces, las cuentas ya existen y la salida cambia. Borre el archivo antes de repetir la prueba.

Validacion de cedula: la ventana de cuentas solo abre cuentas para asociados ya registrados (se valida con `AsociadoServicio`). Las pruebas por consola no validan, por eso usan cedulas de ejemplo.

Salida esperada de `PruebaCuentas`

```
OK
OK
Ya existe una cuenta con ese numero.
--- Todas las cuentas ---
Ahorro 001 - asociado 118450236 - saldo 350000.0 - interes mensual 1313
Corriente 002 - asociado 118450236 - saldo 120000.0 - sobregiro 100000.0
Ahorro 001 retira 400000: Fondos insuficientes.
Corriente 002 retira 200000: OK
--- Despues de los retiros ---
Ahorro 001 - asociado 118450236 - saldo 350000.0 - interes mensual 1313
Corriente 002 - asociado 118450236 - saldo -80000.0 - sobregiro 100000.0
Total depositado: 270000.0
```


Prof. Bryan Alejandro Vega Rondon - Cuatrimestre C3-2026
