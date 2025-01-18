package affichage;

import javax.swing.*;
import java.awt.*;

public class FenetrePrincipale {
    public FenetrePrincipale() {
         JFrame frame = new JFrame("FOOT S5");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("FOOT S5", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel principal pour le contenu
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        frame.add(mainPanel, BorderLayout.CENTER);

        // Ajouter le panneau des scores
        ScorePanel scorePanel = new ScorePanel("Rouge", 1, "Bleu", 2);
        mainPanel.add(scorePanel);

        // Ajouter le panneau des images
        ImagePanel imagePanel = new ImagePanel();
        mainPanel.add(imagePanel);

        // Bouton Analyser
        JButton analyzeButton = new JButton("Analyser");
        JPanel analyzePanel = new JPanel();
        analyzePanel.add(analyzeButton);
        mainPanel.add(analyzePanel);

        // Rendre la fenêtre visible
        frame.setVisible(true);
    }
}
