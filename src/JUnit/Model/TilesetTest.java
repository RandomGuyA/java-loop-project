package JUnit.Model;

import Model.SpriteMap;
import Model.Tileset;
import org.junit.Before;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TilesetTest {

    private int TILE_WIDTH = 32;
    private int TILE_HEIGHT = 32;
    private int OFFSET_X = 96;
    private int OFFSET_Y = 96;
    private int GRID_WIDTH = 19;
    private int GRID_HEIGHT = 9;

    private Tileset tileset;
    private ArrayList<SpriteMap> spriteMaps;
    private BufferedImage[][] sprites;

    @Before
    public void setUp() throws Exception {

        tileset = new Tileset("shapes.png", TILE_WIDTH, TILE_HEIGHT,3,3,0,0);
        spriteMaps =  tileset.setupSpriteMap("data/sprites/sprite-map.xml");
        sprites = tileset.setupTileSheet("shapes.png");

    }
}