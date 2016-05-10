package Model;

public class Side {

    private String side;
    private Coordinates adjacent;
    private boolean isOpen;

    public Side(String side, Coordinates adjacent, boolean isOpen) {
        this.side = side;
        this.adjacent = adjacent;
        this.isOpen = isOpen;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
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
}
