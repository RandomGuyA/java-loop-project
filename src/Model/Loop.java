package Model;


import java.util.ArrayList;

public class Loop {

    private int id;
    private ArrayList<Tile> loopTiles;

    public Loop(int id, ArrayList<Tile> loopTiles) {
        this.id = id;
        this.loopTiles = loopTiles;
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
}
