package elements;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import util.Entite;

public class Ballon extends Entite {
    public Ballon() {
        super();
    }

    public Ballon(Point point, Rect dimension) {
        super(point, dimension);
    }

    public void encadrer(Mat image) {
        super.encadrer(image, new Scalar(0, 255, 255), 2);
    }

    public boolean estBut(But[] buts) {
        for (But but : buts) {
            Rect rectBut = but.getDimension();  
            
            if (rectBut.contains(this.getPosition())) { 
                return true;  
            }
        }
        
        return false; 
    }
}
