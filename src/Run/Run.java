package Run;

import java.awt.Dimension;

import javax.swing.JFrame;

import Controller.Controller;
import Model.Model;
import View.View;

public class Run {

    private static void createAndShowGUI() {
        Model theModel = new Model();
        View theView = new View();
        Controller theController = new Controller(theModel,theView);
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(new Dimension(800,640));
        frame.setContentPane(theView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}