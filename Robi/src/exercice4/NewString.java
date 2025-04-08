package exercice4;

import graphicLayer.GString;
import stree.parser.SNode;

public class NewString implements Command {
    public Reference run(Reference reference, SNode method) {
        String text = method.get(2).contents();
        GString gs = new GString(text);
        Reference ref = new Reference(gs);
        ref.primitives.put("setColor", new Exercice4_1_0().getSetColorCommand());
        ref.primitives.put("translate", new Exercice4_1_0().getTranslateCommand());
        return ref;
    }
}
