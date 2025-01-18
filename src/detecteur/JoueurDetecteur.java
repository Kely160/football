package detecteur;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import elements.Joueur;

public class JoueurDetecteur {
    public static Joueur[] detecterJoueurMemeEquipe(Mat image, Scalar limiteInferieure, Scalar limiteSuperieure) {
        if (image == null || image.empty()) {
            System.err.println("Erreur : Impossible de charger l'image.");
            return new Joueur[0];
        }

        // Convertir l'image en espace de couleurs HSV
        Mat imageHSV = new Mat();
        Imgproc.cvtColor(image, imageHSV, Imgproc.COLOR_BGR2HSV);

        // Créer un masque basé sur les limites de couleurs pour détecter les uniformes de l'équipe
        Mat masqueEquipe = new Mat();
        Core.inRange(imageHSV, limiteInferieure, limiteSuperieure, masqueEquipe);

        // Trouver les contours des zones correspondant à la couleur de l'équipe
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchie = new Mat();
        Imgproc.findContours(masqueEquipe, contours, hierarchie, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        if (contours.isEmpty()) {
            System.err.println("Aucun joueur détecté.");
            return new Joueur[0];
        }

        // Liste pour stocker les joueurs détectés
        List<Joueur> listeJoueurs = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            // Récupérer le rectangle englobant
            Rect rectangleEnglobant = Imgproc.boundingRect(contour);

            // Filtrer les petites zones (bruit ou non joueurs)
            if (rectangleEnglobant.height > 10 && rectangleEnglobant.width > 10) {
                // Calculer la position centrale du joueur
                Point positionCentrale = new Point(
                        rectangleEnglobant.x + rectangleEnglobant.width / 2.0,
                        rectangleEnglobant.y + rectangleEnglobant.height / 2.0
                );

                // Définir une couleur pour dessiner le rectangle
                Scalar couleurRectangle = ajusterCouleur(limiteInferieure, -20);

                try {
                    // Créer un objet Joueur avec la position et les dimensions détectées
                    Joueur joueur = new Joueur(positionCentrale, rectangleEnglobant);
                    joueur.encadrer(image, couleurRectangle, 2); // Rectangle épaisseur 2px
                    listeJoueurs.add(joueur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return listeJoueurs.toArray(new Joueur[0]);
    }

    private static Scalar ajusterCouleur(Scalar couleur, int delta) {
        double[] valeurs = couleur.val;
        for (int i = 0; i < valeurs.length; i++) {
            valeurs[i] = Math.min(Math.max(valeurs[i] + delta, 0), 255);
        }
        return new Scalar(valeurs);
    }
}
