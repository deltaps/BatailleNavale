package vue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Classe qui permet de stocker les images dans une variable, de type BufferedImage.
public class Images {

    public static BufferedImage imageCase;
    public static BufferedImage imageCasePlacement;
    public static BufferedImage bateau;
    public static BufferedImage tirTouche;
    public static BufferedImage tirRate;

    public static String DELIMITEUR_LINUX = "/";
    public static String DELIMITEUR_WINDOWS = "\\";
    public static String delimiteur;

    static {
        if (System.getProperty("os.name").equals("Windows")) {
            delimiteur = DELIMITEUR_WINDOWS;
        } else {
            delimiteur = DELIMITEUR_LINUX;
        }
        try {
            // Lis l'image (dans l'exemple de la ligne ci-dessous l'image de la case) dans le ficher images.
            // La variable delimiteur est faite pour pouvoir charger les images dans le fichier, pour les utilisateurs de Windows ou de Linux.
            imageCase = ImageIO.read(new File("./images" + delimiteur + "imageCase.png"));
            tirRate = ImageIO.read(new File("./images" + delimiteur + "tirRate.png"));
            bateau = ImageIO.read(new File("./images" + delimiteur + "bateau.png"));
            tirTouche = ImageIO.read(new File("./images" + delimiteur + "tirTouche.png"));
            imageCasePlacement = ImageIO.read(new File("./images" + delimiteur + "imageCasePlacement.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}