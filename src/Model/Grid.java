package Model;

import Model.Shapes.Shape;
import Model.Shapes.State;

import java.awt.*;
import java.util.ArrayList;

public class Grid {

    private int tileWidth = 32;
    private int tileHeight = 32;
    private int offsetX = 96;
    private int offsetY = 96;
    private int gridWidth = 19;
    private int gridHeight = 9;

    private Tile[][] tiles;

    public Grid(int tileWidth, int tileHeight, int offsetX, int offsetY, int gridWidth, int gridHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        tiles = new Tile[gridWidth][gridHeight];

        addBlankTiles();

    }

    public void draw(Graphics2D g2d){

        for(int a=0; a<gridWidth; a++){
            for(int b=0; b<gridHeight; b++){

                Tile tile = tiles[a][b];

                if(!tile.isEmpty()) {
                    tile.draw(g2d, offsetX + (a * tileWidth), offsetY + (b * tileHeight));
                }
            }
        }
    }


    private void addBlankTiles() {

        for(int a=0; a<gridWidth; a++){
            for(int b=0; b<gridHeight; b++){

                tiles[a][b] = new Tile();
            }
        }
    }

    public void addShapeToGrid(Shape shape) {

        Coordinates position = calculatePositionOnGrid(shape);

        if(tileIsEmpty(position)){

            State[] states = shape.getStates();
            State state = states[shape.getCurrentState()];

            ArrayList<Sprite> sprites = state.getSprites();

            System.out.println("position check: ");

            for(int a=0; a<sprites.size(); a++){

                Sprite sprite = sprites.get(a);

                int x = position.getX()+sprite.getCoordinates().getX();
                int y = position.getY()+sprite.getCoordinates().getY();


                System.out.println("("+x+","+y+")");
                Tile tile = tiles[x][y];
                tile.setImg(sprite.getImage());
                tile.setEmpty(false);

            }
        }
    }

    private boolean tileIsEmpty(Coordinates position) {
        return tiles[position.getX()][position.getY()].isEmpty();
    }

    public boolean allowedToDrop(Shape shape) {

        Coordinates position = calculatePositionOnGrid(shape);

        State[] states = shape.getStates();
        State state = states[shape.getCurrentState()];

        ArrayList<Sprite> sprites = state.getSprites();

        System.out.println("position check: ");

        int count=0;

        for(int a=0; a<sprites.size(); a++){

            Sprite sprite = sprites.get(a);

            int x = position.getX()+sprite.getCoordinates().getX();
            int y = position.getY()+sprite.getCoordinates().getY();


            if(tiles[x][y].isEmpty()) {
                count++;
            }
        }
        if(count==sprites.size()) {
            return true;
        }

        return false;
    }

    private Coordinates calculatePositionOnGrid(Shape shape){

        int x = calculateTilePosition(shape.getPosition().getX(), "x");
        int y = calculateTilePosition(shape.getPosition().getY(), "y");
        System.out.println("("+x+","+y+")");


        return new Coordinates(x,y);
    }

    private int calculateTilePosition(int pos, String axis) {

        int offset = (axis.equals("x"))?offsetX:offsetY;
        int size = (axis.equals("x"))?tileWidth:tileHeight;
        return (pos - offset)/size;
    }


}
