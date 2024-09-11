/**
FUNCIONALIDAD: CLASE SUBIMAGEN
ASIGNATURA: Programación II
ESTUDIOS: ING. Informática
AUTORES: Marcos Socías Alberto y Adrián Raya Carmona
FECHA DE CREACIÓN: 30/05/2023
FECHA DE ÚLTIMA MODIFICACIÓN: 11/06/2023
**/

package practicafinalmarcosadri;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SubImagen extends JLabel {
    
    // ATRIBUTOS
    private int recorte;
    
    // MÉTODO CONSTRUCTOR
    
    public SubImagen(String nombre, int numeroRecorte) {
        recorte = numeroRecorte;
        
        // Se crea una ImageIcon utilizando el archivo de imagen y se le
        // establece al icono de la clase extendida
        setIcon(new ImageIcon("recortes/recorte" + nombre + recorte));   
    }
    
    // MÉTODO GET
    
    public int getRecorte() {
        return recorte;
    }
    
}
