package Model.Loops;


import Model.Model;
import Model.Tile;


import java.util.ArrayList;

public class Loop {

    private int id;
    private ArrayList<Tile> loopTiles;

    public Loop(int id, Tile tile) {
        this.id = id;
        loopTiles = new  ArrayList<Tile>();
        loopTiles.add(tile);
    }
    public Loop(int id, ArrayList<Tile> tiles) {
        this.id = id;
        loopTiles = tiles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Tile> getLoopTiles() {
        return loopTiles;
    }

    public void setLoopTiles(ArrayList<Tile> loopTiles) {
        this.loopTiles = loopTiles;
    }

    public void addTileToLoop(Tile tile) {
        tile.setArrayId(id);
        loopTiles.add(tile);
    }
}
