package exercice4;

import graphicLayer.GElement;
import graphicLayer.GSpace;
import stree.parser.SNode;

public class AddElement implements Command {
    Environment env;

    public AddElement(Environment env) {
        this.env = env;
    }

    @Override
    public Reference run(Reference receiver, SNode method) {
        try {
            String name = method.get(1).contents();
            Reference elementRef = Interpreter.compute(env, method.get(2));

            ((GSpace) receiver.getReceiver()).addElement((GElement) elementRef.getReceiver());
            env.addReference(name, elementRef);
            
           // System.out.println("Ajout de l'élément " + name + " dans le space");


            return receiver;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
