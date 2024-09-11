/********************************************
FUNCIONALIDAD: PROGRAMA PRINCIPAL

ASIGNATURA: Programación II
ESTUDIOS: ING. Informática
AUTORES: Marcos Socías Alberto y Adrián Raya Carmona
FECHA DE CREACIÓN: 29/04/2023
FECHA DE ÚLTIMA MODIFICACIÓN: 11//06/2023
*********************************************/

package practicafinalmarcosadri;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import javax.swing.*;
import static javax.swing.JOptionPane.showConfirmDialog;

public class ProgramaPrincipal extends JFrame {
    
    private final int DIMENSION_BARRA = 100, FACTOR_VELOCIDAD = 4, MINIMO_FILAS_COLUMNAS = 2, MAXIMO_FILAS_COLUMNAS = 5;
    private final String[] NOMBRES_COMPONENTES = {"NUEVA PARTIDA", "HISTORIAL GENERAL", "HISTORIAL SELECTIVO", "SALIR", "CAMBIAR DIRECTORIO DE IMÁGENES"};
    private JPanel panelVisualizaciones = new JPanel(new CardLayout()), panelPartida, panelImagenSolucion;
    private JDialog introduccionDatos;
    private JMenuItem nuevaPartidaMenu, historialGeneral, historialSelectivo, cambiarDirectorioMenu, salirMenu;
    private PanelSubImagenes panelSubImagenes;
    private JLabel imagenUIB;
    private JButton nuevaPartidaIcono, historialGeneralIcono, historialSelectivoIcono, cambiarDirectorioIcono, salirIcono;
    private JTextField nombreJugadorIntroduccion, subDivisionesHorizontalIntroduccion, subDivisionesVerticalIntroduccion, introduccionNombreJugador;
    private JTextArea areaVisualizacionResultados;
    private String nombreJugador, nombreBuscar, nombreImagen, fecha, nombreCarpetaImagenes = "imagenes/", nombreCarpetaRecortes = "recortes/", 
            nombreFicheroResultados = "resultados.dat", nombreFicheroResultadosTemp = "resultadosTemp.dat";
    private int cantidadImagenes, numeroFilas, numeroColumnas, pos, velocidad;
    private int[] numeroRecorteAsignado;
    private CardLayout localVisualizaciones = (CardLayout) (panelVisualizaciones.getLayout());
    private BarraProgresionTemporal barraTemporal;
    private Timer cronometro;
    private SubImagen[] subImagenes;
    private boolean primerClick = false, partidaEnCurso = false;
    private File f1 = new File(nombreFicheroResultados), f2 = new File(nombreFicheroResultadosTemp);
    
