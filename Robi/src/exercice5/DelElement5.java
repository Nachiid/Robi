package exercice5;

import graphicLayer.GContainer;
import graphicLayer.GElement;
import stree.parser.SNode;

public class DelElement5 implements Command5 {
    private final Environment5 env;

    public DelElement5(Environment5 env) {
        this.env = env;
    }

    @Override
    public Reference5 run(Reference5 receiver, SNode method) {
        String name = method.get(1).contents();
        Reference5 child = receiver.getChild(name);

        if (child == null) {
            throw new RuntimeException("Aucun élément nommé '" + name + "' dans ce conteneur.");
        }

        Object rec = receiver.getReceiver();
        if (!(rec instanceof GContainer)) {
            throw new RuntimeException("L'objet " + rec + " n'est pas un conteneur graphique.");
        }

        ((GContainer) rec).removeElement((GElement) child.getReceiver());
        receiver.removeChild(name);
        env.getAll().remove(getFullPath(receiver, name));

        return receiver;
    }

    private String getFullPath(Reference5 parent, String childName) {
        for (String key : env.getAll().keySet()) {
            if (env.getAll().get(key) == parent) {
                return key + "." + childName;
            }
        }
        return childName;
    }
}
