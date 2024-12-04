package ai.imagen.variable.generator;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;

public class CustomSymlinkUI {

    // Crear la ventana principal
    private static JFrame frame;
    private static JPanel mainPanel;
    
    // Componentes para los inputs de FILE_DIRECTORY y FILE_NAME
    private static JTextField fileDirectoryField;
    private static JTextField fileNameField;
    
    private static JTextField fileDirectoryFieldVA;
    private static JTextField fileNameFieldVA;
    
    private static JTextField fileDirectoryFieldVB;
    private static JTextField fileNameFieldVB;
    
    private static JTextField fileDirectoryFieldVC;
    private static JTextField fileNameFieldVC;
    
    // Panel para los datos JSON
    private static JPanel jsonPanel;
    
    
    // Paneles para las variantes
    private static JPanel variantPanelContainer;
    
    // Datos JSON Main
   // private static JTextField sdVersionField;
    private static JTextArea descriptionField;
    private static JTextArea activationTextField;
    private static JTextArea negativeTextField;
    private static JTextArea notesField;
    private static JSlider preferredWeightSlider;
    private static JLabel preferredWeightLabel;
    
    // ComboBox para seleccionar la SD Version
    private static JComboBox<String> sdVersionComboBox;
    
    // Variables para almacenar las variantes
    private static int variantCount = 0;
    
    // Botones que se mostrarán solo si el archivo es válido
    private static JButton addVariantButton;
    private static JButton addVariantButton1;
    private static JButton addVariantButton2;
    private static JButton addVariantButton3;
    private static JButton removeVariantButton;
    private static JButton generateButton;
    
 // Contadores para cada botón
    private static int variantButton1Counter = 0;
    private static int variantButton2Counter = 0;
    private static int variantButton3Counter = 0;

    // Variable para recordar cuál fue el último botón presionado
    private static ArrayList<String> buttonPressHistory = new ArrayList<>();
    
    // Ruta del archivo log.txt
    private static final String LOG_FILE_PATH = Paths.get("").toAbsolutePath().resolve("log.txt").toString();
    //private static String previousVariantDirectory = "";  // Almacenar la dirección de la variante anterior
    //private static int previousVariantWeight = 10;  // Almacenar el peso preferido de la variante anterior
    
    private static ArrayList<VariantDetails> variantList = new ArrayList<>();  // Lista para almacenar las variantes
    private static final ArrayList<String> logMessages = new ArrayList<>();
    
    private static JScrollPane scrollPane;