    public ProgramaPrincipal() {
        //título de la ventana
        super("PRÁCTICA - PROGRAMACIÓN II - 2022-2023 - UIB");
        
        Container panelContenidos = getContentPane();
        panelContenidos.setLayout(new BorderLayout());
        
        panelPartida = new JPanel();
        panelPartida.setLayout(new BorderLayout());
        panelPartida.setBackground(Color.BLACK);
        JPanel panelStandBy = new JPanel();
        panelStandBy.setLayout(new GridLayout());
        
        JSplitPane separadorNorte = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JSplitPane separadorCentral = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane separadorSur = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        JPanel panelHistorial = new JPanel();
        panelHistorial.setLayout(new GridLayout());
        Font fuente = new Font("Arial", Font.BOLD, 28);
        areaVisualizacionResultados = new JTextArea();
        areaVisualizacionResultados.setEditable(false);
        areaVisualizacionResultados.setFont(fuente);
        JScrollPane scrollPane = new JScrollPane(areaVisualizacionResultados);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        panelImagenSolucion = new JPanel();
        panelImagenSolucion.setLayout(new BorderLayout());
        
        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setBackground(Color.BLACK);
        
        JMenu menu = new JMenu("MENÚ");
        menu.setForeground(Color.WHITE);
        nuevaPartidaMenu = new JMenuItem(NOMBRES_COMPONENTES[0]);
        nuevaPartidaMenu.setBackground(Color.BLACK);
        nuevaPartidaMenu.setForeground(Color.WHITE);
        nuevaPartidaMenu.addActionListener(new GestorEventoBotones());
        historialGeneral = new JMenuItem(NOMBRES_COMPONENTES[1]);
        historialGeneral.setBackground(Color.BLACK);
        historialGeneral.setForeground(Color.WHITE);
        historialGeneral.addActionListener(new GestorEventoBotones());
        historialSelectivo = new JMenuItem(NOMBRES_COMPONENTES[2]);
        historialSelectivo.setBackground(Color.BLACK);
        historialSelectivo.setForeground(Color.WHITE);
        historialSelectivo.addActionListener(new GestorEventoBotones());
        cambiarDirectorioMenu = new JMenuItem(NOMBRES_COMPONENTES[4]);
        cambiarDirectorioMenu.setBackground(Color.BLACK);
        cambiarDirectorioMenu.setForeground(Color.WHITE);
        cambiarDirectorioMenu.addActionListener(new GestorEventoBotones());
        salirMenu = new JMenuItem(NOMBRES_COMPONENTES[3]);
        salirMenu.setBackground(Color.BLACK);
        salirMenu.setForeground(Color.WHITE);
        salirMenu.addActionListener(new GestorEventoBotones());
        
        JToolBar iconosMenu = new JToolBar();
        iconosMenu.setFloatable(false);
        iconosMenu.setBackground(Color.BLACK);
        
        nuevaPartidaIcono = new JButton();
        nuevaPartidaIcono.setIcon(new ImageIcon("iconos/iconoNuevaPartida.jpg"));
        nuevaPartidaIcono.setBackground(Color.BLACK);
        nuevaPartidaIcono.addActionListener(new GestorEventoComponentes());
        historialGeneralIcono = new JButton();
        historialGeneralIcono.setIcon(new ImageIcon("iconos/iconoHistorialGeneral.jpg"));
        historialGeneralIcono.setBackground(Color.BLACK);
        historialGeneralIcono.addActionListener(new GestorEventoComponentes());
        historialSelectivoIcono = new JButton();
        historialSelectivoIcono.setIcon(new ImageIcon("iconos/iconoHistorialSelectivo.jpg"));
        historialSelectivoIcono.setBackground(Color.BLACK);
        historialSelectivoIcono.addActionListener(new GestorEventoComponentes());
        cambiarDirectorioIcono = new JButton();
        cambiarDirectorioIcono.setIcon(new ImageIcon("iconos/iconoCambiarDirectorio.jpg"));
        cambiarDirectorioIcono.setBackground(Color.BLACK);
        cambiarDirectorioIcono.addActionListener(new GestorEventoComponentes());
        salirIcono = new JButton();
        salirIcono.setIcon(new ImageIcon("iconos/iconoSalir.jpg"));
        salirIcono.setBackground(Color.BLACK);
        salirIcono.addActionListener(new GestorEventoComponentes());
        
        
        JButton[] botones = new JButton[NOMBRES_COMPONENTES.length - 1];
        JPanel panelBotones = new JPanel(new GridLayout(botones.length, 1));
        for(int i = 0; i < botones.length; i++) {
            botones[i] = new JButton(NOMBRES_COMPONENTES[i]);
            botones[i].addActionListener(new GestorEventoBotones());
            botones[i].setBackground(Color.BLACK);
            botones[i].setForeground(Color.WHITE);
            panelBotones.add(botones[i]);
        }
        
        //METER TODO EN SUS RESPECTIVOS CONTENEDORES
        
        panelHistorial.add(scrollPane);
        
        
        menu.add(nuevaPartidaMenu);
        menu.add(historialGeneral);
        menu.add(historialSelectivo);
        menu.add(cambiarDirectorioMenu);
        menu.add(salirMenu);
        
        barraMenu.add(menu);
        
        iconosMenu.add(nuevaPartidaIcono);
        iconosMenu.add(historialGeneralIcono);
        iconosMenu.add(historialSelectivoIcono);
        iconosMenu.add(cambiarDirectorioIcono);
        iconosMenu.add(salirIcono);
        
        panelVisualizaciones.add(panelPartida, "PANEL PARTIDA");
        panelVisualizaciones.add(panelImagenSolucion, "PANEL IMAGEN SOLUCIÓN");
        panelVisualizaciones.add(panelStandBy, "PANEL STANDBY");
        panelVisualizaciones.add(panelHistorial, "PANEL HISTORIAL");
        
        
        separadorCentral.add(panelBotones);
        separadorCentral.add(panelVisualizaciones);
        
        separadorNorte.add(iconosMenu);
        separadorNorte.add(separadorCentral);
        
        panelContenidos.add(barraMenu, BorderLayout.NORTH);
        panelContenidos.add(separadorNorte, BorderLayout.CENTER);
        panelContenidos.add(separadorSur, BorderLayout.SOUTH);
        
        setResizable(false);
        setDefaultCloseOperation (EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        
        imagenUIB = new JLabel();
        imagenUIB.setIcon(new ImageIcon(new ImageIcon("UIB.jpg").getImage()
                .getScaledInstance((int) panelVisualizaciones.getWidth(), (int) panelVisualizaciones.getHeight(), Image.SCALE_SMOOTH)));
                
        panelStandBy.add(imagenUIB);
        
        localVisualizaciones.show(panelVisualizaciones, "PANEL STANDBY");
    }
    
    private void introduccionDatos() {
        // DECLARACIONES
        final int DIMENSION_X = 600;
        final int DIMENSION_Y = 150;
        
        // ACCIONES
        introduccionDatos = new JDialog(this, "INTRODUCCIÓN DATOS");
        introduccionDatos.setLayout(new BorderLayout());
        
        Container panelContenidosIntroduccionDatos = introduccionDatos.getContentPane();
        
        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new GridLayout(3, 1));
        panelTexto.setPreferredSize(new Dimension(DIMENSION_X / 2 - 16, DIMENSION_Y));
        panelTexto.setBackground(Color.BLACK);
        Font fuente = new Font("Arial", Font.BOLD, 13);
        JLabel nombreJugador = new JLabel("NOMBRE JUGADOR");
        nombreJugador.setFont(fuente);
        nombreJugador.setForeground(Color.WHITE);
        JLabel subDivisionesHorizontal = new JLabel("NÚMERO DE SUBDIVISIONES HORIZONTAL");
        subDivisionesHorizontal.setFont(fuente);
        subDivisionesHorizontal.setForeground(Color.WHITE);
        JLabel subDivisionesVertical = new JLabel("NÚMERO DE SUBDIVISIONES VERTICAL");
        subDivisionesVertical.setFont(fuente);
        subDivisionesVertical.setForeground(Color.WHITE);
        
        panelTexto.add(nombreJugador);
        panelTexto.add(subDivisionesHorizontal);
        panelTexto.add(subDivisionesVertical);
        
        JPanel panelIntroduccion = new JPanel();
        panelIntroduccion.setLayout(new GridLayout(3, 1));
        panelIntroduccion.setPreferredSize(new Dimension(DIMENSION_X / 2, DIMENSION_Y));
        nombreJugadorIntroduccion = new JTextField();
        subDivisionesHorizontalIntroduccion = new JTextField();
        subDivisionesVerticalIntroduccion = new JTextField();
        
        panelIntroduccion.add(nombreJugadorIntroduccion);
        panelIntroduccion.add(subDivisionesHorizontalIntroduccion);
        panelIntroduccion.add(subDivisionesVerticalIntroduccion);
        
        JButton confirmar = new JButton("CONFIRMAR");
        confirmar.addActionListener(new GestorEventoBotones());
        
        panelContenidosIntroduccionDatos.add(panelTexto, BorderLayout.WEST);
        panelContenidosIntroduccionDatos.add(panelIntroduccion, BorderLayout.EAST);
        panelContenidosIntroduccionDatos.add(confirmar, BorderLayout.SOUTH);
        
        introduccionDatos.pack();
        introduccionDatos.setSize(DIMENSION_X, DIMENSION_Y);
        introduccionDatos.setLocationRelativeTo(null);
        introduccionDatos.setVisible(true);
    }
    
