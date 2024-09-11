/**
FUNCIONALIDAD: CLASE REGISTRO PARTIDA OBJETO FICHEROS ESCRITURA

ASIGNATURA: Programación II
ESTUDIOS: ING. Informática
AUTORES: Marcos Socías Alberto y Adrián Raya Carmona
FECHA DE CREACIÓN: 05/06/2023
FECHA DE ÚLTIMA MODIFICACIÓN: 09/06/2023
**/

package practicafinalmarcosadri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class RegistroPartidaObjetoFicherosEscritura {
    
    private ObjectOutputStream fichero;

    // MÉTODO CONSTRUCTOR
    
    public RegistroPartidaObjetoFicherosEscritura(String nombreFichero) throws FileNotFoundException, IOException {
        fichero = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nombreFichero)));
    }

    // MÉTODOS
    
    // Hacemos la escritura de un objeto RegistroPartida pasado por parámetro
    public void escritura(RegistroPartida partida) throws IOException {
        fichero.writeObject(partida);
    }
    
    public void escrituraRegistroPartida(File f1, File f2, String nombreJugador, String fecha, boolean victoria) throws IOException, ClassNotFoundException {
        RegistroPartida registro;
        RegistroPartida registroEscritura = new RegistroPartida(nombreJugador, fecha, victoria ? 4 : 0);
        RegistroPartidaObjetoFicherosLectura fichero = null;
        
        fichero = new RegistroPartidaObjetoFicherosLectura("resultados.dat");
        
        for(registro = fichero.lectura(); !registro.esCentinela();) {
            escritura(registro);
            registro = fichero.lectura();
        }
        
        if(registro.esCentinela()) {
            escritura(registroEscritura);
        }
        escritura(RegistroPartida.getCentinela());
        
        cerrarEnlaceFichero();
        fichero.cerrarEnlaceFichero();
        
        if(f1.delete()) {
            f2.renameTo(f1);
        }
    }
    
    // Cerramos el enlace del fichero
    public void cerrarEnlaceFichero() throws IOException {
        if(fichero != null) {
            fichero.close();
        }
    }
    
}