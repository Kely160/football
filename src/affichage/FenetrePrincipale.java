package affichage;

import javax.swing.*;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import elements.Jeu;

import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class FenetrePrincipale extends JFrame {
    private JTextField imageField1;
    private JTextField imageField2;
    private JButton browseButton1;
    private JButton browseButton2;
    private JButton analyzeButton;

    public FenetrePrincipale() {
        setTitle("Analyseur d'Images");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Initialize components
        imageField1 = new JTextField(20);
        imageField1.setEditable(false);
        imageField2 = new JTextField(20);
        imageField2.setEditable(false);

        browseButton1 = new JButton("Parcourir");
        browseButton2 = new JButton("Parcourir");
        analyzeButton = new JButton("Analyser");

        // Add components with GridBagLayout
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // First row
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Image 1:"), gbc);

        gbc.gridx = 1;
        add(imageField1, gbc);

        gbc.gridx = 2;
        add(browseButton1, gbc);

        // Second row
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Image 2:"), gbc);

        gbc.gridx = 1;
        add(imageField2, gbc);

        gbc.gridx = 2;
        add(browseButton2, gbc);

        // Analyze button (centered, bottom)
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        add(analyzeButton, gbc);

        // Add action listeners
        browseButton1.addActionListener(e -> browseImage(imageField1));
        browseButton2.addActionListener(e -> browseImage(imageField2));
        analyzeButton.addActionListener(e -> analyzeImages());

        // Window settings
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void browseImage(JTextField field) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg")
                        || f.getName().toLowerCase().endsWith(".jpeg")
                        || f.getName().toLowerCase().endsWith(".png");
            }

            public String getDescription() {
                return "Images (*.jpg, *.jpeg, *.png)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            field.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void analyzeImages() {
        if (imageField1.getText().isEmpty() || imageField2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner deux images avant d'analyser.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Mat mat1 = Imgcodecs.imread(imageField1.getText());
            Mat mat2 = Imgcodecs.imread(imageField2.getText());

            if (mat1.empty() || mat2.empty()) {
                throw new Exception("Impossible de charger les images en tant que Mat.");
            }

            // Créer une nouvelle fenêtre pour l'analyse
            JFrame analyseFrame = new JFrame("Résultats de l'analyse");
            analyseFrame.setLayout(new BorderLayout());

            // Afficher les images originales avec OpenCV
            JPanel imagesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
            imagesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Convertir les Mat en BufferedImage pour l'affichage dans Swing
            JLabel imgLabel1 = new JLabel(new ImageIcon(convertMatToImage(mat1)));
            JLabel imgLabel2 = new JLabel(new ImageIcon(convertMatToImage(mat2)));

            imgLabel1.setBorder(BorderFactory.createTitledBorder("Image 1"));
            imgLabel2.setBorder(BorderFactory.createTitledBorder("Image 2"));

            imagesPanel.add(imgLabel1);
            imagesPanel.add(imgLabel2);

            // Ajouter des informations supplémentaires
            JPanel interpretationPanel = new JPanel();
            interpretationPanel.setBorder(BorderFactory.createTitledBorder("Interprétation"));

            JTextArea interpretation = new JTextArea(5, 40);
            interpretation.setEditable(false);
            interpretation.setWrapStyleWord(true);
            interpretation.setLineWrap(true);
            interpretation.setText(new Jeu().analyserImages(mat1, mat2));

            interpretationPanel.add(new JScrollPane(interpretation));

            // Ajouter les panels à la fenêtre
            analyseFrame.add(imagesPanel, BorderLayout.CENTER);
            analyseFrame.add(interpretationPanel, BorderLayout.SOUTH);

            // Configurer la fenêtre
            analyseFrame.pack();
            analyseFrame.setLocationRelativeTo(this);
            analyseFrame.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du traitement des images: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Convertir un Mat en BufferedImage
    private BufferedImage convertMatToImage(Mat mat) {
        int type = mat.channels() > 1 ? BufferedImage.TYPE_3BYTE_BGR : BufferedImage.TYPE_BYTE_GRAY;
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        mat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
        return image;
    }
}