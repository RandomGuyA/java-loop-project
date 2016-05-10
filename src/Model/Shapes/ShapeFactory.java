package Model.Shapes;

import Model.Tileset;

public class ShapeFactory {

    public Shape getShape(String shapeType, Tileset tileset){

        if(shapeType == null){
            return null;
        }
        if(shapeType.equalsIgnoreCase("smallCorner")){
            return new SmallCorner(tileset);

        }
        else if(shapeType.equalsIgnoreCase("LargeCorner")){
            return new LargeCorner( tileset);

        }
        else if(shapeType.equalsIgnoreCase("LShape")){
            return new LShape(tileset);
        }
        else if(shapeType.equalsIgnoreCase("InvertedLShape")){
            return new InvertedLShape(tileset);
        }
        else if(shapeType.equalsIgnoreCase("smallStraight")){
            return new SmallStraight(tileset);
        }
        else if(shapeType.equalsIgnoreCase("largeStraight")){
            return new LargeStraight(tileset);
        }

        return null;
    }
}
