package exercice5;


import stree.parser.SNode;

public class AddScriptCommand implements Command5 {
    @Override
    public Reference5 run(Reference5 receiver, SNode method) {
        if (!(receiver instanceof ScriptableReference))
            throw new RuntimeException("Cet objet ne supporte pas les scripts.");

        String name = method.get(1).contents();
        SNode script = method.get(2);
        ((ScriptableReference) receiver).addScript(name, script);
        return receiver;
    }
}
