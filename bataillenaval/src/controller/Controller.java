package controller;

import model.Joueur;
import model.ChampBataille;
import model.Bateau;
import vue.Vue;

import java.util.ArrayList;

// Classe qui permet de controler tout le jeu.
// Certaines variables sont détaillées en commentaire dans leur accesseur respectif (exemple : this.phase est détaillée avec getPhase()).
// Les lignes avec "this.vue.afficheFenetre()" actualisent la fenêtre affichée.
public class Controller {

    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurCourant;

    private int phase;

    private ArrayList<Integer> bateauAPlacer;
    private final int nbBateauAPlacerInitial;
    private int rotation;

    private boolean tirEffectue;

    private final Vue vue;

    public Controller(Joueur joueur1, Joueur joueur2) {
        this.joueur1 = joueur1; // Variable stockant les données du joueur 1.
        this.joueur2 = joueur2; // Variable stockant les données du joueur 2.

        this.joueurCourant = this.joueur1;

        this.phase = 1;

        this.bateauAPlacer = new ArrayList<>();
        setBateauAPlacer();
        this.nbBateauAPlacerInitial = this.bateauAPlacer.size();
        this.rotation = 0;

        // "this.tir" est true lorsqu'un joueur vient de faire un tir, false sinon. Elle permet au joueur courant de ne pas pouvoir tirer 2 fois dans le même tour. ELle est utilisée dans la méthode tir().
        this.tirEffectue = false;

        this.vue = new Vue(this); // Affiche la fenêtre du jeu.

        EcouteurSouris souris = new EcouteurSouris(this); // Permet d'initialiser l'écouteur (listener) de la souris.
        vue.addMouseListener(souris); // Ajoute l'écouteur à la vue, permettant d'avoir une interface graphique sur laquelle on peut cliquer dessus.
    }


    // Accesseur retournant le joueur courant, le joueur auquel c'est à son tour de faire une action.
    // Le joueur courant est initialement "this.joueur1".
    public Joueur getJoueurCourant() {
        return this.joueurCourant;
    }

    // Accesseur retournant le joueur courant mais avec le type "String".
    // Est utilisé pour le JLabel dans la classe vue.Vue, permettant d'afficher le joueur courant.
    public String getJoueurCourantString() {
        if(getJoueurCourant() == this.joueur1) {
            return "Joueur 1";
        }
        else {
            return "Joueur 2";
        }
    }
    // Accesseur retournant le joueur adverse, c'est à dire celui qui n'est pas le joueur courant.
    public Joueur getJoueurAdverse() {
        if (this.joueurCourant == this.joueur1) {
            return this.joueur2;
        }
        else {
            return this.joueur1;
        }
    }

    // Fonction pour changer le joueur courant.
    public void changerJoueurCourant() {
        if(this.joueurCourant == this .joueur1) {
            this.joueurCourant = this.joueur2;
        }
        else {
            this.joueurCourant = this.joueur1;
        }
    }


    // Accesseur permettant d'avoir la phase actuelle :
    /*
    Si this.phase = 1 : On est en phase 1, c'est-à-dire la phase de placement. Les joueurs peuvent placer leurs bateaux dans cette phase.
    Si this.phase = 2 : On est en phase 2, c'est-à-dire la phase de tir. Les joueurs peuvent tirer les uns sur les autres dans celle-ci.
    Si this.phase = 3 : On est en phase 3, accessible si un des deux joueurs a réussi à couler tous les bateaux de l'autre.
     */
    // Les phases se succèdent, c'est à dire qu'on ne peut pas passer de la phase 1 à la phase 3, de la phase 2 à la phase 1, ...
    // La phase est changée dans la fonction "suivant()" qui est gérée par le bouton "Suivant".
    public int getPhase() {
        return this.phase;
    }


