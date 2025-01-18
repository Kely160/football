package elements;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import util.Entite;

public class Joueur extends Entite {
    String statut;

    public Joueur() {
        super();
    }

    public Joueur(Point point, Rect dimension) {
        super(point, dimension);
    }

    public Joueur(Point point, Rect dimension, String statut) {
        super(point, dimension);
        this.setStatut(statut);
    }

    public String getStatut() {
        return this.statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public static void encadrer(Mat image, MatOfPoint contour, Scalar couleurContour) {
        Rect rectangleEnglobant = Imgproc.boundingRect(contour);
        Imgproc.rectangle(image, rectangleEnglobant, couleurContour, 2);
    }
}
