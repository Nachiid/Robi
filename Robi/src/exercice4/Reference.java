package exercice4;

import java.util.HashMap;
import java.util.Map;
import stree.parser.SNode;

public class Reference {
    Object receiver;
    Map<String, Command> primitives = new HashMap<>();

    public Reference(Object receiver) {
        this.receiver = receiver;
    }

    public void addCommand(String name, Command cmd) {
        primitives.put(name, cmd);
    }

    public Reference run(SNode expr) {
        if (expr.children().size() < 1)
            throw new RuntimeException("Commande vide");
        String cmdName = expr.get(0).contents();
        Command cmd = primitives.get(cmdName);
        if (cmd == null)
            throw new RuntimeException("Commande inconnue : " + cmdName);
        return cmd.run(this, expr);
    }

    public Object getReceiver() {
        return receiver;
    }
}
