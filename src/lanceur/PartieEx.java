package lanceur;
import model.Joueur;
import model.ChampBataille;
import model.Bateau;

public class PartieEx{
  public static void main(String[] args){

    Joueur joueur1 = new Joueur(new ChampBataille());
    Joueur joueur2 = new Joueur(new ChampBataille());


    joueur1.placer(3,2,6,0);
    joueur1.placer(4,7,1,0);
    joueur1.placer(5,0,0,0);
    joueur1.placer(3,3,9,1);
    joueur1.placer(2,3,4,1);

    joueur2.placer(3,1,5,0);
    joueur2.placer(4,2,3,1);
    joueur2.placer(5,7,2,0);
    joueur2.placer(3,2,6,1);
    joueur2.placer(2,4,1,1);

    System.out.println("Terrain du joueur 1 -----------------------------");
    for(int l = 0; l < 10; l++){
      for(int c = 0; c < 10; c++){
        if(joueur1.getChampBataille().getCase(l,c).bateauHere() != null){
          System.out.print(" B ");
        }
        else{
          System.out.print(" C ");
        }
      }
      System.out.println("");
    }
    System.out.println("-----------------------------------------");
    System.out.println("Terrain du joueur 2 -----------------------------");
    for(int l = 0; l < 10; l++){
      for(int c = 0; c < 10; c++){
        if(joueur2.getChampBataille().getCase(l,c).bateauHere() != null){
          System.out.print(" B ");
        }
        else{
          System.out.print(" C ");
        }
      }
      System.out.println("");
    }
    System.out.println("-----------------------------------------");


    joueur1.viser(7,2,joueur2.getChampBataille());
    joueur1.viser(4,1,joueur2.getChampBataille());
    joueur1.viser(5,1,joueur2.getChampBataille());

  }

}
