package Model.Loops;

import Model.Side;
import Model.Tile;

public class Connections {

    private Tile evaluatedTile;
    private Tile connectedTile;
    private Side evaluatedSide;
    private Side connectedSide;

    public Connections(Tile evaluatedTile, Tile connectedTile, Side evaluatedSide, Side connectedSide) {
        this.evaluatedTile = evaluatedTile;
        this.connectedTile = connectedTile;
        this.evaluatedSide = evaluatedSide;
        this.connectedSide = connectedSide;
    }

    public String toString(){

        return "connection between "+evaluatedTile.toString()+" and "+ connectedTile.toString();

    }

    public boolean isValidConnectionAvailable(){

        if(connectedSide.isOpen()){
            return true;
        }
        return false;
    }

    public boolean availableConnectedSide() {

        if(connectedSide==null){
            return false;
        }
        return true;
    }


    public Tile getEvaluatedTile() {
        return evaluatedTile;
    }

    public void setEvaluatedTile(Tile evaluatedTile) {
        this.evaluatedTile = evaluatedTile;
    }

    public Tile getConnectedTile() {
        return connectedTile;
    }

    public void setConnectedTile(Tile connectedTile) {
        this.connectedTile = connectedTile;
    }

    public Side getEvaluatedSide() {
        return evaluatedSide;
    }

    public void setEvaluatedSide(Side evaluatedSide) {
        this.evaluatedSide = evaluatedSide;
    }

    public Side getConnectedSide() {
        return connectedSide;
    }

    public void setConnectedSide(Side connectedSide) {
        this.connectedSide = connectedSide;
    }
}