    // Mostramos un showConfirmDialog en el cual nos servirá para solicitar
    // el nombre para usar el historial selectivo
    private void introduccionNombre() {
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("Panel.background", Color.BLACK);
        
        int seleccion = showConfirmDialog(null, panelIntroduccionNombre(), 
                "INTRODUCCIÓN DE NOMBRE A BUSCAR", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if(seleccion == JOptionPane.OK_OPTION) {
            nombreBuscar = introduccionNombreJugador.getText();
            historialSelectivo();
        }
    }
    
    // Panel para visualizar dentro del JOptionPane para introducir el
    // nombre en la búsqueda selectiva.
    private JPanel panelIntroduccionNombre() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.setBackground(Color.BLACK);
        
        Font fuente = new Font("Arial", Font.BOLD, 13);
        JLabel historialSelectivo = new JLabel("HISTORIAL SELECTIVO");
        historialSelectivo.setFont(fuente);
        historialSelectivo.setForeground(Color.YELLOW);
        JLabel nombreJugador = new JLabel("INTRODUCIR NOMBRE DEL JUGADOR");
        nombreJugador.setFont(fuente);
        nombreJugador.setForeground(Color.YELLOW);
        
        introduccionNombreJugador = new JTextField();
        
        panel.add(historialSelectivo);
        panel.add(nombreJugador);
        panel.add(introduccionNombreJugador);
        
        return panel;
    }
    
