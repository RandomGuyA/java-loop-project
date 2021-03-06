package Model;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tile {

    private BufferedImage img;
    private boolean isEmpty;
    private Side[] sides;
    private Coordinates arrayPosition;
    private int arrayId;

    public Tile(BufferedImage img, boolean isEmpty, Side[] sides, Coordinates arrayPosition) {
        this.img = img;
        this.isEmpty = isEmpty;
        this.sides = sides;
        this.arrayPosition = arrayPosition;

    }

    public Tile(){
        isEmpty = true;
    }

    public void draw(Graphics2D g2d, int x, int y) {
        //System.out.println("("+x+","+y+")");
        g2d.drawImage(img, x, y, null);

    }

    public String toString(){

        if(this.isEmpty()){
            return "Tile: is Empty";
        }else{
            return "Tile: "+arrayPosition.toString();
        }
    }

    public Side getOppositeSide(String sideName){

        String name = getOppositeName(sideName);

        for(int a=0; a<sides.length; a++){
            if(sides[a].getSideName().equals(name)){
                return sides[a];
            }
        }
        return null;
    }

    public Boolean hasOpenSides() {

        for(int a=0;a<sides.length; a++){
            if(sides[a].isOpen()){
                return true;
            }
        }
        return false;
    }


    public ArrayList<Side> getAllOpenSides(){

        ArrayList<Side> openSides= new ArrayList<>();

        for(int a=0; a<sides.length; a++){
            if(sides[a].isOpen()){
                openSides.add(sides[a]);
            }
        }
        return openSides;
    }

    public int getOpenSideCount(){

        ArrayList<Side> openSides = new ArrayList<>();

        for(int a=0; a<sides.length; a++){
            if(sides[a].isOpen()){
                openSides.add(sides[a]);
            }
        }
        return openSides.size();
    }



    public String getOppositeName(String sideName){

        switch(sideName){
            case "top":
                return "bottom";
            case "right":
                return "left";
            case "bottom":
                return "top";
            case "left":
                return "right";
        }
        return null;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public Side[] getSides() {
        return sides;
    }



    public void setSides(Side[] sides) {
        this.sides = sides;
    }

    public Coordinates getArrayPosition() {
        return arrayPosition;
    }

    public void setArrayPosition(Coordinates arrayPosition) {
        this.arrayPosition = arrayPosition;
    }

    public int getArrayId() {
        return arrayId;
    }

    public void setArrayId(int arrayId) {
        this.arrayId = arrayId;
    }
}
