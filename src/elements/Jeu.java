package elements;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import detecteur.BallonDecteur;
import detecteur.ButDetecteur;

public class Jeu {
    Ballon ballon;
    But[] buts;
    Equipe equipeAttaquant;
    Equipe equipeDeffensive;

    public Jeu() {
    }

    public Jeu(Mat image) throws Exception {
        setBallon(BallonDecteur.detecter(image));
        setButs(ButDetecteur.detecter(image));
        Equipe[] equipes = new Equipe[2];
        equipes[0] = new Equipe(image, new Scalar(0, 100, 100), new Scalar(10, 255, 255), this.getButs());
        equipes[1] = new Equipe(image, new Scalar(90, 50, 0), new Scalar(130, 255, 255), this.getButs());
        this.setEquipes(equipes);
    }

    public Ballon getBallon() {
        return this.ballon;
    }

    public But[] getButs() {
        return this.buts;
    }

    public Equipe getEquipeAttaquant() {
        return this.equipeAttaquant;
    }

    public Equipe getEquipeDeffensive() {
        return this.equipeDeffensive;
    }

    public void setBallon(Ballon ballon) throws Exception {
        if (ballon == null)
            throw new Exception("Aucun ballon détecter");
        this.ballon = ballon;
    }

    public void setButs(But[] buts) throws Exception {
        if (buts == null)
            throw new Exception("Aucun but détecter");
        if (buts.length != 2)
            throw new Exception("Nombre de but invalide");
        this.buts = buts;
    }

    public void setEquipes(Equipe[] equipes) throws Exception {
        if (equipes.length != 2)
            throw new Exception("Nombred d'équipe invalide");

        Equipe equipeAttaquant = equipes[0].getEquipeAttaquant(this.ballon, equipes);
        if (equipeAttaquant == null) {
            throw new Exception("Impossible de déterminer l'équipe attaquante");
        }

        this.equipeAttaquant = equipeAttaquant;
        this.equipeDeffensive = (equipeAttaquant == equipes[0]) ? equipes[1] : equipes[0];
    }

    public boolean estAttaqueBasHaut() {
        Joueur attaquant = this.getEquipeAttaquant().getJoueurLePlusProcheDuBallon(this.getBallon());
        Joueur gardienAdverse = this.getEquipeDeffensive().getGardien();

        if (attaquant.getPosition().y < gardienAdverse.getPosition().y) {
            return true;
        }

        return false;
    }

    public void detecterHJ(Mat image) {
        List<Joueur> attaquants = this.getEquipeAttaquant().getAttaquants(this.getBallon(), this.estAttaqueBasHaut());
        Joueur dernierDefenseur = this.getEquipeDeffensive().getDernierDefenseur();
        dernierDefenseur.setStatut("D");
        dernierDefenseur.annoter(image);

        for (Joueur a : attaquants) {
            if (a.estHJ(dernierDefenseur, this.estAttaqueBasHaut())) {
                a.setStatut("HJ");
                a.annoter(image);
            } else {
                a.setStatut("N");
                a.annoter(image);
            }
        }

    }

    public String analyserImages(Mat mat1, Mat mat2) throws Exception {
        String resultat = null;

        Jeu image1 = new Jeu(mat1);
        Jeu image2 = new Jeu(mat2);

        Joueur attaquant = image1.getEquipeAttaquant().getJoueurLePlusProcheDuBallon(image1.getBallon());
        Joueur deffenseur = image1.getEquipeDeffensive().getDernierDefenseur();

        if (!attaquant.estHJ(deffenseur, image1.estAttaqueBasHaut())) {
            if (image2.getBallon().estBut(image2.getButs())) {
                resultat = "Il y a eu un but";
            } else {
                resultat = "Aucun but";
            }
        } else {
            resultat = "Le but n'est pas valide, l'attaquant est HJ ";
        }

        return resultat;
    }
}
