package Model.Loops;

import Model.Grid;
import Model.Tile;
import Model.Side;
import Model.Coordinates;

import java.util.ArrayList;

/**
 * The aim of this class is to sort the tiles into the correct loop array within the grid
 * they can either be added to an existing loop or create a new loop entirely
 */

public class LoopDetection {

    private ArrayList<Connections> connections;

    public void loopDetector(Grid grid, Tile tile) {

        connections = getConnections(grid, tile);

        System.out.println("Evaluating tile: ("+tile.getArrayPosition().getX()+" , "+tile.getArrayPosition().getY()+")");

        System.out.println("tile has "+tile.getOpenSideCount()+" open sides");

        if(hasNoAdjacentTiles(grid, tile)){
            System.out.println("tile has no adjacent Tiles, creating new Loop");
            grid.createNewLoop(tile);
        }else {

            if(isConnectedToAnotherTile(grid, tile)){

                System.out.println("tile is connected to another tile");

                int connectionTotal = calculateConnectionTotal(grid, tile);

                if(connectionTotal==1){

                    System.out.println("tile is connected to 1 other tile");

                    Tile adjTile = getAdjacentConnectedTile(grid,tile);
                    Loop loop = grid.getLoopById(adjTile.getArrayId());
                    loop.addTileToLoop(tile);

                    evaluateLoop(grid,loop);
                    countOpenEnds(loop);
                    //System.out.println("array id "+adjTile.getArrayId());

                }else if(connectionTotal==2){

                    System.out.println("tile is connected to 2 other tile");

                    ArrayList<Tile> connectedTiles = getArrayOfAdjacentConnectedTiles(grid, tile);

                    int id1 = connectedTiles.get(0).getArrayId();
                    int id2 =connectedTiles.get(1).getArrayId();
                    grid.mergeLoopArrays(id1, id2);

                    //merge 2 arrays
                    //get the array id for each connection
                    //create new array
                    //add all to new array

                }
            }else{
                System.out.println("tile has adjacent tiles but is not connected");
                grid.createNewLoop(tile);
            }
        }
    }

    public ArrayList<Connections> getConnections(Grid grid, Tile tile){

        ArrayList<Side> sides = tile.getAllOpenSides();
        ArrayList<Connections> connections = new ArrayList<>();
        Tile[][] tiles = grid.getTiles();

        Tile connectedTile = null;
        Side connectedSide = null;

        for(int a=0; a<sides.size(); a++){

            Side side = sides.get(a);

            Coordinates adjTile = side.getAdjacent();

            int x = tile.getArrayPosition().getX() + adjTile.getX();
            int y = tile.getArrayPosition().getY() + adjTile.getY();

            if(withinBounds(grid, x, y)) {

                Tile gridTile = tiles[x][y];
                connectedTile = gridTile;

                if(!gridTile.isEmpty()) {

                    Side adjSide = gridTile.getOppositeSide(side.getSideName());
                    connectedSide = adjSide;

                }
            }
            connections.add(new Connections(tile, connectedTile,side,connectedSide));
        }
        return connections;
    }


    private void countOpenEnds(Loop loop) {
        ArrayList<Tile> tiles =  loop.getLoopTiles();

        int count = 0;
        for(int a=0; a<tiles.size();a++){
            count = count + tiles.get(a).getOpenSideCount();
        }
        System.out.println("count "+count);

    }

    private void evaluateLoop(Grid grid, Loop loop) {

        ArrayList<Tile> tiles = loop.getLoopTiles();

        for(int a=0; a<tiles.size();a++){
            Tile tile = tiles.get(a);

            if(isConnectedToAnotherTile(grid, tile)){
                setConnectedOpenSides(grid, tile);
            }
        }
    }

    private void setConnectedOpenSides(Grid grid, Tile tile) {

        ArrayList<Side> sides = tile.getAllOpenSides();
        Tile[][] tiles = grid.getTiles();

        for(int a=0; a<sides.size(); a++){

            Side side = sides.get(a);

            Coordinates adjTile = side.getAdjacent();

            int x = tile.getArrayPosition().getX() + adjTile.getX();
            int y = tile.getArrayPosition().getY() + adjTile.getY();

            if(withinBounds(grid, x, y)) {

                Tile gridTile = tiles[x][y];

                if(!gridTile.isEmpty()) {

                    Side adjSide = gridTile.getOppositeSide(side.getSideName());

                    if (adjSide.isOpen()) {
                        side.setOpen(false);
                        adjSide.setOpen(false);
                    }
                }
            }
        }

    }

