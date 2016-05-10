package Model;

import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Model {

    private int TILE_WIDTH = 32;
    private int TILE_HEIGHT = 32;
    private int OFFSET_X = 96;
    private int OFFSET_Y = 96;
    private int GRID_WIDTH = 19;
    private int GRID_HEIGHT = 9;

    private String[] shapes;
    private ShapeFactory shapeFactory;
    private Tileset tileset;
    private Shape currentShape;
    private Background background;
    private Grid grid;


    public Model(){

        populateShapeStringArray();
        tileset = new Tileset("shapes.png", TILE_WIDTH, TILE_HEIGHT,3,3,0,0);
        grid = new Grid(TILE_WIDTH,TILE_HEIGHT,OFFSET_X,OFFSET_Y,GRID_WIDTH,GRID_HEIGHT);
        shapeFactory = new ShapeFactory();
        BufferedImage backgroundTile = tileset.getSingleSprite(1,1);
        background = new Background(GRID_WIDTH, GRID_HEIGHT, OFFSET_X, OFFSET_Y, tileset.getTileWidth(), tileset.getTileHeight(), backgroundTile);

        nextShape();
    }

    public void nextShape(){
        currentShape = shapeFactory.getShape(shapes[randInt(0,shapes.length-1)], tileset);
    }

    private void populateShapeStringArray(){
        shapes =  new String[5];
        shapes[0] = "LShape";
        shapes[1] = "LargeCorner";
        shapes[2] = "smallCorner";
        shapes[3] = "smallStraight";
        shapes[4] = "LargeStraight";
    }


    public Tileset getTileset() {
        return tileset;
    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    public Background getBackground() {
        return background;
    }

    public int getTILE_WIDTH() {
        return TILE_WIDTH;
    }

    public int getTILE_HEIGHT() {
        return TILE_HEIGHT;
    }

    public int getOFFSET_X() {
        return OFFSET_X;
    }

    public int getOFFSET_Y() {
        return OFFSET_Y;
    }

    public int getGRID_WIDTH() {
        return GRID_WIDTH;
    }

    public int getGRID_HEIGHT() {
        return GRID_HEIGHT;
    }

    public Grid getGrid() {
        return grid;
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
