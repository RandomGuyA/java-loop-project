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
    private Tile tile;
    private Grid grid;

    public LoopDetection(){}

    public LoopDetection(Grid grid, Tile tile){
        connections = getConnections(grid, tile);
        this.tile=tile;
        this.grid=grid;
    }

    public void loopDetector(Grid grid, Tile tile) {

        connections = getConnections(grid, tile);

        System.out.println("Evaluating tile: "+tile.toString());
        System.out.println("tile has "+tile.getOpenSideCount()+" open sides");

        if(hasNoAdjacentTiles(grid, tile)){
            System.out.println("tile has no adjacent Tiles, creating new Loop");
            grid.createNewLoop(tile);
        }else {

            if(isConnectedToAnotherTile()){

                int connectionTotal = calculateConnectionTotal();

                System.out.println("tile is connected to "+connectionTotal+" other tiles");

                if(connectionTotal==1){



                    Tile adjTile = getAdjacentConnectedTile();
                    Loop loop = grid.getLoopById(adjTile.getArrayId());
                    loop.addTileToLoop(tile);

                    evaluateLoop(grid,loop);
                    countOpenEnds(loop);
                    //System.out.println("array id "+adjTile.getArrayId());

                }else if(connectionTotal==2){



                    ArrayList<Tile> connectedTiles = getArrayOfAdjacentConnectedTiles();

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

        Tile connectedTile;
        Side connectedSide;

        for(int a=0; a<sides.size(); a++){

            connectedTile = null;
            connectedSide = null;

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

            if(isConnectedToAnotherTile()){
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

    public int calculateConnectionTotal() {

        int count =0;

        for(int a=0; a<connections.size(); a++) {

            Connections con = connections.get(a);

            System.out.println(con.toString());

            if(con.availableConnectedSide()){

                System.out.println(con.getConnectedSide().toString());

                if(con.getConnectedSide().isOpen()){
                    count++;
                }

            }
        }
        return count;
    }


    public ArrayList<Tile> getArrayOfAdjacentConnectedTiles() {

        ArrayList<Tile> connectedTiles = new ArrayList<Tile>();

        for(int a=0; a<connections.size(); a++) {

            Connections con = connections.get(a);

            System.out.println(con.toString());

            if(con.getConnectedSide().isOpen()){
                connectedTiles.add(con.getConnectedTile());
            }

        }
        return connectedTiles;
    }

    public Tile getAdjacentConnectedTile() {
        Tile tile = null;

        System.out.println("Potential Connections: "+connections.size());

        for(int a=0; a<connections.size(); a++) {

            Connections con = connections.get(a);

            System.out.println(con.toString());

            if(con.availableConnectedSide()){
                tile = con.getConnectedTile();
            }
        }
        return tile;
    }

    public boolean isConnectedToAnotherTile() {

        for(int a=0; a<connections.size(); a++){

            Connections con = connections.get(a);

            if(con.availableConnectedSide()){
                return con.isValidConnectionAvailable();
            }
        }
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
