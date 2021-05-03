package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// Classe de l'écouteur (Listener) de la souris, faite avec MouseAdapter qui permet d'utiliser la méthode mousePressed(Mouse Event e).
public class EcouteurSouris extends MouseAdapter{

    private final Controller controller;
    boolean deuxiemeClic = false;
    int numeroBateau = -1;

    public EcouteurSouris(Controller controller) {
        this.controller = controller;
    }


    // Fonction de MouseAdapter qui s'active dès qu'un clique de souris est détecté.
    // La variable e de type MouseEvent permet de récupérer la position de la souris à l'instant où l'utilisateur a cliqué.
    @Override
    public void mousePressed(MouseEvent e) {
        int xSouris = e.getX() -8;
        int ySouris = e.getY() -30; // getX() et getY() permettent d'avoir la position de la souris. Ces positions sont ajustées pour bien correspondre à la position réelle de chaque élément, d'où le "-7" et le "-30".

        int numeroCaseColonneGrilleGauche = getNumeroCaseColonneGrilleGauche(xSouris); // Variable correspondant à l'abscisse de la case cliquée du tableau de gauche, soit celui du joueur courant.
        int numeroCaseColonneGrilleDroite = getNumeroCaseColonneGrilleDroite(xSouris); // Variable correspondant à l'abscisse de la case cliquée du tableau de droite.
        int numeroCaseLigne = getNumeroCaseLigne(ySouris); // Variable correspondant à l'ordonnée de la case cliquée, qui est la même sur les deux tableaux car ils sont placés à coté l'un de l'autre.
        //Ces trois variables sont très utiles pour travailler avec les valeurs réelles du model, la position de la souris exacte ne sera donc plus utile.

        // L'écouteur est différent en fonction de la phase, celle de placement étant la plus complexe, celle-ci est donc assez longue.
        if(this.controller.getPhase() == 1) { // Phase de placement.
            int bateauAPlacerClique = bateauAPlacerClique(numeroCaseColonneGrilleDroite, numeroCaseLigne);

            // Partie qui permet d'annuler le placement d'un bateau qui a déjà été placé.
            if(numeroCaseColonneGrilleGauche != -1 && numeroCaseLigne != -1) { // true si le clique détecté est sur le tableau de gauche.
                if(bateauPlaceClique(numeroCaseColonneGrilleGauche, numeroCaseLigne)) { // true si le clique détecté est sur la bateau.
                    this.controller.annulerPlacement(numeroCaseColonneGrilleGauche, numeroCaseLigne); // Annule le placement d'un bateau, plus de détails dans la classe Controller.
                    deuxiemeClic = false; // Permet de ne pas exécuter les conditions ci dessous.
                }
            }

            // Partie qui permet de placer un bateau sur le tableau de gauche.
            if(deuxiemeClic) { // true si un bateau a déjà été cliqué.
                if(numeroCaseColonneGrilleGauche != -1 && numeroCaseLigne != -1) { // true si le clique détecté est sur le tableau de gauche.
                    this.controller.placement(numeroCaseColonneGrilleGauche, numeroCaseLigne, numeroBateau);
                    if(this.controller.getJoueurCourant().getChampBataille().getCase(numeroCaseColonneGrilleGauche,numeroCaseLigne).bateauHere() != null) { // On vérifie que le bateau a bien été placé.
                        this.deuxiemeClic = false;
                    }
                }
            }

            if(bateauAPlacerClique != -1) { // true si un bateau a été cliqué.
                numeroBateau = bateauAPlacerClique; // Permet de mémoriser le numéro (index de l'ArrayList) du bateau cliqué.
                deuxiemeClic = true; // Permet de mémoriser le fait qu'un bateau a été cliqué
            }
        }

        if(this.controller.getPhase() == 2) { // Phase de tir.
            if(numeroCaseColonneGrilleDroite != -1 && numeroCaseLigne != -1) { // true si le clique détecté est sur le tableau de gauche.
                this.controller.tir(numeroCaseColonneGrilleDroite, numeroCaseLigne); // Permet au joueur courant de tirer sur le plateau adverse, plus de détail dans la classe Controller.
            }
        }
    }


