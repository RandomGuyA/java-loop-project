package Model;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage img;
    private boolean isEmpty = true;

    public Tile(){

    }

    public void draw(Graphics2D g2d, int x, int y) {
        //System.out.println("("+x+","+y+")");
        g2d.drawImage(img, x, y, null);

    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }


}
