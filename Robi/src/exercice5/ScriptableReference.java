package exercice5;

import stree.parser.SNode;

import java.util.List;

public class ScriptableReference extends Reference5 {
    private final ScriptManager scriptManager = new ScriptManager();

    public ScriptableReference(Object receiver) {
        super(receiver);
    }

    public void addScript(String name, SNode script) {
        scriptManager.addScript(name, script);
    }

    public boolean hasScript(String name) {
        return scriptManager.hasScript(name);
    }

    public SNode getScript(String name) {
        return scriptManager.getScript(name);
    }

    @Override
    public Reference5 run(SNode expr) {
        List<SNode> subExpr = expr.children();
        if (subExpr.isEmpty()) throw new RuntimeException("Commande vide");

        String commandName = subExpr.get(0).contents();
        Command5 cmd = this.getCommand(commandName);

        if (cmd != null)
            return cmd.run(this, expr);

        // 🔁 Sinon, exécute un script enregistré
        if (this.hasScript(commandName))
            return ScriptInterpreter.execute(this, this.getScript(commandName), expr);

        throw new RuntimeException("Commande ou script inconnu : " + commandName);
    }
}
