/**
FUNCIONALIDAD: CLASE BARRA PROGRESIÓN TEMPORAL
ASIGNATURA: Programación II
ESTUDIOS: ING. Informática
AUTORES: Marcos Socías Alberto y Adrián Raya Carmona
FECHA DE CREACIÓN: 30/05/2023
FECHA DE ÚLTIMA MODIFICACIÓN: 10/06/2023
**/

package practicafinalmarcosadri;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JProgressBar;

public class BarraProgresionTemporal extends JProgressBar {
    
    // ATRIBUTOS
    private int valorMinimo = 0;
    private int valorMaximo = 100;
    private boolean activo = true;
    private static final int ANCHO_BARRA = 40;

    // MÉTODO CONSTRUCTOR
    
    public BarraProgresionTemporal() {
        super();
        
        // Establecemos el mínimo y máximo y el valor por defecto al crear un nuevo objeto
        setMinimum(valorMinimo);
        setMaximum(valorMaximo);
        setValue(0);
        
        // Habilitamos que se vea el porcentaje de la barra y cambiamos la dimensión
        setStringPainted(true);
        setPreferredSize(new Dimension(50, ANCHO_BARRA));
        
        // Ponemos el color del fondo de la barra a amarillo y color de
        // la progresión de la barra a rojo
        setForeground(Color.RED);
        setBackground(Color.YELLOW);
    }

    // MÉTODOS GET Y SET
    
    public int getValorMaximo() {
        return valorMaximo;
    }
    
    public void setValorBarraTemporal(int valor) {
        setValue(valor);
    }
    
    public int getValorBarraTemporal() {
        return getValue();
    }
    
    public static int getAnchoBarra() {
        return ANCHO_BARRA;
    }

    public boolean estaActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
