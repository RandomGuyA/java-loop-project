package Model.Shapes;

import Model.Sprite;

import java.util.ArrayList;

public class State {

    ArrayList<Sprite> sprites;

    public State(ArrayList<Sprite> sprites) {
        this.sprites = sprites;
    }
    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(ArrayList<Sprite> sprites) {
        this.sprites = sprites;
    }
}
