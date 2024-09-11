/**
FUNCIONALIDAD: CLASE REGISTRO PARTIDA

ASIGNATURA: Programación II
ESTUDIOS: ING. Informática
AUTORES: Marcos Socías Alberto y Adrián Raya Carmona
FECHA DE CREACIÓN: 05/06/2023
FECHA DE ÚLTIMA MODIFICACIÓN: 11/06/2023
**/

package practicafinalmarcosadri;

import java.io.Serializable;

public class RegistroPartida implements Serializable {
    
    // ATRIBUTOS
    private final int MAXIMOS_CARACTERES_NOMBRE = 20;
    private char[] caracteres;
    private String fecha;
    private int puntos;
    private static final RegistroPartida CENTINELA = new RegistroPartida("", "", Integer.MAX_VALUE);

    // MÉTODO CONSTRUCTOR
    
    public RegistroPartida(String nombre, String fecha, int puntos) {
        this.caracteres = nombre.toCharArray();
        this.fecha = fecha;
        this.puntos = puntos;
    }

    // Método toString que pasa a String un objeto RegistroPartida (con sus atributos)
    @Override
    public String toString() {
        return "JUGADOR: " + getNombreEspacios() + "  -  FECHA: " + fecha + "  -  PUNTOS: " + puntos + " puntos.\n";
    }
    
    // MÉTODOS GET
    
    public boolean esCentinela() {
        return puntos == CENTINELA.puntos;
    }
    
    public String getNombreEspacios() {
        String str = "";
        
        // Añade los caracteres a una string hasta llegar al máximo 
        // de caracteres o al final de la array de caracteres
        for(int i = 0; i < caracteres.length && i < MAXIMOS_CARACTERES_NOMBRE; i++) {
            str += caracteres[i];
        }
        
        // Agregamos espacios en blanco para completar el número máximo de
        // caracteres para la posterior visualización en el historial.
        if(caracteres.length < MAXIMOS_CARACTERES_NOMBRE) {
            for(int i = caracteres.length; i < MAXIMOS_CARACTERES_NOMBRE; i++) {
                str += "  ";
            }
        }
        
        return str;
    }
    
    // Obtiene el String nombre de una cadena de caracteres
    public String getNombre() {
        String str = "";
        
        for(int i = 0; i < caracteres.length; i++) {
            str += caracteres[i];
        }
        
        return str;
    }
    
    public static RegistroPartida getCentinela() {
        return CENTINELA;
    }
    
}