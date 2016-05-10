package Controller;

import Model.Model;
import View.View;

public class Controller {

    private View theView;
    private Model theModel;
    private CustomMouseListener mouseListener;

    public Controller(Model theModel, View theView) {
        this.theView=theView;
        this.theModel=theModel;
        theView.init(theModel);
        mouseListener = new CustomMouseListener(theView, theModel);
        theView.addMouseListener(mouseListener);
        theView.addMouseMotionListener(mouseListener);
    }
}
