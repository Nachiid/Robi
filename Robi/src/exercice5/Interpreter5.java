package exercice5;

import stree.parser.SDefaultNode;
import stree.parser.SNode;

public class Interpreter5 {
    public static Reference5 compute(Environment5 env, SNode expr) {
    	
        if (expr.size() == 0) {
            System.err.println("Expression vide détectée !");
            System.err.println("Expr contenu : " + expr);
            throw new RuntimeException("Expression vide !");
        }
        
        String receiverName = expr.get(0).contents();
        Reference5 receiver = env.getReferenceByPath(receiverName);

        SDefaultNode commandExpr = new SDefaultNode();
        for (int i = 1; i < expr.size(); i++) {
            commandExpr.addChild(expr.get(i));
        }

        return receiver.run(commandExpr);
    }
}

