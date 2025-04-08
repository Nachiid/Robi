package exercice1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;

import graphicLayer.GRect;
import graphicLayer.GSpace;

public class Exercice1_0 {
    GSpace space = new GSpace("Exercice 1", new Dimension(400, 400));
    GRect robi = new GRect();

    Dimension lastSize;

    public Exercice1_0() {
        space.addElement(robi);
        robi.setPosition(new Point(0, 0));
        robi.setDimension(new Dimension(50, 50));
        space.open();

        // Sauvegarde taille initiale
        lastSize = space.getSize();

        // Ajoute le listener directement sur l’objet space (qui est un composant graphique)
        space.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = space.getSize();
                Point pos = robi.getPosition();
                Dimension dim = robi.getDimension();

                // Calculer les rapports d’échelle
                double scaleX = (double) newSize.width / lastSize.width;
                double scaleY = (double) newSize.height / lastSize.height;

                // Recalculer la position
                int newX = (int) (pos.x * scaleX);
                int newY = (int) (pos.y * scaleY);

                // Recalculer la taille
                int newWidth = (int) (dim.width * scaleX);
                int newHeight = (int) (dim.height * scaleY);

                // Appliquer les nouvelles valeurs
                robi.setPosition(new Point(newX, newY));
                robi.setDimension(new Dimension(newWidth, newHeight));

                // Mettre à jour la taille de référence
                lastSize = newSize;
            }
        });

        // Animation dans un thread séparé
        new Thread(() -> {
        	//while true -> tester les redimensions
            while (true) {
                animate();
            }
        }).start();
    }

    private void animate() {
        try {
            // Déplacement vers la droite
            while (robi.getPosition().x + robi.getDimension().width < space.getWidth()) {
                robi.translate(new Point(1, 0));
                Thread.sleep(5);
            }

            // Déplacement vers le bas
            while (robi.getPosition().y + robi.getDimension().height < space.getHeight()) {
                robi.translate(new Point(0, 1));
                Thread.sleep(5);
            }

            // Déplacement vers la gauche
            while (robi.getPosition().x > 0) {
                robi.translate(new Point(-1, 0));
                Thread.sleep(5);
            }

            // Déplacement vers le haut
            while (robi.getPosition().y > 0) {
                robi.translate(new Point(0, -1));
                Thread.sleep(5);
            }

            // Changement de couleur aléatoire
            Random rand = new Random();
            Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            robi.setColor(randomColor);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Exercice1_0();
    }
}
