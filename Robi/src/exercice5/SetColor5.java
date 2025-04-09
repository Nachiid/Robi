package exercice5;

import graphicLayer.GElement;
import graphicLayer.GSpace;
import stree.parser.SNode;

import java.awt.*;

public class SetColor5 implements Command5 {
    @Override
    public Reference5 run(Reference5 receiver, SNode method) {
        Color color = getColor(method.get(1).contents());
        Object obj = receiver.getReceiver();

        if (obj instanceof GElement) {
            ((GElement) obj).setColor(color);
        } else if (obj instanceof GSpace) {
            ((GSpace) obj).setColor(color);
        } else {
            System.err.println("setColor: le type ne supporte pas setColor()");
        }

        return receiver;
    }

    private Color getColor(String name) {
        try {
            return (Color) Color.class.getField(name.toUpperCase()).get(null);
        } catch (Exception e) {
            return Color.BLACK;
        }
    }
}
