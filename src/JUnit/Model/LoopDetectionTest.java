package JUnit.Model;

import Model.*;
import Model.Loops.LoopDetection;
import Model.Shapes.Shape;
import Model.Shapes.ShapeFactory;
import Model.Shapes.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class LoopDetectionTest {

    private Model model;
    private ShapeFactory shapeFactory;
    private LoopDetection loopDetection;
    private Grid grid;

    @Before
    public void setup(){

        model = new Model();
        shapeFactory = new ShapeFactory();
        loopDetection = new LoopDetection();
        grid = model.getGrid();
    }


    @Test
    public void testGetAdjacentConnectedTile(){

        Tile tile1 = createTile(5,5);
        grid.addTileToGrid(tile1);
        Tile tile2 = createTile(4,5);
        grid.addTileToGrid(tile2);

        Tile adjTile = loopDetection.getAdjacentConnectedTile(grid, tile1);

        Assert.assertEquals(4, adjTile.getArrayPosition().getX());

    }

    @Test
    public void testCalculateConnectionTotalWithOneConnection(){

        Tile tile1 = createTile(5,5);
        grid.addTileToGrid(tile1);
        Tile tile2 = createTile(4,5);
        grid.addTileToGrid(tile2);

        Assert.assertEquals(1,loopDetection.calculateConnectionTotal(grid, tile2));

    }
    @Test
    public void testCalculateConnectionTotalWithTwoConnections(){

        Tile tile1 = createTile(5,5);
        grid.addTileToGrid(tile1);
        Tile tile2 = createTile(4,5);
        grid.addTileToGrid(tile2);
        Tile tile3 = createTile(3,5);
        grid.addTileToGrid(tile3);

        Assert.assertEquals(2,loopDetection.calculateConnectionTotal(grid, tile2));

    }


    @Test
    public void testIsConnectedToAnotherTile(){

        Tile tile = createTile(5,5);
        grid.addTileToGrid(tile);
        Tile testTile = createTile(6,5);
        Assert.assertTrue(loopDetection.isConnectedToAnotherTile(grid, testTile));

    }

    @Test
    public void testIsConnectedToAnotherTileWhenFalse(){

        Tile tile1 = createTile(5,5);
        grid.addTileToGrid(tile1);
        Tile tile2 = createTile(3,5);
        grid.addTileToGrid(tile2);

        Assert.assertFalse(loopDetection.isConnectedToAnotherTile(grid, tile2));

    }

    @Test
    public void testGridActiveTileCountWithZero(){

        Assert.assertEquals(0,grid.getNumberOfActiveTiles());
    }

    @Test
    public void testGridActiveTileCount(){

        Tile tile1 = createTile(5,5);
        grid.addTileToGrid(tile1);
        Tile tile2 = createTile(3,5);
        grid.addTileToGrid(tile2);
        Assert.assertEquals(2,grid.getNumberOfActiveTiles());
    }


    @Test
    public void testHasNoAdjacentTilesWithNormalCoords(){

        Tile tile = createTile(5,5);
        Assert.assertTrue(loopDetection.hasNoAdjacentTiles(grid, tile));
    }
    @Test
    public void testHasNoAdjacentTilesWhenFalse(){

        Tile tile = createTile(5,5);
        grid.addTileToGrid(tile);
        Tile testTile = createTile(5,4);
        Assert.assertFalse(loopDetection.hasNoAdjacentTiles(grid, testTile));
    }

    @Test
    public void testHasNoAdjacentTilesOnEdges(){

        Tile tile = createTile(0,0);
        Assert.assertTrue(loopDetection.hasNoAdjacentTiles(grid, tile));
    }

    @Test
    public void testWithinBoundsTrue(){

        Tile tile = createTile(0,5);
        Assert.assertTrue(loopDetection.withinBounds(grid, 0, 0));
    }

    @Test
    public void testWithinBoundsFalse(){

        Tile tile = createTile(0,5);
        Assert.assertFalse(loopDetection.withinBounds(grid, -1, 0));

    }


    private Shape createShape(String shapeType){

        System.out.println("creating "+shapeType);
        Shape shape = shapeFactory.getShape(shapeType, model.getTileset());

        return shape;
    }

    private Tile createTile(int x, int y){

        System.out.println("Creating Tile at grid position: ("+x+","+y+")");

        Shape shape = createShape("LargeStraight");
        State state = shape.getStates()[0];
        Sprite sprite = state.getSprites().get(0);

        Tile tile = new Tile();
        tile.setImg(sprite.getImage());
        tile.setEmpty(false);

        System.out.println("sprite sides "+sprite.getSides().length);

        tile.setSides(sprite.getSides());
        tile.setArrayPosition(new Coordinates(x,y));

        return tile;
    }

}
