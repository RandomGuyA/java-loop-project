package Model;

public class Side {

    private String sideName;
    private Coordinates adjacent;
    private boolean isOpen;
    private boolean isConnected;

    public Side(String sideName, Coordinates adjacent, boolean isOpen) {
        this.sideName = sideName;
        this.adjacent = adjacent;
        this.isOpen = isOpen;
        isConnected=false;
    }

    public String toString(){

        return "\nSide name: "+sideName +"\n isOpen: "+isOpen+"\n isConnected: "+isConnected;

    }

    public String getSideName() {
        return sideName;
    }

    public void setSideName(String sideName) {
        this.sideName = sideName;
    }

    public Coordinates getAdjacent() {
        return adjacent;
    }

    public void setAdjacent(Coordinates adjacent) {
        this.adjacent = adjacent;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
