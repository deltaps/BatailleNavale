package model;
import java.util.ArrayList;

// La classe Bateau permet de définir un bateau sur un plateau de jeu de bataille navale.
public class Bateau{
  // Cette classe contient donc une position x, y, une taille, un boolean permettant de savoir si le bateau est coulé ou non,
  // et une liste comprenant toutes les Case que contient le bateau.
  private int taille;
  private Integer x;
  private Integer y;
  private boolean coule;
  private ArrayList<Case> toutBateau;

  // Le constructeur prend en argument une taille, et va initialiser toutes les autres variables à null.
  public Bateau(int taille){
    this.taille = taille;
    this.x = null;
    this.y = null;
    this.coule = false;
    this.toutBateau = new ArrayList<>();
  }
  // Accesseurs --------------------------------------
  public boolean getCoule(){
    return this.coule;
  }
  public int getTaille(){
    return this.taille;
  }
  // ------------------------------------------------
  // La méthode addToBateau permet d'ajouter une case à la liste de cases du bateau.
  public void addToBateau(Case casee){
    toutBateau.add(casee);
  }
  // La méthode majCoule permet de vérifier si le bateau est coulé, donc si toutes les cases du bateau possèdent un Obus.
  // Auquel cas notre variable coule vas être placé à true.
  public void majCoule(){
    boolean teste = true;
    for(Case casec : this.toutBateau){
      if(!(casec.getToucher())){
        teste = false;
      }
    }
    if(teste){
      System.out.println("Un bateau a été coulé");
      this.coule = true;
    }
  }
}
