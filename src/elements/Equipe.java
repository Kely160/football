package elements;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import detecteur.JoueurDetecteur;

public class Equipe {
    int score;
    Scalar limiteInferieureCouleur;
    Scalar limiteSuperieureCouleur;
    Joueur[] joueurs;

    public Equipe() {
    }

    public Equipe(Mat image, Scalar limiteInferieureCouleur, Scalar limiteSuperieureCouleur) throws Exception {
        this.setCouleur(limiteInferieureCouleur, limiteSuperieureCouleur);
        this.setJoueurs(
                JoueurDetecteur.detecterJoueurMemeEquipe(image, limiteInferieureCouleur, limiteSuperieureCouleur));
    }

    public Equipe(Scalar limiteInferieureScalar, Scalar limiteSuperieureScalar, Joueur[] joueurs) throws Exception {
        this.setCouleur(limiteInferieureCouleur, limiteSuperieureCouleur);
        this.setJoueurs(joueurs);
    }

    public int getScore() {
        return this.score;
    }

    public Scalar getLimiteInferieureCouleur() {
        return this.limiteInferieureCouleur;
    }

    public Scalar getLimiteSuperieureCouleur() {
        return this.limiteSuperieureCouleur;
    }

    public Joueur[] getJoueurs() {
        return this.joueurs;
    }

    public void setScore(int scrore) {
        this.score = scrore;
    }

    public void setCouleur(Scalar limiteInferieureCouleur, Scalar limiteSuperieureCouleur) {
        this.limiteInferieureCouleur = limiteInferieureCouleur;
        this.limiteSuperieureCouleur = limiteSuperieureCouleur;
    }

    public void setJoueurs(Joueur[] joueurs) throws Exception {
        if (joueurs == null)
            throw new Exception("Aucun joueur");
        this.joueurs = joueurs;
    }
}
