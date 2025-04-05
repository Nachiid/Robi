package test;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Timer;
import graphicLayer.GRect;
import graphicLayer.GSpace;

public class test {

    GSpace space = new GSpace("Exercice 1", new Dimension(500, 500));
    GRect robi = new GRect();
    private int phase = 0;
    private Timer timer;

    public test() {
        space.addElement(robi);
        robi.setDimension(new Dimension(50, 50));
        space.open();
        timer = new Timer(10, e -> animate());
        timer.start();
    }

    private void animate() {
        switch (phase) {
            case 0:
                moveRight();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveUp();
                break;
            case 4:
                changeColor();
                timer.stop();
                break;
        }
    }

    private void moveRight() {
        int targetX = space.getWidth() - robi.getWidth();
        if (robi.getX() < targetX) {
            robi.setX(robi.getX() + 1);
        } else {
            phase++;
        }
    }

    private void moveDown() {
        int targetY = space.getHeight() - robi.getHeight();
        if (robi.getY() < targetY) {
            robi.setY(robi.getY() + 1);
        } else {
            phase++;
        }
    }

    private void moveLeft() {
        int targetX = 0;
        if (robi.getX() > targetX) {
            robi.setX(robi.getX() - 1);
        } else {
            phase++;
        }
    }

    private void moveUp() {
        int targetY = 0;
        if (robi.getY() > targetY) {
            robi.setY(robi.getY() - 1);
        } else {
            phase++;
        }
    }

    private void changeColor() {
        Color color = Color.getHSBColor((float) Math.random(), 1.0f, 1.0f);
        robi.setColor(color);
    }

    public static void main(String[] args) {
        new test();
    }
}