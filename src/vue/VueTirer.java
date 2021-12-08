package vue;

import javax.swing.*;
import java.awt.*;

import model.ChampBataille;

// Classe qui va permettre de remplacer la classe VuePlacer lors de la phase 2, qui va afficher le plateau du joueur adverse en cachant les bateaux (sauf ceux coulés).
// Prend en paramètre le champBataille du joueur adverse, pour que le joueur courant tire sur une des cases.
public class VueTirer extends JPanel {

    private final ChampBataille champBataille;
    private final int tailleImage;

    public VueTirer(ChampBataille champBataille) {
        this.setSize(new Dimension(450,450)); // Défini la taille de ce composant à 450 de largeur et 450 de hauteur.
        this.setLocation(464,0); // Place ce composant en haut (y = 0) à partir du milieu (x = 464) de l'écran.

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
                afficheBateauCoule(g, this.champBataille, x, y);
                afficheTir(g, this.champBataille, x, y);
            }
        }
    }


    // Méthode qui va permettre d'afficher les bateaux qui sont coulés uniquement, donc qui contiennent des tirs sur toutes leurs cases.
    public void afficheBateauCoule(Graphics g, ChampBataille champBataille, int x, int y) {
        if(champBataille.getCase(x, y).bateauHere() != null) { // true si un bateau est sur la case de coordonnées (x,y). Cette vérification permet d'être sur qu'un bateau est placé ici, avant de regarder s'il est coulé.
            if (champBataille.getCase(x, y).bateauHere().getCoule()) { // true si le bateau de cette case est coulé.
                g.drawImage(Images.bateau, x * this.tailleImage, y * this.tailleImage, null);
            }
        }
    }

    // Méthode qui va permettre d'afficher les tirs effectués par le joueur courant sur le plateau du joueur adverse. Affiche une croix rouge si le tir est touché, une croix verte si le tir est raté, sur la case de coordonnées (x,y).
    public void afficheTir(Graphics g, ChampBataille champBataille, int x, int y) {
        if(champBataille.getCase(x, y).getToucher()) { // true si il y a déjà eu un tir sur la case de coordonnées (x,y).
            if(champBataille.getCase(x, y).bateauHere() != null) { // true si un bateau est sur la case de coordonnées (x,y).
                g.drawImage(Images.tirTouche, x * this.tailleImage, y * this.tailleImage, null);
            }
            else {
                g.drawImage(Images.tirRate, x * this.tailleImage, y * this.tailleImage, null);
            }
        }
    }
}