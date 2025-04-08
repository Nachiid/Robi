package exercice4;

import java.awt.Color;
import java.lang.reflect.Field;

import graphicLayer.GElement;
import stree.parser.SNode;

public class SetColor implements Command {
    @Override
    public Reference run(Reference receiver, SNode method) {
        Color color = getColor(method.get(1).contents());
        ((GElement) receiver.getReceiver()).setColor(color);
        return receiver;
    }

    private Color getColor(String name) {
        try {
            Field field = Color.class.getField(name.toUpperCase());
            return (Color) field.get(null);
        } catch (Exception e) {
            return Color.BLACK;
        }
    }
}
