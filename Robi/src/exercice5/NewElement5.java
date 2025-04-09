package exercice5;

import graphicLayer.*;
import stree.parser.SNode;

public class NewElement5 implements Command5 {
    @Override
    public Reference5 run(Reference5 classRef, SNode method) {
        try {
            GElement element = ((Class<GElement>) classRef.getReceiver()).getDeclaredConstructor().newInstance();
            Reference5 ref = new Reference5(element);
            ref.addCommand("setColor", new SetColor5());
            ref.addCommand("translate", new Translate5());
            ref.addCommand("setDim", new SetDim5());
            ref.addCommand("add", new AddElement(Exercice5.getEnvironment()));
            ref.addCommand("del", new DelElement5(Exercice5.getEnvironment()));
            return ref;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
