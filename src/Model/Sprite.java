package Model;

import java.awt.image.BufferedImage;

public class Sprite {

    private BufferedImage image;
    private Coordinates coordinates;
    private Side[] sides;

    public Sprite(BufferedImage image, Coordinates coordinates, Side[] sides) {
        this.image = image;
        this.coordinates = coordinates;
        this.sides = sides;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public BufferedImage getImage() {
        return image;
    }
}
