package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Tileset {

    private BufferedImage[][] sprites;
    private int tileCountX, tileCountY, tileWidth, tileHeight, marginX, marginY;
    private BufferedImage tileset;
    private String DIRECTORY = "Assets/";
    private ArrayList<SpriteMap> spriteMaps;
    private String[] strSides ;

    public Tileset(String fileName, int tileWidth, int tileHeight, int tileCountX, int tileCountY, int marginX, int marginY) {

        this.tileCountX = tileCountX;
        this.tileCountY = tileCountY;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.marginX = marginX;
        this.marginY = marginY;

        strSides = new String[]{"top", "right", "bottom", "left"};

        setupSpriteMap("data/sprites/sprite-map.xml");
        setupTileSheet(fileName);
    }

    private void setupSpriteMap(String filename) {

        Document xmlDoc = loadXMLFile(filename);
        NodeList nodeList = xmlDoc.getElementsByTagName("sprite");

        spriteMaps = new ArrayList<>();

        for(int i=0; i<nodeList.getLength(); i++){

            Node nNode = nodeList.item(i);
            Element eElement = (Element) nNode;

            int x = Integer.parseInt(eElement.getAttribute("x"));
            int y = Integer.parseInt(eElement.getAttribute("y"));

            spriteMaps.add(new SpriteMap(eElement.getAttribute("binary"), new Coordinates(x,y)));
        }
    }

    private void setupTileSheet(String fileName) {
        tileset = loadImage(fileName);
        if(tileset!=null){
            sprites = splitTilesetIntoTiles(tileset);
        }
    }

    private BufferedImage loadImage(String fileName){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(DIRECTORY + fileName));
        } catch (IOException e) {
            System.out.println("failed to load asset");
        }
        return img;
    }

    private BufferedImage[][] splitTilesetIntoTiles(BufferedImage tileset) {

        BufferedImage[][] sprites = new BufferedImage[tileCountX][tileCountY];

        for (int i=0; i<tileCountX; i++){
            for (int j=0; j<tileCountY; j++){
                sprites[i][j] = tileset.getSubimage(i*(tileWidth+marginX), j*(tileHeight+marginY), tileWidth, tileHeight);
            }
        }
        return sprites;
    }

    public Side[] getSides(int x, int y){

        Side[] sides = new Side[4];
        BufferedImage bufferedImages = sprites[x][y];

        SpriteMap spriteMap = getspriteMap(x,y);

        String map = spriteMap.getBinaryValue();

        for(int a=0; a<strSides.length; a++){
            String strSide = strSides[a];
            int top  = Integer.parseInt(map.substring(a,(a+1)));
            boolean isOpen =(top==1)?true:false;
            getAdjacentCoords(strSide);
            sides[a]= new Side(strSide,getAdjacentCoords(strSide), isOpen);
        }
        return sides;
    }

    private Coordinates getAdjacentCoords(String side){

        switch(side){
            case "top":
                return new Coordinates(0,-1);
            case "right":
                return new Coordinates(1,0);
            case "bottom":
                return new Coordinates(0,1);
            case "left":
                return new Coordinates(-1,0);
        }
        return null;
    }

    private SpriteMap getspriteMap(int x, int y) {

        for(int a=0;a<spriteMaps.size(); a++){
            Coordinates coords = spriteMaps.get(a).getSpriteLocation();
            if(coords.getX()==x&&coords.getY()==y){
                return spriteMaps.get(a);
            }
        }
        return null;
    }

    public BufferedImage getSingleSprite(int x, int y){

        return sprites[x][y];
    }

    public BufferedImage[][] getSprites() {
        return sprites;
    }

    public void setSprites(BufferedImage[][] sprites) {
        this.sprites = sprites;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    private Document loadXMLFile(String fileName){

        Document doc = null;

        try{
            File inputFile = new File(fileName);
            System.out.println("Loading File: "+ fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Loaded: " + doc.getDocumentElement().getAttribute("name"));

        }catch(Exception e){
            e.printStackTrace();
        }
        return doc;
    }
}