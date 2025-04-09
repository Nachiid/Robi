package exercice5;

import graphicLayer.GContainer;
import graphicLayer.GElement;
import stree.parser.SNode;

public class AddElement implements Command5 {
    Environment5 environment;

    public AddElement(Environment5 env) {
        this.environment = env;
    }

    @Override
    public Reference5 run(Reference5 receiver, SNode method) {
        String name = method.get(1).contents();
        Reference5 elementRef = Interpreter5.compute(environment, method.get(2));

        Object parent = receiver.getReceiver();
        if (parent instanceof GContainer) {
            ((GContainer) parent).addElement((GElement) elementRef.getReceiver());
            receiver.addChild(name, elementRef);
            environment.addReference(name, elementRef);
            return receiver;
        } else {
            throw new RuntimeException("Le receiver n'est pas un conteneur graphique.");
        }
    }
}