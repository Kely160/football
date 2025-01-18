package affichage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImagePanel extends JPanel{
    public JLabel image1Label;
    public JButton browseButton1;
    public JLabel image2Label;
    public JButton browseButton2;

    public ImagePanel() {
        setLayout(new GridLayout(1, 2, 20, 0));

        // Image 1
        JPanel image1Panel = new JPanel(new BorderLayout(10, 10));
        image1Label = new JLabel("Image 1", SwingConstants.CENTER);
        image1Label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        image1Label.setPreferredSize(new Dimension(150, 200)); // Format portrait
        browseButton1 = new JButton("Parcourir");
        browseButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(image1Label);
            }
        });
        image1Panel.add(image1Label, BorderLayout.CENTER);
        image1Panel.add(browseButton1, BorderLayout.SOUTH);

        // Image 2
        JPanel image2Panel = new JPanel(new BorderLayout(10, 10));
        image2Label = new JLabel("Image 2", SwingConstants.CENTER);
        image2Label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        image2Label.setPreferredSize(new Dimension(150, 200)); // Format portrait
        browseButton2 = new JButton("Parcourir");
        browseButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectImage(image2Label);
            }
        });
        image2Panel.add(image2Label, BorderLayout.CENTER);
        image2Panel.add(browseButton2, BorderLayout.SOUTH);

        add(image1Panel);
        add(image2Panel);
    }

    private void selectImage(JLabel label) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            Image scaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
            label.setText("");
        }
    }
}
