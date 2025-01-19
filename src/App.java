import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

import affichage.FenetrePrincipale;
import detecteur.BallonDecteur;
import detecteur.ButDetecteur;
import elements.Ballon;
import elements.But;
import elements.Equipe;
import elements.Jeu;
import elements.Joueur;

public class App {
    public static void main(String[] args) throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // Mat image = Imgcodecs.imread("D://Foot-image/Ito.jpg");

        // Jeu jeu = new Jeu(image);
        // jeu.detecterHJ(image);

        // Imgcodecs.imwrite("./Image_Modifiee.jpg", image);

        new FenetrePrincipale().setVisible(true);
    }
}
