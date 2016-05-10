package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import Model.Model;
import Model.Coordinates;


public class View extends JPanel{

    private int DELAY = 1;
    private Timer timer;
    private Model theModel;
    private Coordinates mousePosition;

    public View(){
        hideMouse();
        setMousePosition(new Coordinates(0,0));
    }

    public void init(Model theModel) {

        this.theModel=theModel;

        startTimer();
    }

    private void startTimer() {
        //Swing Timer
        timer = new Timer(DELAY, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                update();
                repaint();
                revalidate();
            }
        });
        timer.start();
    }

    private void update() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        setBackground(Color.white);

        theModel.getBackground().draw(g2d);
        theModel.getGrid().draw(g2d);
        theModel.getCurrentShape().draw(g2d, mousePosition.getX(), mousePosition.getY());
    }

    public void setMousePosition(Coordinates mousePosition) {
        this.mousePosition = mousePosition;
    }

    private void hideMouse(){

        ImageIcon emptyIcon = new ImageIcon(new byte[0]);
        Cursor invisibleCursor = getToolkit().createCustomCursor(
                emptyIcon.getImage(), new Point(0,0), "Invisible");
        this.setCursor(invisibleCursor);
    }
}






















