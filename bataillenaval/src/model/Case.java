package model;

public class Case{
  private int positionX;
  private int positionY;
  //private boolean touche;
  private Bateau bateau;
  private Obus obus;

  public Case(int x, int y){
    this.positionX = x;
    this.positionY = y;
    this.bateau = null;
    this.obus = null;
  }
  public boolean getToucher(){
    if(this.obus != null){
      return true;
    }
    return false;
  }
  public int getX(){
    return this.positionX;
  }
  public int getY(){
    return this.positionY;
  }
  public Bateau bateauHere(){
    return this.bateau;
  }
  public void annulerBateau() {
    this.bateau = null;
  }
  public void placer(Bateau bateau){
    this.bateau = bateau;
    bateau.addToBateau(this);
  }
  public void viser(Obus obus){
    this.obus = obus;
    if(this.bateau != null){
      System.out.println("Un bateau a été touché");
      this.bateau.majCoule();
    }
  }
}
