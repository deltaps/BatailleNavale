package vue;

import javax.swing.*;
import java.awt.*;
import model.ChampBataille;

// Classe qui va permettre d'afficher le plateau du joueur courant. Il sera toujours placé à gauche, peu importe dans quelle phase l'on se trouve.
// Elle prend en paramètre le ChampBataille du joueur courant, pour en afficher son contenu (bateau, tir, ...)
public class VueJoueurCourant extends JPanel {

    private final ChampBataille champBataille;
    private final int tailleImage;

    public VueJoueurCourant(ChampBataille champBataille) {
        this.setSize(new Dimension(450,450)); // Défini la taille de ce composant à 450 de largeur et 450 de hauteur.
        this.setLocation(0,0); // Place ce composant en haut (y = 0) à gauche (x = 0) de l'écran.

        this.champBataille = champBataille;

        this.tailleImage = 45; // La taille de toutes les images est de 45, cette variable est une constante et est la plupart du temps multipliée à des coordonnées pour pouvoir afficher correctement l'interface graphique.
    }


    // Fonction appelée lorsque l'on exécute la méthode setVisible(true), sert à afficher tous les éléments grâce au Grapics g qui est défini automatiquement.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) { // Deux boucles allant de 0 à 9 car le plateau de jeu fait 10x10 en taille (100 cases).
                g.drawImage(Images.imageCase, x * tailleImage, y * tailleImage, null); // Affiche les cases une à une. Place donc une case tous les x*45 et y*45 pixels.
                afficheBateau(g, this.champBataille, x, y);
                afficheTir(g, this.champBataille, x, y);
            }
        }
    }


    // Méthode qui va permettre d'afficher les bateaux.
    public void afficheBateau(Graphics g, ChampBataille champBataille, int x, int y) {
        if(champBataille.getCase(x,y).bateauHere() != null) { // true si un bateau est sur la case de coordonnées (x,y).
            g.drawImage(Images.bateau, x * this.tailleImage, y * this.tailleImage, null);
        }
    }

    // Méthode qui va afficher les tirs du joueur adverse. Si le tir est touché, la croix est rouge. Si le tir est raté, la croix est verte.
    public void afficheTir(Graphics g, ChampBataille champBataille, int x, int y) {
        if(champBataille.getCase(x, y).getToucher()) { // true si il y a déjà eu un tir sur cette case.
            if(champBataille.getCase(x, y).bateauHere() != null) {
                g.drawImage(Images.tirTouche, x * this.tailleImage, y * this.tailleImage, null);
            }
            else {
                g.drawImage(Images.tirRate, x * this.tailleImage, y * this.tailleImage, null);
            }
        }
    }
}