    // Cette méthode renvoie une liste de bateaux à placer, elle est donc utile uniquement pour la phase 1, la phase de placement.
    // Elle fonctionne de la manière suivante :
    /*
    - C'est une ArrayList d'entier. Les entiers correspondent à la taille d'un bateau (par conséquent ne doit pas dépasser 10 car le plateau est de taille 10x10).
    - Son affichage est géré par la Classe vue.VuePlacer, en fonction de la taille de chaque bateau.
    - Pour sélectionner un bateau (pour pouvoir le placer ensuite) via l'interface graphique, on peut cliquer sur l'intégralité du bateau grâce à sa taille.
     */
    // Ainsi tout est géré grâce à la taille du bateau, même pour placer le bateau voulu sur le model. On peut donc modifier cette liste et le jeu fonctionnera avec la taille et le nombre de bateaux qu'on lui donnera.
    public ArrayList<Integer> getBateauAPlacer() {
        return this.bateauAPlacer;
    }

    // Cette méthode permet d'ajouter les bateaux par défaut dans les bateaux à placer : un bateau de taille 5, un autre de 4, deux de 3 et un dernier de 2.
    // Tant que cette partie n'est pas vide, on peut très bien mettre une partie des bateaux en commentaire, en ne laissant que le 5 par exemple, pour accélérer le déroulement de la partie.
    public void setBateauAPlacer() {
        ajouterBateauAPlacer(5);
        ajouterBateauAPlacer(4);
        ajouterBateauAPlacer(3);
        ajouterBateauAPlacer(3);
        ajouterBateauAPlacer(2);
    }

    // Méthode qui permet d'ajouter des bateaux à placer, à condition que la taille de celui-ci ne dépasse pas 10.
    // On peut ajouter autant de bateau que l'on veut, mais on doit pouvoir placer tous les bateaux, ainsi la taille totale ne doit pas dépasser 100 qui est le nombre total de case. Nous avons donc mis que la taille totale ne doit pas dépasser 50 pour pouvoir placer tous les bateaux, et ne pas avoir de configuration impossible.
    public void ajouterBateauAPlacer(int taille) {
        int sommeDeTaille = taille;
        for (int bateau : this.bateauAPlacer) { // Calcul de la somme totale de la taille de tous les bateaux à placer.
            sommeDeTaille += bateau;
        }

        if(taille <= 10 && sommeDeTaille <= 50) { // La taille du bateau à placer ne doit pas dépasser 10, et la somme totale ne doit pas dépasser 50 pour éviter les configurations impossibles.
            this.bateauAPlacer.add(taille);
        }
    }


    // Accesseur retournant la rotation actuelle, this.rotation.
    // C'est l'équivalent de la variable "direction" du model : c'est une variable binaire qui défini dans quel sens sont les bateau à placer (de la liste this.bateauAPlacer).
    /*
    Si this.rotation = 0 : alors les bateaux seront affichés verticalement.
    Si this.rotation = 1 : alors les bateaux seront affichés horizontalement.
    Cela tourne donc uniquement les bateaux de 90°.
     */
    // Cela n'a pas d'effet sur les bateaux qui sont déjà placés, leur sens de rotation a déjà été défini avant d'être placé.
    public int getRotation() {
        return this.rotation;
    }

    // Permet de changer la rotation, donc de tourner de 90° tous les bateaux à placer.
    // Est activée lorsque l'on appuie sur le bouton "Rotation" lors de la phase 1.
    public void changeRotation() {
        if(this.rotation == 0) {
            this.rotation = 1;
        }
        else {
            this.rotation = 0;
        }
        this.vue.afficheFenetre(); // Actualise la fenêtre.
    }


    // Fonction permettant de placer le bateau, à partir de coordonnées et d'un numéro de bateau :
    /*
    x et y correspondent aux coordonnées de la case sur laquelle placer le bateau.
    Le placement se fait en fonction de la tête du bateau, c'est-à-dire l'avant du bateau. Si le bateau est tourné verticalement, sa tête est en haut et s'il est tourné horizontalement, sa tête est à gauche.
    La variable "numeroBateau" correspond à l'indice dans la liste this.bateauAPlacer, définie dans la classe EcouteurSouris.
    Si dans la liste, le bateau à placer est le bateau d'indice 1, alors ça placera ce bateau.
     */
    public void placement(int x, int y, int numeroBateau) {
        //this.joueurCourant.placer(new Bateau(this.bateauAPlacer.get(numeroBateau)), x, y, this.rotation); // Place le bateau aux coordonnées (x,y), dans la direction this.rotation. Sa taille est définie par la valeur du tableau "this.bateauAPlacer" d'indice "numero bateau".
        this.joueurCourant.placer(this.bateauAPlacer.get(numeroBateau), x, y, this.rotation); // Place le bateau aux coordonnées (x,y), dans la direction this.rotation. Sa taille est définie par la valeur du tableau "this.bateauAPlacer" d'indice "numero bateau".
        if(this.joueurCourant.getChampBataille().getCase(x,y).bateauHere() != null) {
            this.bateauAPlacer.remove(numeroBateau); // Le bateau a bien été placé, on enlève donc le bateau à placer de la liste car il n'est plus à placer.
            this.vue.afficheFenetre();
        }
        else {
            this.vue.afficheMessage("Placement impossible.");
        }
    }

