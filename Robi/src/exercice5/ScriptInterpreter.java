package exercice5;

import stree.parser.SNode;

import java.util.List;

public class ScriptInterpreter {
    public static Reference5 execute(ScriptableReference self, SNode script, SNode callExpr) {
        List<SNode> paramList = script.get(0).children();
        List<SNode> body = script.children().subList(1, script.size());

        Environment5 localEnv = new Environment5();
        localEnv.addReference(paramList.get(0).contents(), self); // "self"

        for (int i = 1; i < paramList.size(); i++) {
            String param = paramList.get(i).contents();
            SNode argExpr = callExpr.get(i);
            Reference5 arg = Interpreter5.compute(Exercice6.getEnvironment(), argExpr);
            localEnv.addReference(param, arg);
        }

        for (SNode stmt : body) {
            // Gestion spéciale du return : (^ ...)
            if (stmt.get(0).contents().equals("^")) {
                if (stmt.size() < 2)
                    throw new RuntimeException("Instruction return (^) sans expression");

                return Interpreter5.compute(localEnv, stmt.get(1));
            }

            Interpreter5.compute(localEnv, stmt);
        }

        return self; // Valeur par défaut si pas de ^
    }
}