    public int calculateConnectionTotal(Grid grid, Tile tile) {

        ArrayList<Side> sides = tile.getAllOpenSides();
        Tile[][] tiles = grid.getTiles();

        int count=0;

        for(int a=0; a<sides.size(); a++){

            Side side = sides.get(a);

            Coordinates adjTile = side.getAdjacent();

            int x = tile.getArrayPosition().getX() + adjTile.getX();
            int y = tile.getArrayPosition().getY() + adjTile.getY();

            if(withinBounds(grid, x, y)) {

                Tile gridTile = tiles[x][y];

                if(!gridTile.isEmpty()) {

                    Side adjSide = gridTile.getOppositeSide(side.getSideName());

                    if (adjSide.isOpen()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }


    public ArrayList<Tile> getArrayOfAdjacentConnectedTiles(Grid grid, Tile tile) {

        ArrayList<Side> sides = tile.getAllOpenSides();
        Tile[][] tiles = grid.getTiles();

        ArrayList<Tile> connectedTiles = new ArrayList<Tile>();

        for(int a=0; a<sides.size(); a++){

            Side side = sides.get(a);

            Coordinates adjTile = side.getAdjacent();

            int x = tile.getArrayPosition().getX() + adjTile.getX();
            int y = tile.getArrayPosition().getY() + adjTile.getY();

            if(withinBounds(grid, x, y)) {

                Tile gridTile = tiles[x][y];

                if(!gridTile.isEmpty()) {

                    Side adjSide = gridTile.getOppositeSide(side.getSideName());

                    if (adjSide.isOpen()) {
                        connectedTiles.add(gridTile);
                    }
                }
            }
        }
        return connectedTiles;
    }

    public Tile getAdjacentConnectedTile(Grid grid, Tile tile) {

        ArrayList<Side> sides = tile.getAllOpenSides();
        Tile[][] tiles = grid.getTiles();

        Tile aTile = null;

        for(int a=0; a<sides.size(); a++){

            Side side = sides.get(a);

            Coordinates adjTile = side.getAdjacent();

            int x = tile.getArrayPosition().getX() + adjTile.getX();
            int y = tile.getArrayPosition().getY() + adjTile.getY();

            if(withinBounds(grid, x, y)) {

                Tile gridTile = tiles[x][y];

                if(!gridTile.isEmpty()) {

                    Side adjSide = gridTile.getOppositeSide(side.getSideName());

                    if (adjSide.isOpen()) {
                        aTile = gridTile;
                    }
                }
            }
        }
        return aTile;
    }


    public boolean isConnectedToAnotherTile(Grid grid, Tile tile) {

        for(int a=0; a<connections.size(); a++){

            Connections con = connections.get(a);

            if(con.availableConnectedSide()){
                return con.isValidConnectionAvailable();
            }
        }



        /*
        ArrayList<Side> sides = tile.getAllOpenSides();
        Tile[][] tiles = grid.getTiles();
        System.out.println("tile has "+sides.size()+" open sides");
        for(int a=0; a<sides.size(); a++){

            Side side = sides.get(a);
            System.out.println("Side Location: "+side.getSideName());
            Coordinates adjTile = side.getAdjacent();

            int x = tile.getArrayPosition().getX() + adjTile.getX();
            int y = tile.getArrayPosition().getY() + adjTile.getY();

            if(withinBounds(grid, x, y)) {

                Tile gridTile = tiles[x][y];
                System.out.println("Evaluating adjacent tile at location ("+x+" , "+y+")");

                System.out.println("Tile is empty:"+ gridTile.isEmpty() );
                if(!gridTile.isEmpty()) {

                    Side adjSide = gridTile.getOppositeSide(side.getSideName());


                    ArrayList<Side> adjSides = gridTile.getAllOpenSides();
                    for(int b=0; b<adjSides.size();b++){
                        System.out.println("side names: "+adjSides.get(b).getSideName());
                    }


                    System.out.println("adjacent tile has "+gridTile.getOpenSideCount()+" open sides");

                    System.out.println("Adjacent Side of Tile is Open: "+adjSide.isOpen());
                    if (adjSide.isOpen()) {

                        return true;
                    }
                }
            }
        }*/
        return false;
    }

    public boolean hasNoAdjacentTiles(Grid grid, Tile tile) {

        Side[] sides = tile.getSides();
        Tile[][] tiles = grid.getTiles();

        for(int a=0; a<sides.length;a++){

            Coordinates adjSide = sides[a].getAdjacent();

            int x = tile.getArrayPosition().getX() + adjSide.getX();
            int y = tile.getArrayPosition().getY() + adjSide.getY();

            if(withinBounds(grid, x, y)) {

                Tile gridTile = tiles[x][y];

                if (!gridTile.isEmpty()) { //if not empty
                    return false;
                }
            }
        }
        return true;
    }

    public boolean withinBounds(Grid grid, int x, int y) {

        if(x>=0 && x<=grid.getGridWidth()-1 && y>=0 && y<=grid.getGridHeight()-1){

            return true;
        }
        return false;
    }
}
