package detecteur;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import elements.But;

public class ButDetecteur {
    public static But[] detecter(Mat image) {
        if (image == null || image.empty()) {
            throw new IllegalArgumentException("L'image ne peut pas être nulle ou vide.");
        }

        // Conversion en niveaux de gris
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Flou pour réduire le bruit
        Mat blurred = new Mat();
        Imgproc.GaussianBlur(grayImage, blurred, new org.opencv.core.Size(5, 5), 0);

        // Détection des bords avec Canny
        Mat edges = new Mat();
        Imgproc.Canny(blurred, edges, 50, 150);

        // Détection des contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        List<But> detectedGoals = new ArrayList<>();

        // Analyse des contours pour détecter des rectangles aux extrémités de l'image
        for (MatOfPoint contour : contours) {
            Rect rect = Imgproc.boundingRect(contour);

            // Vérification que le rectangle est situé en haut ou en bas de l'image
            if (rect.y < image.height() * 0.2 || rect.y > image.height() * 0.8) {
                // Vérification des proportions typiques d'un but
                double aspectRatio = (double) rect.width / rect.height;
                if (aspectRatio > 1.5 && aspectRatio < 6) { // Large plage de proportions
                    But detectedGoal = new But(rect.tl(), rect);
                    detectedGoal.encadrer(image);
                    detectedGoals.add(detectedGoal);
                }
            }
        }

        if (detectedGoals.isEmpty()) {
            System.err.println("Aucun but détecté.");
            return null;
        }

        return detectedGoals.toArray(new But[0]);
    }
}
