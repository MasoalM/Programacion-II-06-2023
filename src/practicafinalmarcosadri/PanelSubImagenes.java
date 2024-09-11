/**
FUNCIONALIDAD: CLASE PANEL SUBIMAGENES
ASIGNATURA: Programación II
ESTUDIOS: ING. Informática
AUTORES: Marcos Socías Alberto y Adrián Raya Carmona
FECHA DE CREACIÓN: 30/05/2023
FECHA DE ÚLTIMA MODIFICACIÓN: 09/06/2023
**/

package practicafinalmarcosadri;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class PanelSubImagenes extends JPanel {
    
    private static int filas;
    private static int columnas;
    
    // MÉTODO CONSTRUCTOR
    
    public PanelSubImagenes(int filas, int columnas) {
        setLayout(new GridLayout(filas, columnas, 1, 1));
        this.filas = filas;
        this.columnas = columnas;
    }
    
    // MÉTODOS GET
    
    public static int getFilas() {
        return filas;
    }

    public static int getColumnas() {
        return columnas;
    }
    
}
