package exercice5;

import stree.parser.SNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reference5 {
    private Object receiver;
    private Map<String, Command5> primitives = new HashMap<>();
    private Map<String, Reference5> children = new HashMap<>();

    public Reference5(Object receiver) {
        this.receiver = receiver;
    }
    
    public Command5 getCommand(String name) {
        return primitives.get(name);
    }


    public Object getReceiver() {
        return receiver;
    }

    public void addCommand(String name, Command5 cmd) {
        primitives.put(name, cmd);
    }

    public void addChild(String name, Reference5 child) {
        children.put(name, child);
    }

    public Reference5 getChild(String name) {
        return children.get(name);
    }

    public Reference5 run(SNode expr) {
        List<SNode> subExpr = expr.children();
        if (subExpr.size() < 1) {
            throw new RuntimeException("Commande vide : " + expr);
        }

        String commandName = subExpr.get(0).contents();
        Command5 cmd = primitives.get(commandName);

        if (cmd == null) {
            throw new RuntimeException("Commande inconnue : " + commandName);
        }

        return cmd.run(this, expr);
    }
    
    public void removeChild(String name) {
        children.remove(name);
    }


}