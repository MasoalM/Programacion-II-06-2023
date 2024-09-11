/**
FUNCIONALIDAD: CLASE REGISTRO PARTIDA OBJETO FICHEROS LECTURA

ASIGNATURA: Programación II
ESTUDIOS: ING. Informática
AUTORES: Marcos Socías Alberto y Adrián Raya Carmona
FECHA DE CREACIÓN: 05/06/2023
FECHA DE ÚLTIMA MODIFICACIÓN: 08/06/2023
**/

package practicafinalmarcosadri;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class RegistroPartidaObjetoFicherosLectura {
    
    private ObjectInputStream fichero;

    // MÉTODO CONSTRUCTOR
    
    public RegistroPartidaObjetoFicherosLectura(String nombreFichero) throws FileNotFoundException, IOException {
        fichero = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nombreFichero)));
    }

    // MÉTODOS
    
    // Devolvemos un objeto RegistroPartida
    public RegistroPartida lectura() throws IOException, ClassNotFoundException {
        return (RegistroPartida) fichero.readObject();
    }
    
    // Cerramos el enlace del fichero
    public void cerrarEnlaceFichero() throws IOException {
        if(fichero != null) {
            fichero.close();
        }
    }
    
}