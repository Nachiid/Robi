package exercice4;

import stree.parser.SNode;

public class Sleep implements Command {
    @Override
    public Reference run(Reference receiver, SNode method) {
        try {
            Thread.sleep(Integer.parseInt(method.get(1).contents()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return receiver;
    }
}
