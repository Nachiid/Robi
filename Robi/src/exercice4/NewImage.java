package exercice4;

import graphicLayer.GImage;
import stree.parser.SNode;
import java.awt.Image;
import java.awt.Toolkit;

public class NewImage implements Command {
    @Override
    public Reference run(Reference reference, SNode method) {
        try {
            String filename = method.get(2).contents();
            Image img = Toolkit.getDefaultToolkit().getImage(filename);
            GImage gImage = new GImage(img);

            Reference ref = new Reference(gImage);
            ref.addCommand("setColor", new SetColor());
            ref.addCommand("translate", new Translate());

            return ref;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
