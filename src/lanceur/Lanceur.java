package lanceur;

import controller.Controller;
import model.ChampBataille;
import model.Joueur;

public class Lanceur {
    public static void main(String[] args) {
        Joueur joueur1 = new Joueur(new ChampBataille());
        Joueur joueur2 = new Joueur(new ChampBataille());

        Controller controller = new Controller(joueur1, joueur2);
    }
}
