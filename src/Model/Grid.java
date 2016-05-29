package Model;

import Model.Loops.Loop;
import Model.Loops.LoopDetection;
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
    private Grid theGrid;
    private int lastLoopId;
    private LoopDetection loopDetection;
    private ArrayList<Loop> potentialLoops;

    private Tile[][] tiles;

    public Grid(int tileWidth, int tileHeight, int offsetX, int offsetY, int gridWidth, int gridHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        theGrid=this;
        lastLoopId=1;
        tiles = new Tile[gridWidth][gridHeight];
        potentialLoops = new ArrayList<>();
        loopDetection = new LoopDetection();
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
        ArrayList<Tile> shapeTiles = new ArrayList<>();

        if(tileIsEmpty(position)){

            State[] states = shape.getStates();
            State state = states[shape.getCurrentState()];

            ArrayList<Sprite> sprites = state.getSprites();


            for(int a=0; a<sprites.size(); a++){

                Sprite sprite = sprites.get(a);

                int x = position.getX() + sprite.getCoordinates().getX();
                int y = position.getY() + sprite.getCoordinates().getY();

                //System.out.println("("+x+","+y+")");
                Tile tile = tiles[x][y];

                tile.setImg(sprite.getImage());
                tile.setEmpty(false);
                tile.setSides(sprite.getSides());
                tile.setArrayPosition(new Coordinates(x,y));

                loopDetection.loopDetector(theGrid, tile);

            }
        }
        printLoopsOut();
    }

    private void printLoopsOut() {

        for(int a=0; a<potentialLoops.size(); a++){
            Loop loop = potentialLoops.get(a);
            System.out.println("Loop Id: "+loop.getId());
            System.out.println("Loop Tile Count: "+loop.getLoopTiles().size());
        }
    }

    public void createNewLoop(Tile tile) {
        tile.setArrayId(lastLoopId);
        System.out.println("loop id "+ lastLoopId);

        potentialLoops.add(new Loop(lastLoopId, tile));
        lastLoopId++;
    }

    public Loop getLoopById(int id){

        Loop loop = null;
        for(int a=0; a<potentialLoops.size();a++){
            if(potentialLoops.get(a).getId()==id){
                loop = potentialLoops.get(a);
            }
        }
        return loop;
    }

    public void mergeLoopArrays(int arrayId, int arrayId1) {

        ArrayList<Tile> loopTiles = new ArrayList<>();
        System.out.println("merging loops id: "+arrayId+" and id: "+arrayId1);
        System.out.println("1st array is "+getLoopById(arrayId).getLoopTiles().size()+" long");
        System.out.println("2nd array is "+getLoopById(arrayId1).getLoopTiles().size()+" long");

        loopTiles = addTilesFromLoop(loopTiles, getLoopById(arrayId), lastLoopId);
        loopTiles = addTilesFromLoop(loopTiles, getLoopById(arrayId1), lastLoopId);

        potentialLoops.add(new Loop(lastLoopId, loopTiles));

        lastLoopId++;
    }

    private ArrayList<Tile> addTilesFromLoop(ArrayList<Tile> loopTiles , Loop loop, int loopId) {

        ArrayList<Tile> theTiles = loop.getLoopTiles();

        for(int a=0; a<theTiles.size();a++){
            Tile tile = theTiles.get(a);
            tile.setArrayId(loopId);
            loopTiles.add(theTiles.get(a));
        }

        return loopTiles;
    }

    private boolean tileIsEmpty(Coordinates position) {
        return tiles[position.getX()][position.getY()].isEmpty();
    }

    public boolean allowedToDrop(Shape shape) {

        Coordinates position = calculatePositionOnGrid(shape);

        State[] states = shape.getStates();
        State state = states[shape.getCurrentState()];

        ArrayList<Sprite> sprites = state.getSprites();

       // System.out.println("position check: ");

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

    public void addTileToGrid(Tile tile){

        tiles[tile.getArrayPosition().getX()][tile.getArrayPosition().getY()] = tile;
    }

    public int getNumberOfActiveTiles (){

        int count = 0;

        for(int a=0; a<gridWidth; a++){
            for(int b=0; b<gridHeight; b++){
                if(!tiles[a][b].isEmpty()){
                    count++;
                }
            }
        }
        return count;
    }

    private Coordinates calculatePositionOnGrid(Shape shape){

        int x = calculateTilePosition(shape.getPosition().getX(), "x");
        int y = calculateTilePosition(shape.getPosition().getY(), "y");
        //System.out.println("("+x+","+y+")");

        return new Coordinates(x,y);
    }

    private int calculateTilePosition(int pos, String axis) {

        int offset = (axis.equals("x"))?offsetX:offsetY;
        int size = (axis.equals("x"))?tileWidth:tileHeight;
        return (pos - offset)/size;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public ArrayList<Loop> getPotentialLoops() {
        return potentialLoops;
    }


}
