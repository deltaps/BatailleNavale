package vue;

import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Classe permettant d'afficher les bateaux à placer, grille (de cases rouge) qui est présente lors de la phase 1.
public class VuePlacer extends JPanel {

    private final Controller controller;
    private final int tailleImage;

    public VuePlacer(Controller controller) {
        this.setSize(new Dimension(450,450)); // Défini la taille de ce composant à 450 de largeur et 450 de hauteur.
        this.setLocation(464,0); // Place ce composant en haut (y = 0) à partir du milieu (x = 464) de l'écran.

        this.controller = controller;
        this.tailleImage = 45; // La taille de toutes les images est de 45, cette variable est une constante et est la plupart du temps multipliée à des coordonnées pour pouvoir afficher correctement l'interface graphique.
    }


    // Fonction appelée lorsque l'on exécute la méthode setVisible(true), sert à afficher tous les éléments grâce au Grapics g qui est défini automatiquement.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) { // Deux boucles allant de 0 à 10 pour pouvoir remplir toute la fenêtre.
                g.drawImage(Images.imageCasePlacement, x * tailleImage, y * tailleImage, null); // Affiche les cases une à une. Place donc une case tous les x*45 et y*45 pixels.
            }
        }
        afficheBateauAPlacer(g, this.controller);
    }


    // Méthode qui place les bateaux à placer en fonction de l'ArrayList "bateauAPlacer" du controller, mais aussi de la rotation choisie par le joueur (détaillée dans controller).
    public void afficheBateauAPlacer(Graphics g, Controller controller) {
        ArrayList<Integer> bateauAPlacer = controller.getBateauAPlacer();
        if(!bateauAPlacer.isEmpty()) { // true si l'ArrayList "bateauAPlacer" n'est pas vide.
            if(controller.getRotation() == 0) { // Si la rotation est verticale.
                for(int x = 0; x < bateauAPlacer.size(); x++) { // Boucle sur la taille de l'ArrayList "bateauAPlacer".
                    for(int y = 0; y < bateauAPlacer.get(x); y++) { // Boucle sur la taille du bateau.

                        g.drawImage(Images.bateau, x * this.tailleImage * 2, y * this.tailleImage + 90, null); // Le "* 2" du second paramètre permet de placer les bateaux avec 45 pixels de différence, soit une case. Le "+ 90" du troisième paramètre permet de placer les bateaux à 90 pixel par rapport au haut de ce composant, soit de deux cases.
                    }
                }
            }
            else { // Si la rotation est horizontale.
                for(int y = 0; y < bateauAPlacer.size(); y++) {
                    for(int x = 0; x < bateauAPlacer.get(y); x++) { // Inversion de x et y en fonction de la rotation.
                        g.drawImage(Images.bateau, x * this.tailleImage + 90, y * 2 * this.tailleImage, null); // Le "* 2" du troisième paramètre permet de placer les bateaux avec 45 pixels de différence, soit une case. Le "+ 90" du second paramètre permet de placer les bateaux à 90 pixel par rapport au bord gauche de ce composant, soit de deux cases.
                    }
                }
            }
        }
    }
}
