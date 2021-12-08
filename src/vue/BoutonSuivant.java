package vue;

import controller.Controller;
import javax.swing.*;
import java.awt.*;

// Cette classe crée un bouton permettant de changer de joueur courant, ou de phase selon l'avancement de la partie, voir de rejouer au jeu si la partie est finie. Il y a plus de détail dans controller.Controller.
public class BoutonSuivant extends JButton {

    private final Controller controller;

    public BoutonSuivant(Controller controller, int width, int x) {
        this.controller = controller;

        this.setText("Suivant"); // Ajoute le texte "Suivant" sur le bouton.
        this.setSize(new Dimension(width,110)); // Défini la taille de ce composant à "width" de largeur et 110 de hauteur.
        this.setLocation(x,465); // Place le composant à x pixel du bord gauche de la fenêtre et à 465 pixels du bord haut.


        // Ajoute un écouteur lorsque l'on appuie sur ce composant, permettant d'exécuter la fonction "suivant()" du controller.
        this.addActionListener(e -> this.controller.suivant());
    }
}