package Model.Shapes;

import Model.Coordinates;
import Model.Side;
import Model.Sprite;
import Model.Tileset;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Shape implements IShape{

    private String DIRECTORY = "Data/shapes/";
    private Tileset tileset;
    private State[] states;
    private int currentState;
    private Coordinates position;

    public Shape(){

    }

    public Shape(String fileName, Tileset tileset){
        this.tileset=tileset;
        Document xmlDoc = loadXMLFile(fileName);
        states = generateStates(xmlDoc);
        currentState=0;
    }

    public void draw(Graphics2D g2d, int mouseX, int mouseY){

        position = new Coordinates(mouseX, mouseY);
        ArrayList<Sprite> sprites = states[currentState].getSprites();

        for(int i=0; i<sprites.size(); i++){
            Sprite sprite = sprites.get(i);
            try {
                int x = (sprite.getCoordinates().getX() * tileset.getTileWidth()) + position.getX();
                int y = (sprite.getCoordinates().getY() * tileset.getTileHeight()) + position.getY();

                //System.out.println("("+x+","+y+")");

                g2d.drawImage(sprite.getImage(), x, y, null);

            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }
    }


    private State[] generateStates(Document xmlDoc){

        NodeList nodeList = xmlDoc.getElementsByTagName("state");

        State[] states = new State[4];

        for(int i=0; i<nodeList.getLength(); i++){
           // System.out.println("get State " + (i+1));
            Node nNode = nodeList.item(i);
            states[i] = generateSingleState(nNode);
        }
        return states;
    }

    private State generateSingleState(Node nNode) {

        State state = null;

        if (isNode(nNode)) {

            Element eElement = (Element) nNode;
            NodeList column = eElement.getElementsByTagName("column");

            state = new State(iterateRows(column));
        }
        return state;
    }

    private ArrayList<Sprite> iterateRows(NodeList column) {

        ArrayList<Sprite> sprites = new ArrayList<Sprite>();

        for(int i=0; i<column.getLength(); i++) {

            Node columnNode = column.item(i);

            if (isNode(columnNode)) {

                Element columnElement = (Element) columnNode;
                NodeList row = columnElement.getElementsByTagName("row");


                for (int j = 0; j < row.getLength(); j++) {

                    Node rowNode = row.item(j);

                    if (isNode(rowNode)) {
                        Element rowElement = (Element) rowNode;

                        if (rowElement.getAttribute("active").equals("true")) {

                            int x = Integer.parseInt(rowElement.getAttribute("xcoord"));
                            int y = Integer.parseInt(rowElement.getAttribute("ycoord"));

                            //System.out.println("(" + x + "," + y + ")");
                           // System.out.println("(" + i + "," + j + ")");




                            sprites.add(new Sprite(tileset.getSingleSprite(x, y), new Coordinates(i-1, j-1), new Side[4]));  ///need to get side info from the tileset
                        }
                    }
                }
            }
        }

        return sprites;
    }

    private boolean isNode(Node nNode){
        return (nNode.getNodeType() == Node.ELEMENT_NODE);
    }

    private Document loadXMLFile(String fileName){

        Document doc = null;

        try{
            File inputFile = new File(DIRECTORY + fileName);
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

    public State[] getStates() {
        return states;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public Coordinates getPosition() {
        return position;
    }
}
