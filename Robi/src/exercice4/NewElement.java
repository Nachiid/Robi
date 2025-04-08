package exercice4;

import graphicLayer.GElement;
import stree.parser.SNode;

public class NewElement implements Command {
    @Override
    public Reference run(Reference reference, SNode method) {
        try {
            @SuppressWarnings("unchecked")
            Class<GElement> clazz = (Class<GElement>) reference.getReceiver();
            GElement e = clazz.getDeclaredConstructor().newInstance();
            Reference ref = new Reference(e);
            ref.primitives.put("setColor", new Exercice4_1_0().getSetColorCommand());
            ref.primitives.put("translate", new Exercice4_1_0().getTranslateCommand());
            ref.primitives.put("setDim", new SetDim());
            return ref;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
