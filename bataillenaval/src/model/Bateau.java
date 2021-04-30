package model;
import java.util.ArrayList;

public class Bateau{
  private int taille;
  private Integer x;
  private Integer y;
  private boolean coule;
  private ArrayList<Case> toutBateau;

  public Bateau(int taille){
    this.taille = taille;
    this.x = null;
    this.y = null;
    this.coule = false;
    this.toutBateau = new ArrayList<>();
  }
/*
  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getDirection() {
    if(this.toutBateau.get(1).getX() == this.x +1) {
      return 0;
    }
    else {
      return 1;
    }
  }
*/
  public boolean getCoule(){
    return this.coule;
  }
  public int getTaille(){
    return this.taille;
  }
  public void addToBateau(Case casee){
    toutBateau.add(casee);
  }
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
