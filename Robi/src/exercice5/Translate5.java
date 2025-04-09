package exercice5;

import graphicLayer.GElement;
import stree.parser.SNode;
import java.awt.Point;

public class Translate5 implements Command5 {
    public Reference5 run(Reference5 receiver, SNode method) {
        int dx = Integer.parseInt(method.get(1).contents());
        int dy = Integer.parseInt(method.get(2).contents());
        ((GElement) receiver.getReceiver()).translate(new Point(dx, dy));
        
        //System.out.println("→ Translation de " + receiver.getReceiver() + " vers (" + dx + ", " + dy + ")");

        return receiver;
    }
}