    private void partidaIniciada() {
        // DECLARACIONES
        Random generador = new Random();
        numeroRecorteAsignado = new int[numeroFilas * numeroColumnas];
        subImagenes = new SubImagen[numeroFilas * numeroColumnas];
        fecha = new SimpleDateFormat("HH:mm dd/MM/yy").format(Calendar.getInstance().getTime());
        
        // ACCIONES
        Utilidades.creacionPrimerFichero(f1, nombreFicheroResultados);
        
        partidaEnCurso = true;
        cantidadImagenes = Utilidades.contarImagenesCarpetas(nombreCarpetaImagenes);
        File imagen = Utilidades.seleccionImagen(nombreCarpetaImagenes, generador.nextInt(1, cantidadImagenes + 1));
        nombreImagen = imagen.getName();
        Utilidades.vaciarArray(numeroRecorteAsignado);
        
        panelSubImagenes = new PanelSubImagenes(numeroFilas, numeroColumnas);
        panelSubImagenes.setBackground(Color.BLACK);
        
        Imagen imagenPartida = new Imagen(nombreImagen, panelVisualizaciones.getSize(), imagen);
        imagenPartida.generacionImagenesRecortes(nombreImagen);
        
        generarSubImagenes();
        
        JButton botonContinuar = new JButton("CONTINUAR");
        botonContinuar.setBackground(Color.BLACK);
        botonContinuar.setForeground(Color.WHITE);
        botonContinuar.addActionListener(new GestorEventoBotones());
        
        panelImagenSolucion.add(imagenPartida, BorderLayout.CENTER);
        panelImagenSolucion.add(botonContinuar, BorderLayout.SOUTH);
        
        barraTemporal = new BarraProgresionTemporal();
        barraTemporal.setValorBarraTemporal(0);
        
        cronometro = new Timer(800, new GestorEventoComponentes());
        cronometro.start();
        
        JSplitPane separador = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        separador.setBottomComponent(barraTemporal);
        
        panelPartida.add(panelSubImagenes, BorderLayout.CENTER);
        panelPartida.add(separador, BorderLayout.SOUTH);
        
        localVisualizaciones.show(panelVisualizaciones, "PANEL PARTIDA"); 
    }
    
    
    private void historialGeneral() {
        RegistroPartidaObjetoFicherosLectura fichero;
        RegistroPartida registro;
                        
        try {
            fichero = new RegistroPartidaObjetoFicherosLectura(nombreFicheroResultados); 
            localVisualizaciones.show(panelVisualizaciones, "PANEL HISTORIAL");
            areaVisualizacionResultados.setText("\t\tHISTORIAL GENERAL\n\n");
            try {
                for(registro = fichero.lectura(); !registro.esCentinela();) {
                    areaVisualizacionResultados.setText(areaVisualizacionResultados.getText() + registro.toString());
                    registro = fichero.lectura();
                }
            } catch(IOException error) {
                System.err.println("ERROR: ERROR EN LA LECTURA DE OBJETOS 'Registro Partida' EN EL FICHERO " + nombreFicheroResultados);
            } catch(ClassNotFoundException error) {
                System.err.println("ERROR: NO SE HA PODIDO REALIZAR LA LECTURA DE DATOS");
            } finally {
                try {
                    fichero.cerrarEnlaceFichero();
                } catch(IOException error) {
                    System.err.println("ERROR: ERROR EN EL CIERRE DEL ENLACE CON EL FICHERO");
                }
            }
        } catch(IOException error) {
            visualizarError("ERROR", "No has jugado ninguna partida, por lo\ntanto, no hay partidas para visualizar aún.");
        }
    }
    
