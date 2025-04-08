package exercice4;

import graphicLayer.GElement;
import stree.parser.SNode;
import java.awt.Point;

public class Translate implements Command {
    @Override
    public Reference run(Reference receiver, SNode method) {
        int dx = Integer.parseInt(method.get(1).contents());
        int dy = Integer.parseInt(method.get(2).contents());
        ((GElement) receiver.getReceiver()).translate(new Point(dx, dy));
        return receiver;
    }
}
