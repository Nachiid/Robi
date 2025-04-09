package exercice5;

import graphicLayer.GString;
import stree.parser.SNode;

public class NewString5 implements Command5 {
    public Reference5 run(Reference5 reference, SNode method) {
        String text = method.get(2).contents();
        GString gstring = new GString(text);
        Reference5 ref = new Reference5(gstring);
        ref.addCommand("setColor", new SetColor5());
        ref.addCommand("translate", new Translate5());
        return ref;
    }
}