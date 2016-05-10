package Model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {

    private int countX, countY, offsetX, offsetY, spriteWidth, spriteHeight;
    private BufferedImage image;

    public Background(int countX, int countY, int offsetX, int offsetY, int spriteWidth, int spriteHeight, BufferedImage image) {
        this.countX = countX;
        this.countY = countY;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.image = image;
    }

    public void draw(Graphics g){
        for(int a=0; a<countX; a++){
            for(int b=0; b<countY; b++){
                g.drawImage(image,(a*spriteWidth)+offsetX, (b*spriteHeight)+offsetY, null );
            }
        }
    }

    public int getCountX() {
        return countX;
    }

    public void setCountX(int countX) {
        this.countX = countX;
    }

    public int getCountY() {
        return countY;
    }

    public void setCountY(int countY) {
        this.countY = countY;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
