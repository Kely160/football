package elements;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import util.Entite;

public class But extends Entite {

    public But() {
        super();
    }

    public But(Point point, Rect dimension) {
        super(point, dimension);
    }

    public void encadrer(Mat image) {
        super.encadrer(image, new Scalar(0, 255, 0), 2);  // Encadré en vert pour les buts détectés
    }
}