    // Fonction utilisée dans la classe EcouteurSouris.
    // Retourne true si le placement est possible, et false si celui-ci est impossible.
    public boolean placementPossible(int x, int y, int numeroBateau) {
        int taille = this.bateauAPlacer.get(numeroBateau);
        boolean placementPossible = false;

        if(this.rotation == 0) { // Si le bateau est vertical.
            for (int yCase = y; yCase != y + taille; yCase++) { // Boucle qui vérifie, on fonction de la direction du bateau et de sa taille si le placement est possible.
                if(yCase <= 9) { // Le placement n'est pas possible si une case du bateau dépasse du plateau.
                    if(this.joueurCourant.getChampBataille().getCase(x, yCase).bateauHere() == null) { // Le placement n'est pas possible si une case du bateau superpose un autre bateau.
                        placementPossible = true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
        }
        else { // Si le bateau est horizontal (mêmes vérifications que si le bateau est vertical, avec quelques changement par rapport aux coordonnées).
            for (int xCase = x; xCase != x + taille; xCase++) {
                if(xCase <= 9) {
                    if(this.joueurCourant.getChampBataille().getCase(xCase, y).bateauHere() == null) {
                        placementPossible = true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
        }
        return placementPossible;
    }

    // Fonction qui permet d'annuler le placement du bateau de coordonnées (x,y) lorsque l'on clique dessus.
    // Est utilisée dans EcouteurSouris.
    public void annulerPlacement(int x, int y) {
        if(this.vue.messageConfirmation("Voulez-vous annuler le placement de ce bateau ?") == 0) { // Affiche un message de confirmation : plus de détails dans vue.Vue.
            //ChampBataille modelJoueurCourant = this.joueurCourant.getChampBataille();
            Bateau bateauClique = this.joueurCourant.getChampBataille().getCase(x, y).bateauHere(); // Variable contenant le bateau cliqué qui est sur la case (x,y).

            this.joueurCourant.getChampBataille().annulerPlacementBateau(x, y, bateauClique);
            /*
            // Cette partie du code pourrait faire partie du model.
            this.joueurCourant.getChampBataille().getBateaux().remove(bateauClique); // Permet d'enlever le bateau de la liste de bateau.

            modelJoueurCourant.getCase(x, y).annulerBateau(); // Permet d'enlever la bateau sur la case de coordonnées (x,y).
            for (int i = 1; i < bateauClique.getTaille(); i++) { // Boucle qui vérifie toutes les cases alentour de celle cliquée, si elles contiennent le bateau cliqué.
                if (x + i <= 9) { // Si on ne dépasse pas les bords du terrain.
                    if (modelJoueurCourant.getCase(x + i, y).bateauHere() == bateauClique) {
                        modelJoueurCourant.getCase(x + i, y).annulerBateau();
                    }
                }
                if (x - i >= 0) {
                    if (modelJoueurCourant.getCase(x - i, y).bateauHere() == bateauClique) {
                        modelJoueurCourant.getCase(x - i, y).annulerBateau();
                    }
                }
                if (y + i <= 9) {
                    if (modelJoueurCourant.getCase(x, y + i).bateauHere() == bateauClique) {
                        modelJoueurCourant.getCase(x, y + i).annulerBateau();
                    }
                }
                if (y - i >= 0) {
                    if (modelJoueurCourant.getCase(x, y - i).bateauHere() == bateauClique) {
                        modelJoueurCourant.getCase(x, y - i).annulerBateau();
                    }
                }
            }

             */

            this.bateauAPlacer.add(bateauClique.getTaille()); // Remet un bateau à placer de la même tailler que celui enlevé dans les bateaux à placer.
            this.vue.afficheFenetre();
        }
    }


    // Fonction qui permet au joueur courant de tirer sur le plateau du joueur adverse.
    // Tire sur la case de coordonnées (x,y) si la case n'a pas reçu de tir et si le joueur n'a pas déjà tiré pendant son tour (this.tir == false).
    // Est utilisée dans la clases EcouteurSouris.
    public void tir(int x, int y) {
        if(!getJoueurAdverse().getChampBataille().getCase(x,y).getToucher() && !this.tirEffectue) {
            this.joueurCourant.viser(x, y, getJoueurAdverse().getChampBataille());
            this.tirEffectue = true; // Le joueur vient de tirer.
            this.vue.afficheFenetre();
        }

        else {
            this.vue.afficheMessage("Tir Impossible");
        }
    }


    // Fonction qui va être déclenchée par le bouton "Suivant".
    // Elle dépend de la phase actuelle, this.phase. C'est uniquement celle-ci qui peut changer le joueur courant ou la phase actuelle.
    public void suivant() {
        if(this.phase == 1) { // Si on est en phase de placement.
            if(this.bateauAPlacer.isEmpty()) { // true s'il n'y a plus de bateau à placer.
                changerJoueurCourant(); // Change le joueur actuel.

                if(this.joueur2.getChampBataille().getBateaux().size() == this.nbBateauAPlacerInitial) { // true si le joueur 2 a le bon nombre de bateaux, 5 par défaut.
                    this.phase = 2; // Changement de phase.
                    this.vue.afficheMessage("Le joueur 1 doit maintenant viser une case, puis le joueur 2 et ainsi de suite.");
                }
                else {
                    setBateauAPlacer(); // Rempli de nouveau this.bateauAPlacer pour le joueur 2 (le joueur courant a changé mais pas la phase).
                    this.vue.afficheMessage("C'est au tour du joueur 2 de placer ses bateaux.");
                }
                this.vue.afficheFenetre();
            }
        }

        else if(this.phase == 2) { // Si on est en phase de tir, déclenchée par la fin de placement des bateaux du joueur 2.
            if(this.tirEffectue) { // true si le joueur courant a déjà tiré.
                if(getJoueurAdverse().getChampBataille().isOver()) { // Vérifie si tous les bateaux du joueur 2 sont coulés.
                    if(this.joueurCourant == this.joueur1) {
                        System.out.println("Le joueur 1 a gagné !");
                        this.vue.afficheMessage("Le joueur 1 a remporté la partie !\nCliquez sur \"Suivant\" pour rejouer.");
                    }
                    else {
                        System.out.println("Le joueur 2 a gagné !");
                        this.vue.afficheMessage("Le joueur 2 a remporté la partie !\nCliquez sur \"Suivant\" pour rejouer.");
                    }
                    this.phase = 3;
                }
                else {
                    this.tirEffectue = false;
                    changerJoueurCourant();

                    this.vue.afficheFenetre();
                }
            }
        }

        else { // this.phase == 3 : Si on est à la troisième phase, déclenchée lorsqu'un joueur a gagné.
            rejouer();
        }
    }


    // Méthode qui va permettre de rejouer une partie lorsque une partie vient de se finir, en réinitialisant toutes les variables.
    // Est déclenchée dans la méthode "suivant()", dans la phase 3, c'est à dire quand l'un des deux joueurs a remporté la partie.
    public void rejouer() {
        this.joueur1 = new Joueur(new ChampBataille());
        this.joueur2 = new Joueur(new ChampBataille());

        this.joueurCourant = this.joueur1;

        this.phase = 1;

        this.bateauAPlacer = new ArrayList<>();
        setBateauAPlacer();
        this.rotation = 0;

        this.tirEffectue = false;

        this.vue.afficheFenetre();
        this.vue.afficheMessage("C'est au tour du joueur 1 de placer ses bateaux.");
    }
}
