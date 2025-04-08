package exercice4;

import graphicLayer.GElement;
import graphicLayer.GSpace;
import stree.parser.SNode;

public class DelElement implements Command {
    public Reference run(Reference receiver, SNode method) {
        String name = method.get(1).contents();
        Environment env = (Environment) receiver.receiver;
        GElement e = (GElement) env.getReferenceByName(name).receiver;
        ((GSpace) receiver.receiver).removeElement(e);
        env.variables.remove(name);
        return receiver;
    }
}
