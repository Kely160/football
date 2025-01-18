package util;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Entite {
    Point position;
    Rect dimension;

    public Entite() {
    }

    public Entite(Point position, Rect dimension) {
        this.setPosition(position);
        this.setDimension(dimension);
    }

    public Point getPosition() {
        return this.position;
    }

    public Rect getDimension() {
        return this.dimension;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setDimension(Rect dimension) {
        this.dimension = dimension;
    }

    public void encadrer(Mat image, Scalar couleur, int epaisseur) {
        if (image != null && dimension != null) {
            Point hautGauche = new Point(dimension.x, dimension.y);
            Point basDroite = new Point(dimension.x + dimension.width, dimension.y + dimension.height);
            Imgproc.rectangle(image, hautGauche, basDroite, couleur, epaisseur);
        }
    }
}