    public static void main(String[] args) {
        // Crear la ventana principal
        frame = new JFrame("Symlinks and JSON generator");
        
        // Establecer tamaño inicial y tamaño mínimo
        frame.setSize(1000, 900);
        frame.setMinimumSize(new Dimension(1000, 900)); // Establecer tamaño mínimo
        
        // Establecer comportamiento para restringir el tamaño
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                Dimension currentSize = frame.getSize();
                int maxWidth = 1000;
                
                // Restringir la anchura pero permitir que la altura crezca
                if (currentSize.width > maxWidth) {
                    frame.setSize(new Dimension(maxWidth, currentSize.height));
                }
            }
        });
        
        // Establecer el tamaño máximo del JFrame (solo ancho, alto puede crecer)
        frame.setPreferredSize(new Dimension(1000, 800)); // Ajustar el tamaño preferido
        
        // Centrar la ventana en la pantalla
        frame.setLocationRelativeTo(null);
        
        // Crear el panel principal y establecer el layout
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Alineación vertical
        
        // Asegurarse de que los componentes dentro de mainPanel estén centrados
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Agregar los componentes a la ventana
        setupUI();
        
        // Cargar los valores desde el log (si existen)
        loadLog();
        
        // Agregar el panel dentro de un JScrollPane para manejar el desplazamiento si es necesario
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        // Agregar el JScrollPane al frame
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Ajustar el tamaño del frame según los componentes
        frame.pack();
        
        // Mostrar la ventana
        frame.setVisible(true);
    }


    
    private static void setupUI() {
        // Crear la ventana principal
    	JLabel titleLabel = new JLabel("Symlinks and JSON generator");
    	titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

    	// Crear un panel para contener el JLabel y centrarlo
    	JPanel titlePanel = new JPanel();
    	titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));

    	// Añadir el glue antes y después del JLabel para centrarlo horizontalmente
    	titlePanel.add(Box.createHorizontalGlue());
    	titlePanel.add(titleLabel);
    	titlePanel.add(Box.createHorizontalGlue());

    	// Añadir el panel al mainPanel
    	mainPanel.add(titlePanel);

        // Línea con los inputs FILE_DIRECTORY y FILE_NAME
        JPanel fileSelectionPanel = new JPanel();
        fileSelectionPanel.setLayout(new BoxLayout(fileSelectionPanel, BoxLayout.X_AXIS));

        fileDirectoryField = new JTextField(20);
        fileDirectoryField.setPreferredSize(new Dimension(200, 25));
        fileDirectoryField.setMaximumSize(new Dimension(400, 25));

        fileNameField = new JTextField(15);
        fileNameField.setPreferredSize(new Dimension(200, 25));
        fileNameField.setMaximumSize(new Dimension(300, 25));

        JButton browseButton = new JButton("Search");
        browseButton.addActionListener(e -> browseFile());

        // Añadir labels y inputs al panel de selección de archivo
        fileSelectionPanel.add(Box.createHorizontalGlue());  // Para centrar los componentes
        fileSelectionPanel.add(new JLabel("Directory:"));
        fileSelectionPanel.add(fileDirectoryField);
        fileSelectionPanel.add(new JLabel("Name:"));
        fileSelectionPanel.add(fileNameField);
        fileSelectionPanel.add(browseButton);
        fileSelectionPanel.add(Box.createHorizontalGlue());  // Para centrar los componentes

        mainPanel.add(fileSelectionPanel);

     // Botón de cargar
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> loadFile());

        // Crear un panel para centrar el botón
        JPanel loadButtonPanel = new JPanel();
        loadButtonPanel.setLayout(new BoxLayout(loadButtonPanel, BoxLayout.X_AXIS));
        loadButtonPanel.add(Box.createHorizontalGlue());  // Centrar el componente
        loadButtonPanel.add(loadButton);
        loadButtonPanel.add(Box.createHorizontalGlue());  // Centrar el componente
        mainPanel.add(loadButtonPanel);

        mainPanel.add(Box.createVerticalStrut(5));  // Espaciado entre los componentes

        // Panel JSON
        jsonPanel = new JPanel();
        jsonPanel.setLayout(new BoxLayout(jsonPanel, BoxLayout.Y_AXIS));
        jsonPanel.setVisible(false);
        setupJsonPanel();

        // Crear un panel para centrar el jsonPanel
        JPanel jsonPanelWrapper = new JPanel();
        jsonPanelWrapper.setLayout(new BoxLayout(jsonPanelWrapper, BoxLayout.X_AXIS));
        jsonPanelWrapper.add(Box.createHorizontalGlue());  // Centrar el componente
        jsonPanelWrapper.add(jsonPanel);
        jsonPanelWrapper.add(Box.createHorizontalGlue());  // Centrar el componente
        mainPanel.add(jsonPanelWrapper);

        mainPanel.add(Box.createVerticalStrut(5));  // Espaciado entre los componentes

        // Panel de botones de variantes
        JPanel variantButtonsPanel = new JPanel();
        variantButtonsPanel.setLayout(new BoxLayout(variantButtonsPanel, BoxLayout.X_AXIS));  // Alineación horizontal

        // Crear un panel para centrar el variantButtonsPanel
        JPanel variantButtonsPanelWrapper = new JPanel();
        variantButtonsPanelWrapper.setLayout(new BoxLayout(variantButtonsPanelWrapper, BoxLayout.X_AXIS));
        variantButtonsPanelWrapper.add(Box.createHorizontalGlue());  // Centrar el componente
        variantButtonsPanelWrapper.add(variantButtonsPanel);
        variantButtonsPanelWrapper.add(Box.createHorizontalGlue());  // Centrar el componente
        mainPanel.add(variantButtonsPanelWrapper);

        // Configurar los botones de variantes
        setupVariantButtonsAndContainer();

        // Botón "Generar"
        generateButton = new JButton("Generate files");
        generateButton.addActionListener(e -> generateSymlinkAndJson());

        // Crear un panel para centrar el botón "Generar"
        JPanel generateButtonPanel = new JPanel();
        generateButtonPanel.setLayout(new BoxLayout(generateButtonPanel, BoxLayout.X_AXIS));
        generateButtonPanel.add(Box.createHorizontalGlue());  // Centrar el componente
        generateButtonPanel.add(generateButton);
        generateButtonPanel.add(Box.createHorizontalGlue());  // Centrar el componente
        mainPanel.add(generateButtonPanel);

        // Panel de relleno para centrar los componentes
        JPanel fillerPanel = new JPanel();
        fillerPanel.setLayout(new BoxLayout(fillerPanel, BoxLayout.Y_AXIS));
        fillerPanel.add(Box.createVerticalGlue());
        mainPanel.add(fillerPanel);


        // Inicialmente, ocultar los botones de variantes y el botón de generar
        removeVariantButton.setVisible(false);
        generateButton.setVisible(false);

        // Forzar el repintado de la ventana para asegurar que los botones no se muestren
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    // Nueva función para configurar el panel de botones y contenedor de variantes
    private static void setupVariantButtonsAndContainer() {
        // Panel de botones Añadir y Eliminar Variante
        JPanel variantButtonPanel = new JPanel();
        variantButtonPanel.setLayout(new BoxLayout(variantButtonPanel, BoxLayout.X_AXIS));
        variantButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

     // Botón para Añadir Variante 1
        addVariantButton1 = new JButton("Add Variant 1");
        addVariantButton1.addActionListener(e -> {
            variantButton1Counter++; // Incrementa el contador de Variante 1
            String name = fileNameFieldVA.getText() + variantButton1Counter; // Concatenamos el nombre con el contador
            String directory = fileDirectoryFieldVA.getText(); // Usamos el directorio de VA
            addVariantPanel(name, directory); // Llamamos a addVariantPanel con el nombre y directorio correspondientes

            // Agregamos al historial de botones presionados
            buttonPressHistory.add("Add Variant 1");

            // Mover el scroll hacia el final
            SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
        });

        // Botón para Añadir Variante 2
        addVariantButton2 = new JButton("Add Variant 2");
        addVariantButton2.addActionListener(e -> {
            variantButton2Counter++; // Incrementa el contador de Variante 2
            String name = fileNameFieldVB.getText() + variantButton2Counter; // Concatenamos el nombre con el contador
            String directory = fileDirectoryFieldVB.getText(); // Usamos el directorio de VB
            addVariantPanel(name, directory); // Llamamos a addVariantPanel con el nombre y directorio correspondientes

            // Agregamos al historial de botones presionados
            buttonPressHistory.add("Add Variant 2");

            // Mover el scroll hacia el final
            SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
        });

        // Botón para Añadir Variante 3
        addVariantButton3 = new JButton("Add Variant 3");
        addVariantButton3.addActionListener(e -> {
            variantButton3Counter++; // Incrementa el contador de Variante 3
            String name = fileNameFieldVC.getText() + variantButton3Counter; // Concatenamos el nombre con el contador
            String directory = fileDirectoryFieldVC.getText(); // Usamos el directorio de VC
            addVariantPanel(name, directory); // Llamamos a addVariantPanel con el nombre y directorio correspondientes

            // Agregamos al historial de botones presionados
            buttonPressHistory.add("Add Variant 3");

            // Mover el scroll hacia el final
            SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
        });
        


        // El botón para eliminar variantes
        removeVariantButton = new JButton("Delete Variant");
        removeVariantButton.addActionListener(e -> removeVariantPanel());
        

        // Crear un panel para los botones de variantes y centrarlo
        JPanel variantButtonWrapper = new JPanel();
        variantButtonWrapper.setLayout(new BoxLayout(variantButtonWrapper, BoxLayout.X_AXIS));
        variantButtonWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes
        variantButtonWrapper.add(variantButtonPanel);
        variantButtonWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes

        // Agregar los botones al panel de variantes
        variantButtonPanel.add(addVariantButton1);
        variantButtonPanel.add(Box.createHorizontalStrut(20)); // Espaciado
        variantButtonPanel.add(addVariantButton2);
        variantButtonPanel.add(Box.createHorizontalStrut(20)); // Espaciado
        variantButtonPanel.add(addVariantButton3);
        variantButtonPanel.add(Box.createHorizontalStrut(20)); // Espaciado
        variantButtonPanel.add(removeVariantButton);
        
        variantButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, variantButtonPanel.getPreferredSize().height));
        variantButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Alineación a la izquierda

        // Panel de variantes
        variantPanelContainer = new JPanel();
        variantPanelContainer.setLayout(new BoxLayout(variantPanelContainer, BoxLayout.Y_AXIS));
        variantPanelContainer.setAlignmentY(Component.TOP_ALIGNMENT);

        // Crear los paneles para cada variante con FlowLayout para centrar los campos
        JPanel variantFieldsPanelVA = createVariantFieldsPanel(
        	"Variant A Directory: ", "Variant A Name: ",
            fileDirectoryFieldVA = new JTextField(20), fileNameFieldVA = new JTextField(15)
        );
        variantFieldsPanelVA.setLayout(new FlowLayout(FlowLayout.CENTER));
        variantFieldsPanelVA.setMaximumSize(new Dimension(Integer.MAX_VALUE, variantFieldsPanelVA.getPreferredSize().height));
        variantPanelContainer.add(variantFieldsPanelVA);

        JPanel variantFieldsPanelVB = createVariantFieldsPanel(
        	"Variant B Directory: ", "Variant B Name: ",
            fileDirectoryFieldVB = new JTextField(20), fileNameFieldVB = new JTextField(15)
        );
        variantFieldsPanelVB.setLayout(new FlowLayout(FlowLayout.CENTER));
        variantFieldsPanelVB.setMaximumSize(new Dimension(Integer.MAX_VALUE, variantFieldsPanelVB.getPreferredSize().height));
        variantPanelContainer.add(variantFieldsPanelVB);

        JPanel variantFieldsPanelVC = createVariantFieldsPanel(
        	"Variant C Directory: ", "Variant C Name: ",
            fileDirectoryFieldVC = new JTextField(20), fileNameFieldVC = new JTextField(15)
        );
        variantFieldsPanelVC.setLayout(new FlowLayout(FlowLayout.CENTER));
        variantFieldsPanelVC.setMaximumSize(new Dimension(Integer.MAX_VALUE, variantFieldsPanelVC.getPreferredSize().height));
        variantPanelContainer.add(variantFieldsPanelVC);

        // Crear un panel para centrar el contenedor de variantes
        JPanel variantPanelWrapper = new JPanel();
        variantPanelWrapper.setLayout(new BoxLayout(variantPanelWrapper, BoxLayout.X_AXIS));
        variantPanelWrapper.add(Box.createHorizontalGlue());  // Centrar el componente
        variantPanelWrapper.add(variantPanelContainer);
        variantPanelWrapper.add(Box.createHorizontalGlue());  // Centrar el componente
        
        

        // Agregar el contenedor de variantes al panel principal
        mainPanel.add(variantPanelWrapper);

        // Agregar el panel de botones al panel principal
        mainPanel.add(variantButtonWrapper);

        // Espacio controlado entre los botones y el panel de variantes
        mainPanel.add(Box.createVerticalStrut(5));
        
        mainPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, mainPanel.getPreferredSize().height));
        
        

        // Inicialmente, los botones y los campos de variante estarán ocultos
        addVariantButton1.setVisible(false);
        addVariantButton2.setVisible(false);
        addVariantButton3.setVisible(false);
        variantPanelContainer.setVisible(false); // Ocultar panel de variantes por defecto
    }


    // Método para crear un panel con campos de texto alineados arriba y sin espacios vacíos
    private static JPanel createVariantFieldsPanel(String label1, String label2, JTextField field1, JTextField field2) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinear el panel al centro horizontalmente

        // Contenedor para centrar los componentes
        JPanel centeredPanel = new JPanel();
        centeredPanel.setLayout(new BoxLayout(centeredPanel, BoxLayout.X_AXIS));
        centeredPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centeredPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Etiquetas y campos de texto
        JLabel dirLabel = new JLabel(label1);
        dirLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        centeredPanel.add(dirLabel);

        field1.setPreferredSize(new Dimension(200, 25));
        field1.setMaximumSize(new Dimension(200, 25));
        field1.setAlignmentY(Component.CENTER_ALIGNMENT);
        centeredPanel.add(field1);

        centeredPanel.add(Box.createHorizontalStrut(10)); // Espaciado entre los campos

        JLabel nameLabel = new JLabel(label2);
        nameLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        centeredPanel.add(nameLabel);

        field2.setPreferredSize(new Dimension(150, 25));
        field2.setMaximumSize(new Dimension(150, 25));
        field2.setAlignmentY(Component.CENTER_ALIGNMENT);
        centeredPanel.add(field2);

        centeredPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(centeredPanel);
        return panel;
    }















    private static void setupJsonPanel() {
        // Configurar los campos del panel JSON
        sdVersionComboBox = new JComboBox<>(new String[]{"Unknown", "SD1", "SD2", "SDXL", "Flux"});
        sdVersionComboBox.setPreferredSize(new Dimension(200, 25));  // Ajustamos el tamaño fijo
        sdVersionComboBox.setMaximumSize(new Dimension(200, 25));

        // Ajustar el tamaño de los JTextArea
        descriptionField = new JTextArea(2, 30);
        descriptionField.setPreferredSize(new Dimension(800, 50));  // Ajuste de altura
        descriptionField.setMaximumSize(new Dimension(800, 50));  // Evitar que se expanda más

        activationTextField = new JTextArea(2, 30);
        activationTextField.setPreferredSize(new Dimension(800, 50));  // Ajuste de altura
        activationTextField.setMaximumSize(new Dimension(800, 50));

        negativeTextField = new JTextArea(2, 30);
        negativeTextField.setPreferredSize(new Dimension(800, 50));  // Ajuste de altura
        negativeTextField.setMaximumSize(new Dimension(800, 50));

        notesField = new JTextArea(2, 30);
        notesField.setPreferredSize(new Dimension(800, 50));  // Ajuste de altura
        notesField.setMaximumSize(new Dimension(800, 50));

        // Configurar el slider para el peso preferido
        preferredWeightSlider = new JSlider(-30, 30, 10);
        preferredWeightSlider.setMajorTickSpacing(10);
        preferredWeightSlider.setMinorTickSpacing(1);
        preferredWeightSlider.setPaintTicks(true);
        preferredWeightSlider.setPaintLabels(true);
        preferredWeightSlider.setPreferredSize(new Dimension(800, 40)); // Ajuste de tamaño
        preferredWeightSlider.setMaximumSize(new Dimension(800, 40));

        // Etiqueta de peso preferido
        preferredWeightLabel = new JLabel("Preferred Weight: 1.0");

        // Mostrar el valor de la barra de peso
        preferredWeightSlider.addChangeListener(e -> preferredWeightLabel.setText("Preferred Weight: " + (preferredWeightSlider.getValue() / 10.0)));

        // Usar BoxLayout para los componentes dentro del jsonPanel
        jsonPanel.setLayout(new BoxLayout(jsonPanel, BoxLayout.Y_AXIS)); // Alineamos verticalmente

        // Panel de contenido con centrado
        JPanel sdVersionPanel = new JPanel();
        sdVersionPanel.setLayout(new BoxLayout(sdVersionPanel, BoxLayout.Y_AXIS)); // Alinear verticalmente
        JPanel sdVersionPanelWrapper = new JPanel();
        sdVersionPanelWrapper.setLayout(new BoxLayout(sdVersionPanelWrapper, BoxLayout.X_AXIS));
        sdVersionPanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes
        sdVersionPanelWrapper.add(sdVersionPanel);
        sdVersionPanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes

        sdVersionPanel.add(Box.createVerticalGlue());
        sdVersionPanel.add(new JLabel("SD Version (SD1, SD2, SDXL, Flux, Unknown):"));
        sdVersionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sdVersionPanel.add(sdVersionComboBox);
        sdVersionPanel.add(Box.createVerticalGlue());

        jsonPanel.add(sdVersionPanelWrapper);
        jsonPanel.add(Box.createVerticalStrut(5));

        // Panel de Descripción con centrado
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS)); // Alinear verticalmente
        JPanel descriptionPanelWrapper = new JPanel();
        descriptionPanelWrapper.setLayout(new BoxLayout(descriptionPanelWrapper, BoxLayout.X_AXIS));
        descriptionPanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes
        descriptionPanelWrapper.add(descriptionPanel);
        descriptionPanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes

     // Cambiar el JTextField por un JTextArea para permitir desplazamiento
        JTextArea descriptionField = new JTextArea(5, 20); // Ajusta las dimensiones según sea necesario
        descriptionField.setLineWrap(true); // Habilita el ajuste de línea
        descriptionField.setWrapStyleWord(true); // Ajusta las palabras
        descriptionField.setCaretPosition(0); // Asegura que el cursor esté al inicio al principio

        // Usar JScrollPane para que puedas desplazarte si el texto se desborda
        JScrollPane scrollPane = new JScrollPane(descriptionField);
        scrollPane.setPreferredSize(new Dimension(800, 60)); // Ajusta el tamaño del área de texto

        // Usar FlowLayout para centrar el JLabel y alinear el texto a la izquierda
        descriptionPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Alineación a la izquierda
        descriptionPanel.add(Box.createVerticalGlue());
        descriptionPanel.add(new JLabel("Description:"));
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        // Usar JScrollPane en lugar de JTextField directamente
        descriptionPanel.add(scrollPane);
        descriptionPanel.add(Box.createVerticalGlue());

        jsonPanel.add(descriptionPanelWrapper);
        jsonPanel.add(Box.createVerticalStrut(5));

     // Panel de Texto de Activación con JTextArea y JScrollPane
        JPanel activationPanel = new JPanel();
        activationPanel.setLayout(new BoxLayout(activationPanel, BoxLayout.Y_AXIS)); // Alinear verticalmente
        JPanel activationPanelWrapper = new JPanel();
        activationPanelWrapper.setLayout(new BoxLayout(activationPanelWrapper, BoxLayout.X_AXIS));
        activationPanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes
        activationPanelWrapper.add(activationPanel);
        activationPanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes

        // Crear el JTextArea para el texto de activación
        JTextArea activationTextArea = new JTextArea(5, 20); // Ajusta las dimensiones según sea necesario
        activationTextArea.setLineWrap(true); // Habilita el ajuste de línea
        activationTextArea.setWrapStyleWord(true); // Ajusta las palabras
        activationTextArea.setCaretPosition(0); // Asegura que el cursor esté al inicio al principio

        // Usar JScrollPane para que puedas desplazarte si el texto se desborda
        JScrollPane activationScrollPane = new JScrollPane(activationTextArea);
        activationScrollPane.setPreferredSize(new Dimension(800, 60)); // Ajusta el tamaño del área de texto

        // Agregar componentes al panel de activación
        activationPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Alineación a la izquierda
        activationPanel.add(Box.createVerticalGlue());
        activationPanel.add(new JLabel("Activation Text:"));
        activationPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        // Usar JScrollPane en lugar de JTextField directamente
        activationPanel.add(activationScrollPane);
        activationPanel.add(Box.createVerticalGlue());


        jsonPanel.add(activationPanelWrapper);
        jsonPanel.add(Box.createVerticalStrut(5));

     // Panel de Texto Negativo con JTextArea y JScrollPane
        JPanel negativePanel = new JPanel();
        negativePanel.setLayout(new BoxLayout(negativePanel, BoxLayout.Y_AXIS)); // Alinear verticalmente
        JPanel negativePanelWrapper = new JPanel();
        negativePanelWrapper.setLayout(new BoxLayout(negativePanelWrapper, BoxLayout.X_AXIS));
        negativePanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes
        negativePanelWrapper.add(negativePanel);
        negativePanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes

        // Crear el JTextArea para el texto negativo
        JTextArea negativeTextArea = new JTextArea(5, 20); // Ajusta las dimensiones según sea necesario
        negativeTextArea.setLineWrap(true); // Habilita el ajuste de línea
        negativeTextArea.setWrapStyleWord(true); // Ajusta las palabras
        negativeTextArea.setCaretPosition(0); // Asegura que el cursor esté al inicio al principio

        // Usar JScrollPane para que puedas desplazarte si el texto se desborda
        JScrollPane negativeScrollPane = new JScrollPane(negativeTextArea);
        negativeScrollPane.setPreferredSize(new Dimension(800, 60)); // Ajusta el tamaño del área de texto

        // Agregar componentes al panel negativo
        negativePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Alineación a la izquierda
        negativePanel.add(Box.createVerticalGlue());
        negativePanel.add(new JLabel("Negative Text:"));
        negativePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        // Usar JScrollPane en lugar de JTextField directamente
        negativePanel.add(negativeScrollPane);
        negativePanel.add(Box.createVerticalGlue());

        jsonPanel.add(negativePanelWrapper);
        jsonPanel.add(Box.createVerticalStrut(5));

        // Panel de Notas con centrado
     // Panel de Notas con JTextArea y JScrollPane
        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.Y_AXIS)); // Alinear verticalmente
        JPanel notesPanelWrapper = new JPanel();
        notesPanelWrapper.setLayout(new BoxLayout(notesPanelWrapper, BoxLayout.X_AXIS));
        notesPanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes
        notesPanelWrapper.add(notesPanel);
        notesPanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes

        // Crear el JTextArea para las notas
        JTextArea notesTextArea = new JTextArea(5, 20); // Ajusta las dimensiones según sea necesario
        notesTextArea.setLineWrap(true); // Habilita el ajuste de línea
        notesTextArea.setWrapStyleWord(true); // Ajusta las palabras
        notesTextArea.setCaretPosition(0); // Asegura que el cursor esté al inicio al principio

        // Usar JScrollPane para que puedas desplazarte si el texto se desborda
        JScrollPane notesScrollPane = new JScrollPane(notesTextArea);
        notesScrollPane.setPreferredSize(new Dimension(800, 60)); // Ajusta el tamaño del área de texto

        // Agregar componentes al panel de notas
        notesPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Alineación a la izquierda
        notesPanel.add(Box.createVerticalGlue());
        notesPanel.add(new JLabel("Note:"));
        notesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        // Usar JScrollPane en lugar de JTextField directamente
        notesPanel.add(notesScrollPane);
        notesPanel.add(Box.createVerticalGlue());

        jsonPanel.add(notesPanelWrapper);
        jsonPanel.add(Box.createVerticalStrut(5));

     // Panel de Peso con JSlider centrado
        JPanel weightPanel = new JPanel();
        weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.Y_AXIS)); // Alinear verticalmente
        JPanel weightPanelWrapper = new JPanel();
        weightPanelWrapper.setLayout(new BoxLayout(weightPanelWrapper, BoxLayout.X_AXIS));
        weightPanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes
        weightPanelWrapper.add(weightPanel);
        weightPanelWrapper.add(Box.createHorizontalGlue());  // Centrar los componentes

        // Crear un JPanel interno para centrar la barra deslizante
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centrar el JSlider
        sliderPanel.add(preferredWeightSlider); // Añadir el JSlider

        // Agregar la etiqueta del peso preferido y el sliderPanel al weightPanel
        weightPanel.add(Box.createVerticalGlue());
        weightPanel.add(new JLabel("Preferred Weight:"));
        weightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        weightPanel.add(sliderPanel); // Añadir el slider centrado
        weightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        weightPanel.add(preferredWeightLabel);
        weightPanel.add(Box.createVerticalGlue());


        jsonPanel.add(weightPanelWrapper);
        jsonPanel.add(Box.createVerticalStrut(10));

        // Asegurarse de que el panel de JSON no se expanda innecesariamente
        jsonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, jsonPanel.getPreferredSize().height));

        // Asegurarse de que los elementos no se deslicen
        for (Component comp : jsonPanel.getComponents()) {
            if (comp instanceof JTextArea) {
                ((JTextArea) comp).setLineWrap(true);  // Habilitar el ajuste de línea para evitar el desbordamiento
            }
        }
    }






    private static void addVariantPanel(String defaultName, String defaultDirectory) {
        // Crear un nuevo panel para la variante
        JPanel variantPanel = new JPanel();
        variantPanel.setLayout(new BoxLayout(variantPanel, BoxLayout.Y_AXIS));

        // Crear los campos con valores predeterminados
        JTextField variantNameField = new JTextField(defaultName, 10);
        variantNameField.setPreferredSize(new Dimension(200, 25));
        variantNameField.setMaximumSize(new Dimension(800, 25));  // Limitar el ancho máximo

        JTextField variantDirectoryField = new JTextField(defaultDirectory, 30);
        variantDirectoryField.setPreferredSize(new Dimension(300, 25));
        variantDirectoryField.setMaximumSize(new Dimension(800, 25));  // Limitar el ancho máximo

        // Crear campos de texto adicionales

        JTextArea variantActivationTextField = new JTextArea(2, 20);
        variantActivationTextField.setLineWrap(true); // Habilita el ajuste de línea
        variantActivationTextField.setWrapStyleWord(true); // Ajusta las palabras
        variantActivationTextField.setPreferredSize(new Dimension(800, 50)); // Limitar el ancho máximo
        variantActivationTextField.setMaximumSize(new Dimension(800, 50));  // Limitar el ancho máximo
        variantActivationTextField.setCaretPosition(0); // Asegura que el cursor esté al inicio al principio
        variantActivationTextField.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextArea variantNegativeTextField = new JTextArea(2, 30);
        variantNegativeTextField.setLineWrap(true); // Habilita el ajuste de línea
        variantNegativeTextField.setWrapStyleWord(true); // Ajusta las palabras        
        variantNegativeTextField.setPreferredSize(new Dimension(800, 50)); // Limitar el ancho máximo
        variantNegativeTextField.setMaximumSize(new Dimension(800, 50));  // Limitar el ancho máximo
        variantNegativeTextField.setCaretPosition(0); // Asegura que el cursor esté al inicio al principio

        // Obtener el valor del preferredWeightSlider
        int X = preferredWeightSlider.getValue();

        // Crear el JSlider con el valor obtenido
        JSlider variantPreferredWeightSlider = new JSlider(-30, 30, X);
        variantPreferredWeightSlider.setMajorTickSpacing(10);
        variantPreferredWeightSlider.setMinorTickSpacing(1);
        variantPreferredWeightSlider.setPaintTicks(true);
        variantPreferredWeightSlider.setPaintLabels(true);
        variantPreferredWeightSlider.setPreferredSize(new Dimension(800, 40)); // Limitar el ancho máximo
        variantPreferredWeightSlider.setMaximumSize(new Dimension(800, 40));  // Limitar el ancho máximo

        JLabel variantPreferredWeightLabel = new JLabel("Preferred Weight: " + X / 10.0);
        variantPreferredWeightSlider.addChangeListener(e -> variantPreferredWeightLabel.setText("Preferred Weight: " + (variantPreferredWeightSlider.getValue() / 10.0)));

        // Centrar los labels
        JLabel variantNameLabel = new JLabel("Variant Name:");
        variantNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel variantDirectoryLabel = new JLabel("Variant Directory:");
        variantDirectoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel variantActivationTextLabel = new JLabel("Variant Activation Text:");
        variantActivationTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel variantNegativeTextLabel = new JLabel("Negative Variant Text:");
        variantNegativeTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel variantPreferredWeightLabelLabel = new JLabel("Variant Preferred Weight:");
        variantPreferredWeightLabelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregar los componentes al panel, con Box.createVerticalStrut(5) para agregar espacio entre los componentes
        variantPanel.add(variantNameLabel);
        variantPanel.add(variantNameField);
        variantPanel.add(Box.createVerticalStrut(5));

        variantPanel.add(variantDirectoryLabel);
        variantPanel.add(variantDirectoryField);
        variantPanel.add(Box.createVerticalStrut(5));

        variantPanel.add(variantActivationTextLabel);
        variantPanel.add(variantActivationTextField);
        variantPanel.add(Box.createVerticalStrut(5));

        variantPanel.add(variantNegativeTextLabel);
        variantPanel.add(variantNegativeTextField);
        variantPanel.add(Box.createVerticalStrut(5));

        variantPanel.add(variantPreferredWeightLabelLabel);
        variantPanel.add(variantPreferredWeightSlider);
        variantPanel.add(variantPreferredWeightLabel);
        variantPanel.add(Box.createVerticalStrut(10));

        // Asegurarse de que el panel no se expanda innecesariamente
        variantPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, variantPanel.getPreferredSize().height));

        // Añadir la variante a la lista
        String variantName = variantNameField.getText();
        String fileDirectory = variantDirectoryField.getText();
        double preferredWeight = variantPreferredWeightSlider.getValue() / 10.0;

        VariantDetails newVariant = new VariantDetails(variantName, fileDirectory, "Unknown", 
                                                        variantActivationTextField.getText(), variantNegativeTextField.getText(), "",
                                                        "", preferredWeight);

        variantList.add(newVariant);

        // Actualizar el contador y el historial del botón presionado
        if (defaultName.equals("DefaultName1")) {
            variantButton1Counter++;
            buttonPressHistory.add("Add Variant 1");
        } else if (defaultName.equals("DefaultName2")) {
            variantButton2Counter++;
            buttonPressHistory.add("Add Variant 2");
        } else if (defaultName.equals("DefaultName3")) {
            variantButton3Counter++;
            buttonPressHistory.add("Add Variant 3");
        }

        // Agregar el panel al contenedor
        variantPanelContainer.add(variantPanel);
        variantPanelContainer.revalidate();
    }








    private static void removeVariantPanel() {
        // Verificar si la lista de historial de botones presionados no está vacía
        if (!buttonPressHistory.isEmpty()) {
            // Obtener el último botón presionado desde el historial
            String lastPressed = buttonPressHistory.get(buttonPressHistory.size() - 1);

            // Eliminar el panel correspondiente solo si hay panels en el contenedor
            if (variantPanelContainer.getComponentCount() > 0) {
                if (lastPressed.equals("Add Variant 1") && variantButton1Counter > 0) {
                    // Si el último botón presionado fue el de Variante 1, eliminamos el panel correspondiente
                    variantButton1Counter--; // Reducir el contador de Variante 1
                    variantPanelContainer.remove(variantPanelContainer.getComponentCount() - 1);
                    variantPanelContainer.revalidate();
                } else if (lastPressed.equals("Add Variant 2") && variantButton2Counter > 0) {
                    // Si el último botón presionado fue el de Variante 2, eliminamos el panel correspondiente
                    variantButton2Counter--; // Reducir el contador de Variante 2
                    variantPanelContainer.remove(variantPanelContainer.getComponentCount() - 1);
                    variantPanelContainer.revalidate();
                } else if (lastPressed.equals("Add Variant 3") && variantButton3Counter > 0) {
                    // Si el último botón presionado fue el de Variante 3, eliminamos el panel correspondiente
                    variantButton3Counter--; // Reducir el contador de Variante 3
                    variantPanelContainer.remove(variantPanelContainer.getComponentCount() - 1);
                    variantPanelContainer.revalidate();
                }

                // Eliminar el último elemento del historial
                buttonPressHistory.remove(buttonPressHistory.size() - 1);
            }
        }
    }

    
    private static void browseFile() {
        // Crear un explorador de archivos para seleccionar un archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Seleccionar archivo");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Safetensors", "safetensors"));
        
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            fileDirectoryField.setText(selectedFile.getParent());
            fileNameField.setText(selectedFile.getName());
        }
    }

    private static void loadFile() {
        // Obtener la dirección del archivo y el nombre del archivo desde los campos de texto
        String fileDirectory = fileDirectoryField.getText();
        String fileName = fileNameField.getText();

        // Construir la ruta completa del archivo
        File file = new File(fileDirectory, fileName);
        

        // Validar si el archivo existe
        if (!file.exists()) {
            // Si el archivo no existe, mostrar un mensaje de error
            JOptionPane.showMessageDialog(frame, "The file does not exist at the address provided.");
            return;  // Salir de la función si el archivo no existe
        }

        // Validar si el archivo tiene la extensión correcta
        if (fileName.endsWith(".safetensors")) {
            // Si el archivo es válido, mostrar el panel JSON y los botones
            jsonPanel.setVisible(true);

            // Asignar los campos de texto a los campos de variante específicos
            fileDirectoryFieldVA.setText(fileDirectory);
            fileNameFieldVA.setText("V");

            // Mostrar los botones de variante
            addVariantButton1.setVisible(true);
            addVariantButton2.setVisible(true);
            addVariantButton3.setVisible(true);
            removeVariantButton.setVisible(true);

            // Mostrar el contenedor de variantes
            variantPanelContainer.setVisible(true);  // Hacer visible el contenedor de variantes
            
            // Hacer visible el botón "Generate"
            generateButton.setVisible(true);
        } else {
            // Si el archivo no tiene la extensión correcta, mostrar un mensaje de error
            JOptionPane.showMessageDialog(frame, "The file is invalid. It must be a file .safetensors.");

            // Ocultar los botones y paneles si el archivo no es válido
            jsonPanel.setVisible(false);
            addVariantButton1.setVisible(false);
            addVariantButton2.setVisible(false);
            addVariantButton3.setVisible(false);
            removeVariantButton.setVisible(false);

            // Ocultar los paneles de variantes
            variantPanelContainer.setVisible(false);

            // Ocultar el botón "Generate"
            generateButton.setVisible(false);
        }

        // Revalidar y repintar el panel de forma explícita para asegurar la correcta actualización del layout
        mainPanel.revalidate();
        mainPanel.repaint();
    }




    private static void generateSymlinkAndJson() {
        // Paso 1: Crear el JSON del archivo original
        generateJsonForMainFile();

        // Paso 2: Generar symlinks y JSON para cada variante
        generateSymlinksAndJsonForVariants();
        
        String fileDirectory = fileDirectoryField.getText();
        String fileName = fileNameField.getText();
        
        updateLog(fileDirectory, fileName);
    }

    private static void generateJsonForMainFile() {
        // Obtener el nombre base sin la extensión ".safetensors"
        String baseFileName = fileNameField.getText().replace(".safetensors", "");

        // Limpiar saltos de línea y caracteres especiales en los textos
        String cleanActivationText = activationTextField.getText().replace("\n", " ").replace("\r", "");
        String cleanNegativeText = negativeTextField.getText().replace("\n", " ").replace("\r", "");
        String cleanDescription = descriptionField.getText().replace("\n", " ").replace("\r", "");
        String cleanNotes = notesField.getText().replace("\n", " ").replace("\r", "");

        // Crear el contenido JSON para el archivo principal
        String jsonContent = "{\n" +
            "  \"description\": \"" + cleanDescription + "\",\n" +
            "  \"sd version\": \"" + sdVersionComboBox.getSelectedItem().toString() + "\",\n" +
            "  \"preferred weight\": " + (preferredWeightSlider.getValue() / 10.0) + ",\n" +
            "  \"activation text\": \"" + cleanActivationText + "\",\n" +
            "  \"negative text\": \"" + cleanNegativeText + "\",\n" +
            "  \"notes\": \"" + cleanNotes + "\"\n" +
            "}";

        // Crear el archivo JSON principal
        File jsonFile = new File(fileDirectoryField.getText() + "\\" + baseFileName + ".json");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile))) {
            writer.write(jsonContent);
            String message = "The main JSON file has been generated successfully in: " + jsonFile.getAbsolutePath();
            logMessages.add(message);
            System.out.println(message);
        } catch (IOException e) {
            String error = "Error generating main JSON file: " + e.getMessage();
            logMessages.add(error);
            System.err.println(error);
        }
    }

    private static void generateSymlinksAndJsonForVariants() {
        for (int i = 0; i < variantPanelContainer.getComponentCount(); i++) {
            JPanel variantPanel = (JPanel) variantPanelContainer.getComponent(i);

            // Recuperar componentes necesarios
            JTextField variantNameField = null;
            JTextField variantDirectoryField = null;
            JTextArea variantActivationTextField = null;
            JTextArea variantNegativeTextField = null;
            JSlider variantPreferredWeightSlider = null;

            for (Component comp : variantPanel.getComponents()) {
                if (comp instanceof JTextField) {
                    if (variantNameField == null) {
                        variantNameField = (JTextField) comp;
                    } else {
                        variantDirectoryField = (JTextField) comp;
                    }
                } else if (comp instanceof JTextArea) {
                    if (variantActivationTextField == null) {
                        variantActivationTextField = (JTextArea) comp;
                    } else if (variantNegativeTextField == null) {
                        variantNegativeTextField = (JTextArea) comp;
                    }
                } else if (comp instanceof JSlider) {
                    variantPreferredWeightSlider = (JSlider) comp;
                }
            }

            if (variantNameField == null || variantDirectoryField == null ||
                variantActivationTextField == null || variantNegativeTextField == null ||
                variantPreferredWeightSlider == null) {
                String error = "Error: Some fields are missing in the variant panel.";
                logMessages.add(error);
                System.err.println(error);
                continue;
            }

            // Limpiar textos
            String cleanActivationText = variantActivationTextField.getText().replace("\n", " ").replace("\r", "");
            String cleanNegativeText = variantNegativeTextField.getText().replace("\n", " ").replace("\r", "");

            // Crear symlink
            String baseFileName = fileNameField.getText().replace(".safetensors", "");
            String symlinkName = baseFileName + "-" + variantNameField.getText();
            Path symlinkPath = Paths.get(variantDirectoryField.getText(), symlinkName + ".safetensors");
            Path targetPath = Paths.get(fileDirectoryField.getText(), baseFileName + ".safetensors");

            try {
                if (!Files.exists(symlinkPath)) {
                    Files.createSymbolicLink(symlinkPath, targetPath);
                    String message = "Symlink created: " + symlinkPath.toString();
                    logMessages.add(message);
                    System.out.println(message);
                } else {
                    String message = "The symlink already exists for: " + symlinkName;
                    logMessages.add(message);
                    System.out.println(message);
                }
            } catch (IOException e) {
                String error = "Error creating symlink for: " + symlinkName + " - " + e.getMessage();
                logMessages.add(error);
                System.err.println(error);
            }

            // Crear JSON para la variante
            String variantJsonContent = "{\n" +
                "  \"description\": \"\",\n" +
                "  \"sd version\": \"" + sdVersionComboBox.getSelectedItem().toString() + "\",\n" +
                "  \"activation text\": \"" + cleanActivationText + "\",\n" +
                "  \"negative text\": \"" + cleanNegativeText + "\",\n" +
                "  \"notes\": \"\",\n" +
                "  \"preferred weight\": " + (variantPreferredWeightSlider.getValue() / 10.0) + "\n" +
                "}";

            File variantJsonFile = new File(variantDirectoryField.getText() + "\\" + symlinkName + ".json");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(variantJsonFile))) {
                writer.write(variantJsonContent);
                String message = "JSON file for variant generated: " + variantJsonFile.getAbsolutePath();
                logMessages.add(message);
                System.out.println(message);
            } catch (IOException e) {
                String error = "Error generating JSON file for variant " + variantNameField.getText() + ": " + e.getMessage();
                logMessages.add(error);
                System.err.println(error);
            }
        }
    }


    private static void loadLog() {
        Path logPath = Paths.get(LOG_FILE_PATH);

        if (Files.exists(logPath)) {
            try {
                // Leer todas las líneas del archivo
                java.util.List<String> lines = Files.readAllLines(logPath);

                // Verificar si el archivo tiene al menos una línea y contiene un formato válido
                if (!lines.isEmpty()) {
                    String firstLine = lines.get(0);
                    String[] parts = firstLine.split(";");

                    // Verificar que la línea tenga al menos 4 partes (para los 4 campos: VB y VC)
                    if (parts.length >= 4) {
                        fileDirectoryField.setText(parts[0]);
                        fileNameField.setText(parts[1]);
                        fileDirectoryFieldVB.setText(parts[2]);
                        fileNameFieldVB.setText(parts[3]);
                        
                        // Si deseas cargar otros valores, como fileDirectoryFieldVC y fileNameFieldVC,
                        // puedes agregarlos de la siguiente manera:
                        if (parts.length >= 6) {
                            fileDirectoryFieldVC.setText(parts[4]);
                            fileNameFieldVC.setText(parts[5]);
                        }
                    } else {
                        System.out.println("Incorrect format in the log. It was expected 'directorio;nombre_archivo;directorio2;nombre_archivo2;...'.");
                        // Manejar caso de formato incorrecto (puedes configurar valores por defecto o alertar al usuario)
                        loadDefaultLog();  // Cargar los valores por defecto
                    }
                } else {
                    System.out.println("Log file is empty.");
                    // Manejar el caso en que el archivo esté vacío (puedes configurar valores por defecto)
                    loadDefaultLog();  // Cargar los valores por defecto
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error reading log file.");
            }
        } else {
            System.out.println("The log file does not exist. Creating a new one...");
            loadDefaultLog();  // Cargar los valores por defecto
        }
    }

    private static void loadDefaultLog() {
        try {
            // Crear el archivo de log si no existe
            Path logPath = Paths.get(LOG_FILE_PATH);
            if (!Files.exists(logPath)) {
                Files.createFile(logPath);
            }

            // Agregar valores predeterminados (por ejemplo, directorio y nombre de archivo vacíos)
            String defaultContent = "C:/directorio/;archivo.safetensors;C:/directorio/;VB;C:/directorio/;VC"; // O el contenido predeterminado que desees
            Files.write(logPath, defaultContent.getBytes());
            System.out.println("Archivo de log creado con valores predeterminados.");

            // Asignar los valores por defecto a los campos
            fileDirectoryField.setText("C:/directorio/");
            fileNameField.setText("archivo.safetensors");
            fileDirectoryFieldVB.setText("C:/directorio/");
            fileNameFieldVB.setText("VB");
            fileDirectoryFieldVC.setText("C:/directorio/");
            fileNameFieldVC.setText("VC");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating log file.");
        }
    }

    private static void updateLog(String fileDirectory, String fileName) {
        try {
            // Leer el contenido existente del log.txt
            String existingContent = "";
            File logFile = new File(LOG_FILE_PATH);
            if (logFile.exists()) {
                existingContent = new String(Files.readAllBytes(logFile.toPath()));
            }

            // Crear la nueva entrada de log con los 4 campos
            String logEntry = String.format("%s;%s;%s;%s;%s;%s;%s%s",
                fileDirectory, 
                fileName, 
                fileDirectoryFieldVB.getText(), 
                fileNameFieldVB.getText(), 
                fileDirectoryFieldVC.getText(), 
                fileNameFieldVC.getText(), 
                new java.util.Date(), 
                System.lineSeparator()
            );

            // Escribir la nueva entrada seguida de los mensajes almacenados y el contenido existente
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH))) {
                writer.write(logEntry); // Escribir la nueva entrada de log al principio

                // Escribir los mensajes almacenados en logMessages (si hay)
                for (String logMessage : logMessages) {
                    writer.write(logMessage + System.lineSeparator()); // Agregar cada mensaje seguido de un salto de línea
                }

                // Escribir el contenido previo del archivo después
                writer.write(existingContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
