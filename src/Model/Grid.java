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

    private int lastLoopId;
    private ArrayList<Loop> potentialLoops;

    private Tile startTile;
    private ArrayList<Tile> evaluatedTiles;

    private Tile[][] tiles;

    public Grid(int tileWidth, int tileHeight, int offsetX, int offsetY, int gridWidth, int gridHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        lastLoopId=1;
        tiles = new Tile[gridWidth][gridHeight];
        potentialLoops = new ArrayList<>();
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

                int x = position.getX()+sprite.getCoordinates().getX();
                int y = position.getY()+sprite.getCoordinates().getY();

                //System.out.println("("+x+","+y+")");
                Tile tile = tiles[x][y];
                tile.setImg(sprite.getImage());
                tile.setEmpty(false);
                tile.setSides(sprite.getSides());
                tile.setArrayPosition(new Coordinates(x,y));
                shapeTiles.add(tile);
            }
        }
        loopDetection(shapeTiles);

        //checkforLoops();
    }

    private void loopDetection(ArrayList<Tile> shapeTiles) {

        //need to normalize the open ends in the array

        shapeTiles = removeConnectedOpenEnds(shapeTiles);

        int count=0;

        for(int a=0; a<shapeTiles.size(); a++){
            count = count + shapeTiles.get(a).getOpenSideCount();
        }
        System.out.println("count: "+count);

        if(connectedToAnotherLoop()){
            //add to loop
        }else {

        }

    }

    private ArrayList<Tile> removeConnectedOpenEnds(ArrayList<Tile> shapeTiles) {


        for(int a=0; a<shapeTiles.size(); a++) {
            Tile tile = shapeTiles.get(a);
            if(tile.hasOpenSides()) {
                evaluatedTileOpenEnds(tile);
            }
        }
        return shapeTiles;
    }

    private void evaluatedTileOpenEnds(Tile tile) {

        ArrayList<Side> openSides = tile.getAllOpenSides();

        for(int b=0; b<openSides.size(); b++){

            Side side = openSides.get(b);
            int x = tile.getArrayPosition().getX();
            int y = tile.getArrayPosition().getY();

            Coordinates adjacentTileCoords = getAdjacentCoords(side, x,y);
            Tile adjacentTile = getAdjacentTile(adjacentTileCoords);

            if (!adjacentTile.isEmpty()) {

                side.setOpen(false);

                String sideName = side.getSideName();

                Side adjside = adjacentTile.getOppositeSide(sideName);


                /**
                 *
                 * need to check whether the adjacent tile is part of the shape
                 *
                 * maybe give it an id 1st...?
                 */



                if (adjside.isOpen()) {

                    adjside.setOpen(false);
                    side.setConnected(true);
                    adjside.setConnected(true);
                }
            }
        }
    }

    private void matchOpenSides(ArrayList<Side> openSides) {




    }

    private ArrayList<Side> getOpenSidesOnly(ArrayList<Tile> shapeTiles) {

        ArrayList<Side> openSides = new ArrayList<>();

        for(int a=0; a<shapeTiles.size(); a++) {
            Tile tile = shapeTiles.get(a);
            if(tile.hasOpenSides()){

                ArrayList<Side> tempOpenSides = tile.getAllOpenSides();
                openSides = mergeArrayBtoA(tempOpenSides, openSides);
            }
        }

        return openSides;
    }

    private ArrayList<Side> mergeArrayBtoA(ArrayList<Side> arrayA, ArrayList<Side> arrayB) {
        for(int a=0;a<arrayB.size(); a++){
            arrayA.add(arrayB.get(a));
        }
        return arrayA;
    }

    private boolean connectedToAnotherLoop() {



        return false;
    }

    private void checkforLoops() {

        evaluatedTiles = new ArrayList<>();

        for(int a=0; a<gridWidth;a++){
            for(int b=0; b<gridHeight;b++){
                Tile tile = tiles[a][b];
                if(!tile.isEmpty()) {
                    System.out.println("found tile: ("+a+","+b+")");

                    if(hasOpenSides(tile.getSides())){
                        System.out.println("has open sides: true");

                        //is tiles already in evaluated list?

                        evaluateTile(tile, a, b);
                    }
                }
            }
        }
    }

    private void evaluateTile(Tile tile, int x, int y) {

        startTile = tile;
        Side side = getOpenSide(tile.getSides()); //gets 1st open side

        if(side==null){
            System.out.println("null");
        }

        //gets coords of the tile next to the open side
        Coordinates adjacentTileCoords = getAdjacentCoords(side, x, y);

        while(true) {

            Tile adjacentTile = getAdjacentTile(adjacentTileCoords);

            if (!adjacentTile.isEmpty()) {

                side.setOpen(false);

                String sideName = side.getSideName();

                Side adjside = adjacentTile.getOppositeSide(sideName);

                if (adjside.isOpen()) {

                    adjside.setOpen(false);

                    side = getOpenSide(adjacentTile.getSides());
                    int ax = adjacentTileCoords.getX();
                    int ay = adjacentTileCoords.getY();
                    System.out.println("evaluating: ("+ax+","+ay+")");
                    adjacentTileCoords = getAdjacentCoords(adjside,ax ,ay);
                }else{
                    break;
                }
            }else{
                break;
            }
        }
    }

    private Coordinates getAdjacentCoords(Side side, int x, int y){


        int dx = side.getAdjacent().getX() + x;
        int dy = side.getAdjacent().getY() + y;

        return new Coordinates(dx, dy);
    }

    private Tile getAdjacentTile(Coordinates position) {

        return tiles[position.getX()][position.getY()];
    }

    private Boolean hasOpenSides(Side[] sides) {

        for(int a=0;a<sides.length; a++){
            if(sides[a].isOpen()){
                return true;
            }
        }
        return false;
    }

    private Side getOpenSide(Side[] sides) {

        Side side= null;

        for(int a=0;a<sides.length; a++){
            if(sides[a].isOpen()){
                side = sides[a];
            }
        }
        return side;
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


}