    private void historialSelectivo() {
        RegistroPartidaObjetoFicherosLectura fichero;
        RegistroPartida registro;
        
        try {
            fichero = new RegistroPartidaObjetoFicherosLectura(nombreFicheroResultados); 
            localVisualizaciones.show(panelVisualizaciones, "PANEL HISTORIAL");
            areaVisualizacionResultados.setText("\t\tHISTORIAL SELECTIVO\n\n");
            try {
                for(registro = fichero.lectura(); !registro.esCentinela();) {
                    if(Utilidades.stringsIguales(nombreBuscar, registro.getNombre())) {
                        areaVisualizacionResultados.setText(areaVisualizacionResultados.getText() + registro.toString());
                    }
                    registro = fichero.lectura();
                }
            } catch(IOException error) {
                System.err.println("ERROR: ERROR EN LA LECTURA DE OBJETOS 'RegistroPartida' EN EL FICHERO " + nombreFicheroResultados);
            } catch(ClassNotFoundException error) {
                System.err.println("ERROR: NO SE HA PODIDO REALIZAR LA LECTURA DE DATOS");
            } finally {
                try {
                    fichero.cerrarEnlaceFichero();
                } catch(IOException error) {
                    System.err.println("ERROR: ERROR EN EL CIERRE DEL ENLACE CON EL FICHERO");
                }
            }
        } catch(IOException error) {
            visualizarError("ERROR", "No has jugado ninguna partida, por lo\ntanto, no hay partidas para visualizar aún.");
        }
    }
    
    private void cambiarDirectorioImagenes() {
        UIManager.put("Panel.background", Color.TRANSLUCENT);
        JFileChooser ventanaSeleccion = new JFileChooser();
        ventanaSeleccion.setDialogTitle("ELEGIR DIRECTORIO DE IMAGENES");
        ventanaSeleccion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int operacion = ventanaSeleccion.showOpenDialog(null);
        if(operacion == JFileChooser.APPROVE_OPTION) {
            if(Utilidades.contarImagenesCarpetas(ventanaSeleccion.getSelectedFile().getAbsolutePath()) != 0) {
                nombreCarpetaImagenes = ventanaSeleccion.getSelectedFile().getAbsolutePath();
            } else {
                visualizarInformacion("INFORMACIÓN", "No se han encontrado imagenes en la\ncarpeta seleccionada, por lo tanto no se"
                        + "\nha cambiado el directorio de imagenes.");
            }
        }
        
        ventanaSeleccion.setVisible(false);
    }
    
