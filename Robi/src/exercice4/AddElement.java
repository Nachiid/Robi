package exercice4;

import graphicLayer.GElement;
import graphicLayer.GSpace;
import stree.parser.SNode;

public class AddElement implements Command {
    public Reference run(Reference receiver, SNode method) {
        String name = method.get(1).contents();
        Reference elementRef = Interpreter.compute((Environment) receiver.receiver, method.get(2));
        ((GSpace) receiver.receiver).addElement((GElement) elementRef.receiver);
        ((Environment) receiver.receiver).addReference(name, elementRef);
        return receiver;
    }
}
