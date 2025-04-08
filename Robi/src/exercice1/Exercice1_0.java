package exercice1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Exercice1_0 {
    enum Etat { DROITE, BAS, GAUCHE, HAUT }

    GSpace space = new GSpace("Exercice 1", new Dimension(400, 400));
    GRect robi = new GRect();

    Dimension lastSize;
    private Etat etat = Etat.DROITE;
    private Timer animationTimer;

    public Exercice1_0() {
        space.addElement(robi);
        robi.setPosition(new Point(0, 0));
        robi.setDimension(new Dimension(50, 50));
        space.open();

        lastSize = space.getSize();

        space.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = space.getSize();
                Point pos = robi.getPosition();
                Dimension dim = robi.getDimension();

                double scaleX = (double) newSize.width / lastSize.width;
                double scaleY = (double) newSize.height / lastSize.height;

                int newWidth = (int)(dim.width * scaleX);
                int newHeight = (int)(dim.height * scaleY);
                int newX = Math.max(0, Math.min((int)(pos.x * scaleX), newSize.width - newWidth));
                int newY = Math.max(0, Math.min((int)(pos.y * scaleY), newSize.height - newHeight));

                // Mise à jour thread-safe
                SwingUtilities.invokeLater(() -> {
                    robi.setPosition(new Point(newX, newY));
                    robi.setDimension(new Dimension(newWidth, newHeight));
                });

                lastSize = newSize;
                checkDirectionAfterResize();
            }
        });

        // Configuration du Timer pour l'animation
        animationTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animateStep();
            }
        });
        animationTimer.start();
    }

    private void animateStep() {
        SwingUtilities.invokeLater(() -> {
            Point pos = robi.getPosition();
            Dimension dim = robi.getDimension();
            int step = 1;

            switch (etat) {
                case DROITE:
                    if (pos.x + dim.width + step <= space.getWidth()) {
                        robi.translate(new Point(step, 0));
                    } else {
                        etat = Etat.BAS;
                    }
                    break;
                case BAS:
                    if (pos.y + dim.height + step <= space.getHeight()) {
                        robi.translate(new Point(0, step));
                    } else {
                        etat = Etat.GAUCHE;
                    }
                    break;
                case GAUCHE:
                    if (pos.x - step >= 0) {
                        robi.translate(new Point(-step, 0));
                    } else {
                        etat = Etat.HAUT;
                    }
                    break;
                case HAUT:
                    if (pos.y - step >= 0) {
                        robi.translate(new Point(0, -step));
                    } else {
                        etat = Etat.DROITE;
                        // Changement de couleur après un tour complet
                        Random rand = new Random();
                        Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                        robi.setColor(randomColor);
                        //Arret apres un tour complet
                        animationTimer.stop();
                    }
                    break;
            }
        });
    }

    private void checkDirectionAfterResize() {
        SwingUtilities.invokeLater(() -> {
            Point pos = robi.getPosition();
            Dimension dim = robi.getDimension();
            switch (etat) {
                case DROITE:
                    if (pos.x + dim.width >= space.getWidth()) {
                        etat = Etat.BAS;
                    }
                    break;
                case BAS:
                    if (pos.y + dim.height >= space.getHeight()) {
                        etat = Etat.GAUCHE;
                    }
                    break;
                case GAUCHE:
                    if (pos.x <= 0) {
                        etat = Etat.HAUT;
                    }
                    break;
                case HAUT:
                    if (pos.y <= 0) {
                        etat = Etat.DROITE;
                    }
                    break;
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Exercice1_0());
    }
}