package affichage;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel{
    private JLabel team1ColorLabel;
    private JLabel team1ScoreLabel;
    private JLabel team2ColorLabel;
    private JLabel team2ScoreLabel;

    public ScorePanel(String team1Color, int team1Score, String team2Color, int team2Score) {
        setLayout(new GridLayout(1, 2, 20, 0));

        // Équipe 1
        JPanel team1Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        team1ColorLabel = new JLabel("Équipe 1: " + team1Color);
        team1ScoreLabel = new JLabel("Score: " + team1Score);
        team1Panel.add(team1ColorLabel);
        team1Panel.add(team1ScoreLabel);

        // Équipe 2
        JPanel team2Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        team2ColorLabel = new JLabel("Équipe 2: " + team2Color);
        team2ScoreLabel = new JLabel("Score: " + team2Score);
        team2Panel.add(team2ColorLabel);
        team2Panel.add(team2ScoreLabel);

        add(team1Panel);
        add(team2Panel);
    }
}
