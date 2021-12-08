package vue;

import controller.Controller;
import javax.swing.*;
import java.awt.*;

// Cette classe crée un bouton permettant de changer la rotation des bateaux à placer.
public class BoutonRotation extends JButton {

    private final Controller controller;

    public BoutonRotation(Controller controller) {
        this.controller = controller;

        this.setText("Rotation"); // Ajoute le texte "Rotation" sur le bouton.
        this.setSize(new Dimension(456,110)); // Défini la taille de ce composant à 465 de largeur et 150 de hauteur.
        this.setLocation(0,465); // Place le composant en bas du plateau du joueur courant, à gauche.


        // Ajoute un écouteur lorsque l'on appuie sur ce composant qui va exécuter la fonction "changeRotation()" du controller.
        this.addActionListener(e -> this.controller.changeRotation());
    }
}