    private void generarSubImagenes() {
        final int NUMERO_FILAS_COLUMNAS = numeroFilas * numeroColumnas;
        boolean validacion = true;
        
        for(int i = 0; i < NUMERO_FILAS_COLUMNAS; i++) {
            int numRecorte = Utilidades.generarNumeroRecorte(numeroRecorteAsignado, i, NUMERO_FILAS_COLUMNAS);
            subImagenes[i] = new SubImagen(nombreImagen, numRecorte);
            subImagenes[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            subImagenes[i].addMouseListener(new GestorEventoSubImagenes());
        }
        
        for(int i = 1; i < NUMERO_FILAS_COLUMNAS; i++) {
            if(subImagenes[i].getRecorte() != subImagenes[i - 1].getRecorte() + 1) {
                validacion = false;
                break;
            }
        }
        
        if(validacion) {
            Utilidades.vaciarArray(numeroRecorteAsignado);
            generarSubImagenes();
        } else {
            for(int i = 0; i < subImagenes.length; i++) {
                panelSubImagenes.add(subImagenes[i]);
            }
        }
    }
    
    private boolean comprobarSolucion() {
        // Bucle para comprobar si las imagenes están en orden o no
        for(int i = 0; i < subImagenes.length; i++) {
            String nombreIcono = subImagenes[i].getIcon().toString();
            String caracter;
            
            if(i >= 10) {
                caracter = nombreIcono.charAt(nombreIcono.length() - 2) + "" + nombreIcono.charAt(nombreIcono.length() - 1);
            } else {
                caracter = "" + nombreIcono.charAt(nombreIcono.length() - 1);
            }
            
            if(Integer.parseInt(caracter) != i) {
                return false;
            }
        }
                
        // Tratamos la victoria
        barraTemporal.setActivo(false);
        visualizarInformacion("VICTORIA", "¡¡¡ ENHORABUENA !!! LO HAS CONSEGUIDO\nHAS OBTENIDO 4 PUNTOS");
        
        tratamientoRegistroPartida(true);
        
        // Eliminamos todas las imagenes almacenadas en el directorio de los recortes
        Utilidades.vaciarCarpetaRecortes(nombreCarpetaRecortes);
        // Visualizamos la solución y devolvemos true
        visualizarSolucion();
        return true;
    }
    
    private void tratamientoRegistroPartida(boolean victoria) {
        RegistroPartidaObjetoFicherosEscritura fichero = null;
        
        try {
            fichero = new RegistroPartidaObjetoFicherosEscritura(nombreFicheroResultadosTemp);
            fichero.escrituraRegistroPartida(f1, f2, nombreJugador, fecha, victoria);
        } catch(IOException ex) {
            System.err.println("");
        } catch (ClassNotFoundException ex) {
            System.err.println("ERROR: NO SE HA PODIDO REALIZAR LA LECTURA DE DATOS");
        }
    }
    
    // Es un gestor de evento por componentes, en el cual
    // para el tratamiento usamos el evento.getSource()
    private class GestorEventoComponentes implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evento) {
            if(evento.getSource() == cronometro) {
                if(barraTemporal.getValorBarraTemporal() < barraTemporal.getValorMaximo() && barraTemporal.estaActivo()) {
                    velocidad = DIMENSION_BARRA / (FACTOR_VELOCIDAD * numeroFilas * numeroColumnas);
                    barraTemporal.setValorBarraTemporal(barraTemporal.getValorBarraTemporal() + velocidad);
                } else if(!barraTemporal.estaActivo()) {
                    cronometro.stop();
                } else {
                    cronometro.stop();
                    visualizarInformacion("DERROTA", "NO LO HAS CONSEGUIDO - EL TIEMPO HA TERMINADO");
                    tratamientoRegistroPartida(false);
                    visualizarSolucion();
                }
            } else if(evento.getSource() == nuevaPartidaIcono) {
                if(!partidaEnCurso) {
                    introduccionDatos();
                } else {
                    visualizarInformacion("INFORMACIÓN", "ANTES DEBES TERMINAR LA PARTIDA EN CURSO");
                }
            } else if(evento.getSource() == historialGeneralIcono) {
                if(!partidaEnCurso) {
                    historialGeneral();
                } else {
                    visualizarInformacion("INFORMACIÓN", "ANTES DEBES TERMINAR LA PARTIDA EN CURSO");
                }
            } else if(evento.getSource() == historialSelectivoIcono) {
                if(!partidaEnCurso) {
                    introduccionNombre();
                } else {
                    visualizarInformacion("INFORMACIÓN", "ANTES DEBES TERMINAR LA PARTIDA EN CURSO");
                }
            } else if(evento.getSource() == cambiarDirectorioIcono) {
                if(!partidaEnCurso) {
                    cambiarDirectorioImagenes();
                } else {
                    visualizarInformacion("INFORMACIÓN", "ANTES DEBES TERMINAR LA PARTIDA EN CURSO");
                }
            } else if(evento.getSource() == salirIcono) {
                System.exit(0);
            }
        }
    }    
    
    // Es un gestor de evento para los botones, en el cual
    // para el tratamiento usamos el nombre del botón
    private class GestorEventoBotones implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evento) {
            switch(evento.getActionCommand()) {
                case "NUEVA PARTIDA" -> {
                    if(!partidaEnCurso) {
                        introduccionDatos();
                    } else {
                        visualizarInformacion("INFORMACIÓN", "ANTES DEBES TERMINAR LA PARTIDA EN CURSO");
                    }
                }
                case "CONFIRMAR" -> {
                    introduccionDatos.setVisible(false);
                    
                    if(Utilidades.comprobacionDatos(nombreJugadorIntroduccion, subDivisionesHorizontalIntroduccion, subDivisionesVerticalIntroduccion)) {
                        nombreJugador = nombreJugadorIntroduccion.getText();
                        numeroFilas = Integer.parseInt(subDivisionesHorizontalIntroduccion.getText());
                        numeroColumnas = Integer.parseInt(subDivisionesVerticalIntroduccion.getText());
                    } else {
                        visualizarInformacion("INFORMACIÓN", "Debes rellenar todos los campos necesarios\ncon la información correcta.");
                        introduccionDatos();
                        break;
                    }
                    
                    if((numeroFilas >= MINIMO_FILAS_COLUMNAS && numeroFilas <= MAXIMO_FILAS_COLUMNAS) && 
                            (numeroColumnas >= MINIMO_FILAS_COLUMNAS && numeroColumnas <= MAXIMO_FILAS_COLUMNAS)) {
                        partidaIniciada();
                    } else {
                        visualizarInformacion("INFORMACIÓN", "El número máximo de filas y columnas\nque puedes introducir son " + MAXIMO_FILAS_COLUMNAS + ".");
                        introduccionDatos();
                    }
                }   
                case "CONTINUAR" -> {
                    partidaEnCurso = false;
                    Utilidades.vaciarArray(numeroRecorteAsignado);
                    Utilidades.vaciarArray(subImagenes);
                    panelSubImagenes.removeAll();
                    panelImagenSolucion.removeAll();
                    panelPartida.removeAll();
                    localVisualizaciones.show(panelVisualizaciones, "PANEL STANDBY");
                }
                case "HISTORIAL GENERAL" -> {
                    if(!partidaEnCurso) {
                        historialGeneral();
                    } else {
                        visualizarInformacion("INFORMACIÓN", "ANTES DEBES TERMINAR LA PARTIDA EN CURSO");
                    }
                }
                case "HISTORIAL SELECTIVO" -> {
                    if(!partidaEnCurso) {
                        introduccionNombre();
                    } else {
                        visualizarInformacion("INFORMACIÓN", "ANTES DEBES TERMINAR LA PARTIDA EN CURSO");
                    }
                }
                case "CAMBIAR DIRECTORIO DE IMÁGENES" -> {
                    if(!partidaEnCurso) {
                        cambiarDirectorioImagenes();
                    } else {
                        visualizarInformacion("INFORMACIÓN", "ANTES DEBES TERMINAR LA PARTIDA EN CURSO");
                    }
                }
                case "SALIR" -> System.exit(0);
            }
        }
    }
        
    // Es un gestor de evento para las subimagenes, en el cual
    // para el tratamiento usamos el MouseListener y todos sus métodos
    private class GestorEventoSubImagenes implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            // No se usa.
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // No se usa.
        }

        @Override
        public void mouseReleased(MouseEvent evento) {
            // Bucle para iterar por todas las subimagenes
            for(int i = 0; i < subImagenes.length; i++) {
                // Comprobar que esa es la que hemos seleccionado
                if(evento.getSource() == subImagenes[i]) {
                    primerClick = !primerClick;
                    
                    // Nos encargamos de intercambiar las imagenes de la que hemos hecho
                    // click antes con la que hemos hecho click después. Además actualizamos
                    // su borde para ver la subimagen en la cual hemos hecho click
                    // primero antes del cambio.
                    if(primerClick) {
                        pos = i;
                        subImagenes[pos].setBorder(BorderFactory.createLineBorder(Color.YELLOW,2));
                    } else {            
                        String subImagenTemp = subImagenes[pos].getIcon().toString();
                        
                        subImagenes[pos].setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
                        subImagenes[pos].setIcon(new ImageIcon(subImagenes[i].getIcon().toString()));
                        subImagenes[i].setIcon(new ImageIcon(subImagenTemp));
                        
                        comprobarSolucion();
                    }
                    
                    // Salimos del for
                    break;
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // No se usa.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // No se usa.
        }

    } 
    
    // Pequeño showConfirmDialog que nos proporciona información sobre algo
    // que estemos haciendo mal o que no debemos o no podemos hacer
    private void visualizarInformacion(String titulo, String mensaje) {
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("OptionPane.messageForeground", Color.YELLOW);
        showConfirmDialog(null, mensaje, 
                titulo, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Pequeño showConfirmDialog que nos informa sobre errores, como a la hora
    // de abrir el historial general sin antes haber registrado partidas
    private void visualizarError(String titulo, String mensaje) {
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("OptionPane.messageForeground", Color.RED);
        showConfirmDialog(null, mensaje, 
                titulo, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }
    
    // Cambiamos el panel de visualizaciones al panel que contiene la imagen con la solución
    private void visualizarSolucion() {
        localVisualizaciones.show(panelVisualizaciones, "PANEL IMAGEN SOLUCIÓN");
    }
    
    // Método main que nos ejecuta el programa
    public static void main(String[] argumentos) {
        ProgramaPrincipal programaPrincipal = new ProgramaPrincipal();
    }
    
}
