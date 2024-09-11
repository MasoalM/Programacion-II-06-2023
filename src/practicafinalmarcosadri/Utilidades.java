/**
FUNCIONALIDAD: CLASE UTILIDADES
ASIGNATURA: Programación II
ESTUDIOS: ING. Informática
AUTORES: Marcos Socías Alberto y Adrián Raya Carmona
FECHA DE CREACIÓN: 11/06/2023
FECHA DE ÚLTIMA MODIFICACIÓN: 11/06/2023
**/

package practicafinalmarcosadri;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTextField;

public class Utilidades {
    
    // Método público static booleano "stringsIguales" que recibe dos Strings por parámetro para compararlos, 
    // si son iguales devolerá true, sino false.
    public static boolean stringsIguales(String str1, String str2) {
        // Si la longitud de los Strings es distinta, devolver false
        if(str1.length() != str2.length()) {
            return false;
        }
        // Mientras i sea menor a la longitud del primer string (ya que mide igual que el segundo), 
        // incrementar i en uno en cada iteración.
        for(int i = 0; i < str1.length(); i++) {
            // Si el carácter en la posición i del primer String, es distinto al de la misma posición 
            // en el segundo String, devolver false, ya que no serían iguales.
            if(str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
        }
        // Si ha pasado las dos pruebas (miden igual y coinciden todos sus caracteres), devolver true.
        return true;
    }
    
    // Método público static entero "generarNumeroRecorte" que le asigna un número a un recorte, para que esa sea su posición. 
    // Recibe por parámetro el array de números ya generados, el índice que toca por rellenar del array 
    // y las filas y columnas que tendrá el puzzle.
    public static int generarNumeroRecorte(int[] array, int indice, final int FILAS_COLUMNAS) {
        // Generador Random
        Random generador = new Random();
        // Generar un número aleatorio de 0 a FILAS_COLUMNAS - 1.
        int num = generador.nextInt(FILAS_COLUMNAS);
        // Mientras ese número esté en el array, volver a generar otro número para no repetirlos.
        while(contieneNumero(array, num)) {
            num = generador.nextInt(FILAS_COLUMNAS);
        }
        // Almacenar el número final y devolverlo.
        array[indice] = num;
        return num;
    }
    
    // Método privado static booleano "contieneNumero", que comprueba si un número está
    // contenido en un array, si es así, devuelve true, sino, devuelve false.
    private static boolean contieneNumero(int[] array, int numero) {
        for(int i = 0; i < array.length; i++) {
            // Si la posición i del array es igual al número buscado, devolver true.
            if(array[i] == numero) {
                return true;
            }
        }
        return false;
    }
    
    // Método público static "vaciarArray" que vacía un array de enteros poniendo 
    // todos sus elementos a -1.
    public static void vaciarArray(int[] array) {
        for(int i = 0; i < array.length; i++) {
            array[i] = -1;
        }
    }
    
    // Método público static "vaciarArray" que vacía un array de objetos poniendo 
    // todos sus elementos a null.
    public static void vaciarArray(Object[] array) {
        for(int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }
    
    // Método que devuelve una array de Files si son ".png" o ".jpg".
    private static File[] cogerImagenesCarpeta(String carpeta) {
        File[] archivos = new File(carpeta).listFiles();
        ArrayList<File> imagenes = new ArrayList<>();
        String extension = "";
        
        // Bucle para iterar sobre todos los archivos encontrados.
        for(int i = 0; i < archivos.length; i++) {
            String nombre = archivos[i].getName();
            int longitud = nombre.length();
            
            // Buscar la extensión de los archivos encontrados.
            extension = "" + nombre.charAt(longitud - 3) + nombre.charAt(longitud - 2) + nombre.charAt(longitud - 1);
            // Si su extensión es la deseada, se añade a la ArrayList de imagenes.
            if(stringsIguales(extension, "png") || stringsIguales(extension, "jpg")) {
                imagenes.add(archivos[i]);
            }
        }
        
        // Devolvemos la ArrayList con forma de Array usando el método toArray().
        return imagenes.toArray(new File[imagenes.size()]);
    }
    
    // Método que cuenta cuántas fotos ".png" o ".jpg" hay en una carpeta.
    public static int contarImagenesCarpetas(String carpeta) {
        // Array que almacena todas las imagenes de una carpeta.
        File[] imagenes = cogerImagenesCarpeta(carpeta);
        
        // Devolver el valor de la cantidad de imagenes en la array.
        return imagenes.length;
    }
    
    // Método que devuelve la foto seleccionada según el número introducido por parámetro.
    public static File seleccionImagen(String carpeta, int numeroDeFoto) {
        File[] imagenes = cogerImagenesCarpeta(carpeta);
        File imagen = null;
        
        for(int i = 0; i < imagenes.length && i < numeroDeFoto; i++) {
            imagen = imagenes[i];
        }
        
        return imagen;
    }
    
    // Método público static "vaciarCarpetaRecortes" que se encarga de eliminar
    // todos los archivos de la carpeta "recortes". Esos archivos son los recortes
    // de las imágenes en las partidas. 
    public static void vaciarCarpetaRecortes(String carpeta) {
        // Array de archivos que guarda todos los archivos de la carpeta.
        File[] archivos = new File(carpeta).listFiles();
        
        // Bucle que elimina todos los archivos.
        for(int i = 0; i < archivos.length; i++) {
            archivos[i].delete();
        }
    }
    
    // Método público static "creacionPrimerFichero" que recibe dos ficheros por parámetro y el nombre 
    // del fichero de resultados. Si no existe un primer fichero, simplemente lo crea.
    public static void creacionPrimerFichero(File f1, String nombreFicheroResultados) {
        if(!f1.exists()) {
            RegistroPartidaObjetoFicherosEscritura fichero = null;
            
            try {
                // Instanciación del enlace con el fichero de resultados (y creación).
                fichero = new RegistroPartidaObjetoFicherosEscritura(nombreFicheroResultados);
                try {
                    // Método para escribir el centinela.
                    fichero.escritura(RegistroPartida.getCentinela());
                } catch(IOException error) {
                    System.err.println("ERROR: NO SE HA PODIDO REALIZAR LA ESCRITURA DE DATOS");
                } finally {
                    try {
                        fichero.cerrarEnlaceFichero();
                    } catch(IOException error) {
                        System.err.println("ERROR: NO SE HA PODIDO CERRAR EL ENLACE DEL FICHERO");
                    }
                }
            } catch(IOException error) {
                System.err.println("ERROR: NO SE HA PODIDO CREAR EL FICHERO");
            }
        }
    }
    
    // Método público static booleano "comprobacionDatos" que comprueba si los datos introducidos 
    // al empezar una partida son correctos, en ese caso, devolvería true, si no, false.
    public static boolean comprobacionDatos(JTextField t1, JTextField t2, JTextField t3) {
        // Coger el texto introducido por el usuario.
        String nombreJugador = t1.getText();
        String subDivisionesH = t2.getText();
        String subDivisionesV = t3.getText();
        
        // Si alguno de los parámetros está vacío, es decir, tiene longitud 0, se devuelve false,
        // por tanto, no son datos correctos.
        if(nombreJugador.length() == 0 || subDivisionesH.length() == 0 || subDivisionesV.length() == 0) {
            return false;
        }
        
        // Pasar los números a int para utilizarlos, si no son números, dará un error que controlaremos
        // y devolveremos false.
        try {
            int numH = Integer.parseInt(t2.getText());
            int numV = Integer.parseInt(t3.getText());
        } catch(NumberFormatException error) {
            return false;
        }
        
        // Si ha pasado todas las comprobaciones correctamente, devolver false.
        return true;
    }
    
}
