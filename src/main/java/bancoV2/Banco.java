/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
*/

package bancoV2;

import java.util.*;

/**
 * @author Fer
*/

public class Banco {

    private String nombre;
    private static ArrayList<Cuenta> cuentas;
    private int numeroCuentas;
    private static final int MAX_CUENTAS = 100;
    
    // Inicio Constructor
    
    public Banco(String nombre) {
        this.nombre = nombre;
        Banco.cuentas = new ArrayList<>();
        Banco.cuentas.ensureCapacity(MAX_CUENTAS);
        this.numeroCuentas = 0;
    }
    
    // Fin Constructor
    
    // Inicio Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }

    public static void setCuentas(ArrayList<Cuenta> cuentas) {
        Banco.cuentas = cuentas;
    }

    public int getNumeroCuentas() {
        return numeroCuentas;
    }

    public void setNumeroCuentas(int numeroCuentas) {
        this.numeroCuentas = numeroCuentas;
    }

    // Fin Getters y Setters
    
    // Inicio Metodos Publicos
    public boolean agregarCuenta(String codigo, String dni, String nombreTitular, String correo) {
        if (this.numeroCuentas >= MAX_CUENTAS) {
            return false;
        } else {
            Banco.cuentas.add(new Cuenta(codigo, dni ,nombreTitular, correo));
            numeroCuentas++;
            return true;
        }
    }
    
    public void cargarDatos() {
        String iban, dni, titular, correo;

        // Itera hasta el número actual de cuentas agregadas, no hasta la capacidad máxima
        for (int i = 0; i <= MAX_CUENTAS; i++) {
            iban = String.format("IBAN%03d", (i + 1));
            dni = String.format("%08dA", (i + 1));
            titular = String.format("Titular%03d", (i + 1));
            correo = String.format("ejemplo%03d@gmail.com", (i + 1));

            agregarCuenta(iban, dni, titular, correo);
        }
    }
    
    public String consultarCuenta(String codigo) {
        StringBuilder consulta = new StringBuilder();

        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas.get(i).getIban().equals(codigo)) {
                consulta.append("IBAN: ").append(codigo).append("\t\t").append("Titular: ").append(cuentas.get(i).getTitular()).append("\t\t").append(cuentas.get(i).getDni()).append("\t\t").append(cuentas.get(i).getCorreo()).append("\t\t").append("Saldo: ").append(cuentas.get(i).getSaldo());
                
                return consulta.toString();
            }
        }

        consulta.append("La cuenta que has indicado no se ha encontrado");
        return consulta.toString();
    }
    
    public void borrarCuenta(String codigo) throws Exception {
        boolean encontrada = false;

        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getIban().equals(codigo)) {
                cuentas.remove(i);
                
                encontrada = true;
            }
        }

        if (encontrada == false) {
            throw new Exception("La cuenta con IBAN " + codigo + " no existe en el banco " + nombre);
        } else {
            numeroCuentas--; // Restar 1 al contador de cuentas si se ha eliminado una cuenta
        }
    }
    
    public boolean existeCuenta(String codigo) {       
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas.get(i).getIban().equals(codigo)) { // obtiene el iban de la posicion de cuenta y lo compara con el parametro que se le pasa al metodo
                return true;
            }
        }
        
        return false;
    }
    
    public String listadoCuentas() {
        StringBuilder listado = new StringBuilder();
        
        listado.append("Total de cuentas: ").append(numeroCuentas).append("\n");
        
        for (int i = 0; i < numeroCuentas; i++) {
            // esto se hace como cuando lo del ejercicio de las interfaces de usuario (creo xd)
            listado.append("IBAN: ").append(cuentas.get(i).getIban()).append("\t\t").append("Titular: ").append(cuentas.get(i).getTitular()).append("\t\t").append(cuentas.get(i).getDni()).append("\t\t").append(cuentas.get(i).getCorreo()).append("\t\t").append("Saldo: ").append(cuentas.get(i).getSaldo()).append("\n"); // lo siguiente junta todo con el append
        }
        
        return listado.toString();
    }
    
    public double informaDeSaldo(String iban) {
        if (existeCuenta(iban)) {
            for (int i = 0; i < numeroCuentas; i++) {
                if (cuentas.get(i).getIban().equals(iban)) {
                    return cuentas.get(i).getSaldo(); // Devuelve el saldo de la cuenta si existe
                }
            }
        }
        
        return -100000000; // Devuelve -100.000.000 si la cuenta no existe
    }
    
    public boolean ingresar(String codigo, double importe) {
        Cuenta cuenta = localizarCuenta(codigo);
        
        if (cuenta == null) {
            return false;
        } else {
            cuenta.ingresarDinero(importe);
            return true;
        }
    }
    
    public boolean retirar(String codigo, double importe) {
        Cuenta cuenta = localizarCuenta(codigo);
        
        if (cuenta == null) {
            return false;
        } else {
            cuenta.retirarDinero(importe);
            return true;
        }
    }
    
    // Este metodo ya es por mi cuenta
//    public String comprobarCuentasPorDNI(String codigo) {
//        
//    }
    
    // Fin Metodos Publicos
    
    
    // Inicio Metodos Privados
    
    // no se para que quiere este metodo xd
    private Cuenta localizarCuenta(String codigo) {
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas.get(i).getIban().equals(codigo)) {
                return cuentas.get(i); // Devolver la cuenta encontrada
            }
        }
        return null; // Devolver null si la cuenta no se encuentra
    }
    
    // Fin Metodos Privados
}
