package exercice4;

import stree.parser.SDefaultNode;
import stree.parser.SNode;

public class Interpreter {
    public static Reference compute(Environment env, SNode expr) {
        String receiverName = expr.get(0).contents();
        Reference receiver = env.getReferenceByName(receiverName);
        SDefaultNode commandExpr = new SDefaultNode();
        for (int i = 1; i < expr.size(); i++) {
            commandExpr.addChild(expr.get(i));
        }
        return receiver.run(commandExpr);
    }
}
