package exercice5;

import stree.parser.SNode;

public class Sleep5 implements Command5 {
    @Override
    public Reference5 run(Reference5 receiver, SNode method) {
        try {
            int duration = Integer.parseInt(method.get(1).contents());
            Thread.sleep(duration);
        } catch (Exception e) {
            System.err.println("Erreur dans Sleep : " + e.getMessage());
        }
        return receiver;
    }
}