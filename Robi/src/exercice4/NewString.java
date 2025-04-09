package exercice4;

import graphicLayer.GString;
import stree.parser.SNode;

public class NewString implements Command {
    @Override
    public Reference run(Reference reference, SNode method) {
        try {
            String text = method.get(1).contents();
            GString gs = new GString(text);
            Reference ref = new Reference(gs);
            ref.addCommand("translate", new Translate());
            ref.addCommand("setColor", new SetColor());
            return ref;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

