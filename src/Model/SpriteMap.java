package Model;


public class SpriteMap {

    private String binaryValue;
    private Coordinates spriteLocation;

    public SpriteMap(String binaryValue, Coordinates spriteLocation) {
        this.binaryValue = binaryValue;
        this.spriteLocation = spriteLocation;
    }

    public String getBinaryValue() {
        return binaryValue;
    }

    public void setBinaryValue(String binaryValue) {
        this.binaryValue = binaryValue;
    }

    public Coordinates getSpriteLocation() {
        return spriteLocation;
    }

    public void setSpriteLocation(Coordinates spriteLocation) {
        this.spriteLocation = spriteLocation;
    }
}
