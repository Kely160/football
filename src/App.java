import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

import detecteur.BallonDecteur;
import detecteur.ButDetecteur;
import detecteur.JoueurDetecteur;
import elements.Ballon;
import elements.But;
import elements.Equipe;
import elements.Joueur;

public class App {
    public static void main(String[] args) throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Imgcodecs.imread("D://Foot-image/Ito.jpg");
        Ballon ballon = BallonDecteur.detecter(image);
        But[] buts = ButDetecteur.detecter(image);
        Equipe equipe1 = new Equipe(image, new Scalar(0, 100, 100), new Scalar(10, 255, 255));
        Equipe equipe2 = new Equipe(image, new Scalar(90, 50, 0), new Scalar(130, 255, 255));

        Joueur gardien1 = equipe1.getGardien(image, buts);
        Joueur gardien2 = equipe2.getGardien(image, buts);


        Imgcodecs.imwrite("./Image_Modifiee.jpg", image);

        //FenetrePrincipale fenetre = new FenetrePrincipale();
    }
}
