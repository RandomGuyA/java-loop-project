import Model.Grid;
import Model.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GridTest {

    private int TILE_WIDTH = 32;
    private int TILE_HEIGHT = 32;
    private int OFFSET_X = 96;
    private int OFFSET_Y = 96;
    private int GRID_WIDTH = 19;
    private int GRID_HEIGHT = 9;
    private Grid grid;

    @Before
    public void setup(){
        grid = new Grid(TILE_WIDTH,TILE_HEIGHT,OFFSET_X,OFFSET_Y,GRID_WIDTH,GRID_HEIGHT);

    }


    @Test
    public void testRemoveConnectedOpenEnds(){




    }


    public ArrayList<Tile> createTestShapeTiles(){

        ArrayList<Tile> tiles = new ArrayList<Tile>();




        return tiles;
    }
}