    // Méthode permettant d'obtenir la coordonnées de l'abscisse de la case cliquée, sur le tableau de gauche.
    // La coordonnée de la souris est divisée par 45 car la taille des images est de 45x45.
    public int getNumeroCaseColonneGrilleGauche(int xSouris) {
        int numeroCaseColonne = xSouris / 45;

        if(numeroCaseColonne < -1 || numeroCaseColonne > 9) {
            return -1; // Retourne -1 si le clique n'est pas dans le tableau de gauche.
        }
        return numeroCaseColonne;
    }

    // Méthode permettant d'obtenir la coordonnées de l'abscisse de la case cliquée, sur le tableau de droite.
    // La coordonnée de la souris est soustraite à 14 car c'est la taille de la séparation entre les deux tableaux en pixel. Elle est ensuite divisée par 45 car la taille des images est de 45x45 et le tout est soustrait par 10 pour avoir la coordonnée de la case.
    public int getNumeroCaseColonneGrilleDroite(int xSouris) {
        int numeroCaseColonne = ((xSouris - 14) / 45) - 10;

        if(numeroCaseColonne < -1 || numeroCaseColonne > 9) {
            return -1; // Retourne -1 si le clique n'est pas dans le tableau de droite.
        }
        return numeroCaseColonne;
    }

    // Méthode permettant d'obtenir la coordonnée de l'ordonnée de la case cliquée.
    // La coordonnée de la souris est divisée par 45 car la taille des images est de 45x45.
    public int getNumeroCaseLigne(int ySouris) {
        int numeroCaseLigne = ySouris / 45;
        if(numeroCaseLigne < -1 || numeroCaseLigne > 9) {
            return -1; // Retourne -1 si le clique n'est pas dans la fenêtre
        }
        return numeroCaseLigne;
    }


    // Cette méthode détecte si un bateau du tableau de droite dans la phase 1 (donc un bateau à placer) a été cliqué.
    // Elle dépend de l'ArrayList "bateauAPlacer" de la classe Controller, et aussi de la rotation des bateaux.
    // Retourne l'indice (index) du bateau cliqué par rapport à "bateauAPlacer" ou -1 si aucun bateau n'a été cliqué.
    // Ici on revoit les mêmes opérations que dans la classes vue.VuePlacer
    public int bateauAPlacerClique(int x, int y) {
        ArrayList<Integer> bateauAPlacer = controller.getBateauAPlacer();
        if(!bateauAPlacer.isEmpty()) { // true si la liste bateauAPlacer n'est pas vide.
            if(controller.getRotation() == 0) { // Si le bateau est vertical.
                for(int xCase = 0; xCase < bateauAPlacer.size(); xCase++) { // Boucle sur la taille de l'ArrayList "bateauAPlacer".
                    for(int yCase = 0; yCase < bateauAPlacer.get(xCase); yCase++) { // Boucle sur la taille du bateau.
                        if(x == xCase * 2 && y == yCase + 2) { // Vérifie si un bateau a été cliqué
                            return(xCase);
                        }
                    }
                }
            }
            else { // Si le bateau est horizontal.
                for(int yCase = 0; yCase < bateauAPlacer.size(); yCase++) {
                    for(int xCase = 0; xCase < bateauAPlacer.get(yCase); xCase++) { // Inversion de x et y en fonction de la rotation.
                        if(x == xCase + 2 && y == yCase * 2) { // Vérifie si un bateau a été cliqué
                            return(yCase);
                        }
                    }
                }
            }
        }
        return -1; // Retourne -1 si aucun bateau n'a été cliqué.
    }


    // Méthode qui vérifie si un bateau déjà placé a été cliqué, lors de la phase 1.
    public boolean bateauPlaceClique(int x, int y) {
        return this.controller.getJoueurCourant().getChampBataille().getCase(x, y).bateauHere() != null; // Retourne true si un bateau est sur la case de coordonnées (x, y).
    }
}