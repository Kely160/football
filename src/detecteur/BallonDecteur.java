package detecteur;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import elements.Ballon;

public class BallonDecteur {
    public static Ballon detecter(Mat image) {
        if (image == null || image.empty()) {
            throw new IllegalArgumentException("L'image ne peut pas être nulle ou vide.");
        }
    
        // Conversion en niveaux de HSV
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);
    
        // Application d'un seuil binaire pour isoler les objets noirs
        Mat noirImage = new Mat();
        Scalar seuilInferieur = new Scalar(0, 0, 0); // Noir - Couleur basse
        Scalar seuilSuperieur = new Scalar(180, 255, 50); // Plage du noir (HSV)
        Core.inRange(hsvImage, seuilInferieur, seuilSuperieur, noirImage); // Filtrer uniquement les nuances noires
    
        // Suppression du bruit avec un filtre morphologique
        Mat imageNettoyee = new Mat();
        Imgproc.morphologyEx(noirImage, imageNettoyee, Imgproc.MORPH_CLOSE,
                Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));
    
        // Détection des contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchie = new Mat();
        Imgproc.findContours(imageNettoyee, contours, hierarchie, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
    
        for (MatOfPoint contour : contours) {
            // Vérifiez si le contour contient un nombre suffisant de points
            if (contour.total() < 5) {
                continue;
            }
    
            // Vérification de l'aire pour éviter les objets trop petits
            double aire = Imgproc.contourArea(contour);
            if (aire < 10) { // Ajustez ce seuil en fonction de vos besoins
                continue;
            }
    
            // Approximation pour détecter une forme circulaire
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            RotatedRect ellipse = Imgproc.fitEllipse(contour2f);
    
            // Vérification de la circularité
            double ratio = Math.abs(ellipse.size.width / ellipse.size.height - 1);
            if (ratio < 0.2) { // Tolérance pour des formes légèrement ovales
                Point position = ellipse.center;
                Rect dimension = new Rect(
                        (int) ellipse.center.x - (int) ellipse.size.width / 2,
                        (int) ellipse.center.y - (int) ellipse.size.height / 2,
                        (int) ellipse.size.width,
                        (int) ellipse.size.height);
    
                Ballon ballon = new Ballon(position, dimension);
                ballon.encadrer(image);
    
                return ballon;
            }
        }
    
        System.err.println("Aucun ballon détecté");
        return null;
    }
}
