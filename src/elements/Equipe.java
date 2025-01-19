package elements;

import java.util.ArrayList;
import java.util.List;

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

    public Equipe(Mat image, Scalar limiteInferieureCouleur, Scalar limiteSuperieureCouleur, But[] buts)
            throws Exception {
        this.setCouleur(limiteInferieureCouleur, limiteSuperieureCouleur);
        this.setJoueurs(
                JoueurDetecteur.detecterJoueurMemeEquipe(image, limiteInferieureCouleur, limiteSuperieureCouleur,
                        buts));
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

    // Méthodes
    public Joueur getJoueurLePlusProcheDuBallon(Ballon ballon) {
        if (joueurs == null || joueurs.length == 0 || ballon == null) {
            return null;
        }

        Joueur joueurLePlusProche = null;
        double distanceMin = Double.MAX_VALUE;

        for (Joueur joueur : joueurs) {
            if (joueur != null) {
                double distance = Math.sqrt(Math.pow(joueur.getPosition().x - ballon.getPosition().x, 2) +
                        Math.pow(joueur.getPosition().y - ballon.getPosition().y, 2));

                if (distance < distanceMin) {
                    distanceMin = distance;
                    joueurLePlusProche = joueur;
                }
            }
        }

        return joueurLePlusProche;
    }

    public Equipe getEquipeAttaquant(Ballon ballon, Equipe[] equipes) {
        if (ballon == null || equipes == null || equipes.length == 0) {
            return null;
        }

        Equipe equipeAttaquant = null;
        double distanceMin = Double.MAX_VALUE;

        for (Equipe equipe : equipes) {
            if (equipe != null) {
                Joueur joueurLePlusProche = equipe.getJoueurLePlusProcheDuBallon(ballon);

                if (joueurLePlusProche != null) {
                    double distance = Math
                            .sqrt(Math.pow(joueurLePlusProche.getPosition().x - ballon.getPosition().x, 2) +
                                    Math.pow(joueurLePlusProche.getPosition().y - ballon.getPosition().y, 2));

                    if (distance < distanceMin) {
                        distanceMin = distance;
                        equipeAttaquant = equipe;
                    }
                }
            }
        }

        return equipeAttaquant;
    }

    public Joueur getGardien() {
        for (Joueur joueur : this.getJoueurs()) {
            if (joueur != null && "G".equals(joueur.getStatut())) {
                return joueur;
            }
        }
        return null;
    }

    public List<Joueur> getAttaquants(Ballon ballon, boolean estAttaqueBasHaut) {
        List<Joueur> attaquants = new ArrayList<>();

        if (ballon == null || joueurs == null || joueurs.length == 0) {
            return attaquants;
        }

        double positionBallonY = ballon.getPosition().y - ballon.getDimension().height;

        for (Joueur joueur : joueurs) {
            if (joueur != null) {
                double positionJoueurY = joueur.getPosition().y - joueur.getDimension().height;

                boolean devantBallon = estAttaqueBasHaut
                        ? positionJoueurY > positionBallonY 
                        : positionJoueurY < positionBallonY;

                if (devantBallon) {
                    attaquants.add(joueur);
                }
            }
        }

        return attaquants;
    }

    public Joueur getDernierDefenseur() {
        Joueur gardien = getGardien();
        if (gardien == null || joueurs == null || joueurs.length == 0) {
            return null;
        }
    
        Joueur dernierDefenseur = null;
        double distanceMin = Double.MAX_VALUE;
    
        for (Joueur joueur : joueurs) {
            if (joueur != null && !"G".equals(joueur.getStatut())) { 
                double distanceY = Math.abs(joueur.getPosition().y - gardien.getPosition().y);
                if (distanceY < distanceMin) {
                    distanceMin = distanceY;
                    dernierDefenseur = joueur;
                }
            }
        }
    
        return dernierDefenseur;
    }    
}
