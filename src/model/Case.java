package model;

// La classe Case permet de définir une Case d'un plateau de jeu de bataille navale.
public class Case{
  // Cette classe contient donc une position x et y, deux réceptacles pour un bateau et un Obus.
  private int positionX;
  private int positionY;
  private Bateau bateau;
  private Obus obus;

  // Le constructeur prend en argument la position de la Case, et place nos récéptacles à null.
  public Case(int x, int y){
    this.positionX = x;
    this.positionY = y;
    this.bateau = null;
    this.obus = null;
  }
  // Accesseurs -------------------------------------
  // Notre Accesseurs getToucher permet de savoir si la case est touchée par un obus. (Donc si elle contient un Obus)
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
  // ------------------------------------------------
  // La méthode bateauHere retourne le bateau présent sur la case (null s'il n'y en a pas)
  public Bateau bateauHere(){
    return this.bateau;
  }
  // La méthode annulerBateau permet de placer le réceptacle du bateau à null.
  public void annulerBateau() {
    this.bateau = null;
  }
  // La méthode placer permet, en donnant un bateau en argument, de placer ce bateau sur la case, et d'ajouter à ce bateau cette même Case.
  public void placer(Bateau bateau){
    this.bateau = bateau;
    bateau.addToBateau(this);
  }
  // La méthode visée permet de placer un Obus sur la case, et si un bateau est présent, de mettre à jour le bateau.
  public void viser(Obus obus){
    this.obus = obus;
    if(this.bateau != null){
      System.out.println("Un bateau a été touché");
      this.bateau.majCoule();
    }
  }
}
