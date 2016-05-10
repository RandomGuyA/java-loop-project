package Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tileset {

    private BufferedImage[][] sprites;
    private int tileCountX, tileCountY, tileWidth, tileHeight, marginX, marginY;
    private BufferedImage tileset;
    private String DIRECTORY = "Assets/";

    public Tileset(String fileName, int tileWidth, int tileHeight, int tileCountX, int tileCountY, int marginX, int marginY) {

        this.tileCountX = tileCountX;
        this.tileCountY = tileCountY;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.marginX = marginX;
        this.marginY = marginY;

        setupTileSheet(fileName);
    }

    private void setupTileSheet(String fileName) {
        tileset = loadImage(fileName);
        if(tileset!=null){
            sprites = splitTilesetIntoTiles(tileset);
        }
    }

    private BufferedImage loadImage(String fileName){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(DIRECTORY + fileName));
        } catch (IOException e) {
            System.out.println("failed to load asset");
        }
        return img;
    }

    private BufferedImage[][] splitTilesetIntoTiles(BufferedImage tileset) {

        BufferedImage[][] sprites = new BufferedImage[tileCountX][tileCountY];

        for (int i=0; i<tileCountX; i++){
            for (int j=0; j<tileCountY; j++){
                sprites[i][j] = tileset.getSubimage(i*(tileWidth+marginX), j*(tileHeight+marginY), tileWidth, tileHeight);
            }
        }
        return sprites;
    }

    public Side[] getSides(int x, int y){

        BufferedImage bufferedImages = sprites[x][y];


        return null;
    }

    public BufferedImage getSingleSprite(int x, int y){

        return sprites[x][y];
    }

    public BufferedImage[][] getSprites() {
        return sprites;
    }

    public void setSprites(BufferedImage[][] sprites) {
        this.sprites = sprites;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
}