package vue;

import javax.swing.*;
import java.awt.*;

import controller.Controller;

// Classe de la fenêtre principale, reliant ainsi toutes les classes du package vue.
// Les autres classes elles-mêmes sont explicitées dans leur classe à elles. Cependant ce package ne sert que pour l'affichage.
public class Vue extends JFrame {

	private final Controller controller;

	public Vue(Controller controller) {
		this.controller = controller;

		this.afficheFenetre();
		this.setLocationRelativeTo(null); // Permet de placer la fenêtre au centre de l'écran.
		afficheMessage("C'est au tour du joueur 1 de placer ses bateaux."); // Affiche le message après la création de la fenêtre
	}

	// Fonction qui permet d'afficher tous les composants qui sont dans le package vue sur la fenêtre principale. Permet aussi de régler certains paramètres de la fenêtre.
	// Cette fonction est aussi très important, notamment pour mettre à jour l'état de la fenêtre, elle est très utilisée dans controller.Controller.
	public void afficheFenetre() {

		this.setTitle("Bataille Navale"); // Met un titre à la fenêtre
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Permet de fermer la fenêtre lorsque l'on clique sur la croix en haut à droite.
		this.setPreferredSize(new Dimension(930, 615)); // Taille de la fenêtre de 930 de largeur et 615 de hauteur.


		JPanel contentPane = new JPanel(); // JPanel qui va contenir tout le contenu de la fenêtre, que nous appellerons le panneau principal.
		contentPane.setLayout(null); // Permet de définir comment vont se placer les composants. Ici "null" veut dire qu'il n'y a pas de disposition, tous les composants vont donc se placer en fonction de leurs coordonnées attitrées (fonction setLocation(x,y))
		contentPane.setBackground(Color.DARK_GRAY); // Permet de mettre la couleur d'arrière plan gris foncé

		VueJoueurCourant vueJoueurCourant = new VueJoueurCourant(this.controller.getJoueurCourant().getChampBataille());
		contentPane.add(vueJoueurCourant); // Ajoute le composant souhaité dans le panneau principal, sa taille et situation sont définies dans sa classe. Cette méthode add() est appelée plusieurs fois.

		// En fonction de la phase (définie dans controller.Controller), l'affichage est différent : On affiche vuePlacer ainsi que le bouton permettant de tourner les bateaux de plus par rapport à la phase 2.
		if(this.controller.getPhase() == 1) {
			VuePlacer vuePlacer = new VuePlacer(this.controller);
			contentPane.add(vuePlacer);

			BoutonRotation boutonRotation = new BoutonRotation(this.controller); // Place le bouton "Rotation" pour faire en sorte qu'il prenne la partie inférieure gauche de l'écran.
			contentPane.add(boutonRotation);

			BoutonSuivant boutonSuivant = new BoutonSuivant(this.controller, 457, 456); // Place le bouton "Suivant" pour faire en sorte qu'il prenne la partie inférieure droite de l'écran, et défini sa largeur à la moitié de l'écran.
			contentPane.add(boutonSuivant);

			contentPane.add(buildTexte("Phase de : Placement", 464)); // Affiche la phase en bas de vuePlacer.
		}

		// On affiche vueTirer à la place de vuePlacer à la 2ème phase.
		if(this.controller.getPhase() == 2) {
			VueTirer vueTirer = new VueTirer(this.controller.getJoueurAdverse().getChampBataille());
			contentPane.add(vueTirer);

			BoutonSuivant boutonSuivant = new BoutonSuivant(this.controller,914, 0); // Place le bouton "Suivant" pour faire en sorte qu'il prenne toute la largeur de l'écran.
			contentPane.add(boutonSuivant);

			contentPane.add(buildTexte("Phase de : Tir", 464)); // Affiche la phase en bas de vuePlacer.
		}

		contentPane.add(buildTexte("Tour de : " + this.controller.getJoueurCourantString(), 0)); // Affiche le joueur courant en bas de vueJouerCourant.

		this.setContentPane(contentPane); // Ajoute le panneau principal dans cette fenêtre JFrame.

		this.pack();

		this.setVisible(true);
	}


	// Méthode permettant de construire un JLabel, permettant d'afficher du texte. Celle-ci est ici utilisée pour afficher le joueur courant et le phase actuelle.
	private JLabel buildTexte(String texte, int x) {
		JLabel label = new JLabel(texte, SwingConstants.CENTER); // SwingConstants.CENTER permet de centrer le texte par rapport à la taille de ce JLabel.
		label.setSize(new Dimension(450, 15));
		label.setLocation(x, 450);
		label.setForeground(Color.WHITE); // Permet de mettre la couleur de texte en blanc.

		return label;
	}


	// Méthode qui affiche un message dans une boîte de dialogue, sur laquelle on doit appuyer sur "OK" (ou appuyer sur la touche "Entrer" du clavier) pour continuer d'exécuter la suite du programme.
	// Est utilisée pour certains changement de joueur courant et pour les changements de phase, ainsi que pour annoncer qu'un joueur a gagné.
	public void afficheMessage(String message) {
		JOptionPane.showMessageDialog(null,message);
	}

	// Méthode qui affiche un message de confirmation dans une boîte de dialogue. Ici nous avons le choix entre "OK" et "Annuler" (ou "Cancel").
	// Est utilisée pour confirmer si l'on veut annuler le placement d'un bateau.
	public int messageConfirmation(String message) {
		return JOptionPane.showConfirmDialog(null, message, "Sélectionnez une option.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
		// Le troisième paramètre est affiché en haut de la boîte de dialogue, en tant que titre. "JOptionPane.OK_CANCEL_OPTION" Permet de définir le fait que l'on aie 2 choix. "JOptionPane.ERROR_MESSAGE" est l'icone qui s'affiche à coté du message.
		// Renvoie 0 si on appuie sur "OK", 2 si on appuie sur "Annuler".
	}
}
