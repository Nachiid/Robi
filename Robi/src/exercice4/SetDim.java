package exercice4;

import java.awt.Dimension;

import graphicLayer.*;
import stree.parser.SNode;

public class SetDim implements Command {
    @Override
    public Reference run(Reference receiver, SNode method) {
        int width = Integer.parseInt(method.get(1).contents());
        int height = Integer.parseInt(method.get(2).contents());

        Object obj = receiver.getReceiver();

        if (obj instanceof GRect) {
            ((GRect) obj).setDimension(new Dimension(width, height));
        } else if (obj instanceof GOval) {
            ((GOval) obj).setDimension(new Dimension(width, height));
        } else if (obj instanceof GString) {
            ((GString) obj).setDimension(new Dimension(width, height));
        } else {
            System.err.println("setDim: le type ne supporte pas setDimension()");
        }

        return receiver;
    }
}
