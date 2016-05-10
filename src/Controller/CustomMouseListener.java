package Controller;


import Model.Model;
import View.View;
import Model.Grid;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import Model.Coordinates;

public class CustomMouseListener implements MouseListener, MouseMotionListener {

    private View theView;
    private Model theModel;

    public CustomMouseListener(View theView, Model theModel) {
        this.theView=theView;
        this.theModel=theModel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){

            int currentState = theModel.getCurrentShape().getCurrentState();

            if(currentState>=3){
                currentState=0;
            }else{
                currentState++;
            }
            theModel.getCurrentShape().setCurrentState(currentState);
        }
        if(SwingUtilities.isLeftMouseButton(e)){

            Grid grid = theModel.getGrid();

            if(grid.allowedToDrop(theModel.getCurrentShape())){
                grid.addShapeToGrid(theModel.getCurrentShape());
                theModel.nextShape();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setMousePosition(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        setMousePosition(e);
    }

    private void setMousePosition(MouseEvent e){

        int tileWidth = theModel.getTILE_WIDTH();
        int tileHeight = theModel.getTILE_HEIGHT();

        int x = (e.getX()/tileWidth)*tileWidth;
        int y = (e.getY()/tileHeight)*tileHeight;

        //System.out.println("("+x+","+y+")");
        theView.setMousePosition(new Coordinates(x,y));
    }


}
