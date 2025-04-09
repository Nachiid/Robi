package exercice5;

import graphicLayer.GImage;
import stree.parser.SNode;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

public class NewImage5 implements Command5 {
    @Override
    public Reference5 run(Reference5 classRef, SNode method) {
        if (method.size() < 2) {
            throw new RuntimeException("Usage: (Image new <filename>)");
        }

        try {
            String filename = method.get(1).contents();
            Image img = Toolkit.getDefaultToolkit().getImage(filename);

            MediaTracker tracker = new MediaTracker(new java.awt.Canvas());
            tracker.addImage(img, 0);
            tracker.waitForAll();

            GImage gImage = new GImage(img);

            Reference5 ref = new Reference5(gImage);
            ref.addCommand("setColor", new SetColor5());
            ref.addCommand("translate", new Translate5());
            ref.addCommand("add", new AddElement(Exercice5.getEnvironment()));
            ref.addCommand("del", new DelElement5(Exercice5.getEnvironment()));
            
            //System.out.println("Création image : " + filename);


            return ref;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du chargement de l'image.");
        }
    }
}
