/**
FUNCIONALIDAD: CLASE IMAGEN
ASIGNATURA: Programación II
ESTUDIOS: ING. Informática
AUTORES: Marcos Socías Alberto y Adrián Raya Carmona
FECHA DE CREACIÓN: 30/05/2023
FECHA DE ÚLTIMA MODIFICACIÓN: 11/06/2023
**/

package practicafinalmarcosadri;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Imagen extends JLabel {
    
    // ATRIBUTOS
    private BufferedImage imagen = null;
    private int filas = PanelSubImagenes.getFilas();
    private int columnas = PanelSubImagenes.getColumnas();
    
    // MÉTODO CONSTRUCTOR
    
    public Imagen(String nombre, Dimension dimensionPanel, File archivoImagen) {
        try {
            // Ponemos el icono con una imagen redimensionada a las dimensiones necesarias
            setIcon(new ImageIcon(new ImageIcon(archivoImagen.getAbsolutePath()).getImage()
                    .getScaledInstance((int) dimensionPanel.getWidth(), (int) dimensionPanel.getHeight(), Image.SCALE_SMOOTH)));
            setSize(dimensionPanel);
            
            // Cogemos la imagen
            imagen = ImageIO.read(archivoImagen);
            // Redimensionamos la imagen para ajustarla a las dimensiones necesarias
            imagen = redimensionar(imagen, (int) dimensionPanel.getWidth(), (int) dimensionPanel.getHeight() - BarraProgresionTemporal.getAnchoBarra() - 12);
        } catch(IOException e) {
            System.err.println("ERROR: No se ha podido hacer la lectura de la imagen.");
        }
    }
    
    // MÉTODOS
    
    public void generacionImagenesRecortes(String nombreImagen) {
        int y = 0;
        int k = 0;
        
        // Recorremos las filas
        for(int i = 0; i < filas; i++) {
            int x = 0;
            
            // Recorremos las columnas
            for(int j = 0; j < columnas; j++) {
                // Obtener una subimagen de la imagen original según las coordenadas, dimensiones de recorte y
                // filas y columnas para ajustarlo a las necesidades de la partida
                BufferedImage imagenSalida = imagen.getSubimage(x, y, imagen.getWidth() / columnas, imagen.getHeight() / filas);
                
                // Guardamos la subimagen
                try {
                    ImageIO.write(imagenSalida, "jpg", new File("recortes/recorte" + nombreImagen + k));
                } catch(IOException error) {
                    System.err.println("ERROR: No se ha grabando la imagen: " + nombreImagen);
                }
                
                // Actualizamos la coordenada x
                x += imagen.getWidth() / columnas;
                k++;
            }
            
            // Actualizamos la coordenada y
            y += imagen.getHeight() / filas;
        }
    }
    
    public BufferedImage redimensionar(BufferedImage imagenOriginal, int ancho, int alto) throws IOException {
        // Crea una nueva imagen redimensionada con las dimensiones especificadas
        BufferedImage imagenRedimensionada = new BufferedImage(ancho, alto, imagenOriginal.getType());

        // Crea un objeto Graphics2D para dibujar en la imagen redimensionada
        Graphics2D g2d = imagenRedimensionada.createGraphics();
        // Dibuja la imagen original en la imagen redimensionada, ajustando su tamaño al ancho y alto especificados
        g2d.drawImage(imagenOriginal, 0, 0, ancho, alto, null);

        return imagenRedimensionada;
    }
    
}  
