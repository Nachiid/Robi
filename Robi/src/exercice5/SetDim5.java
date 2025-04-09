package exercice5;

import java.awt.Dimension;
import graphicLayer.*;
import stree.parser.SNode;

public class SetDim5 implements Command5 {
    @Override
    public Reference5 run(Reference5 receiver, SNode method) {
        int width = Integer.parseInt(method.get(1).contents());
        int height = Integer.parseInt(method.get(2).contents());

        Object obj = receiver.getReceiver();

        if (obj instanceof GRect) {
            ((GRect) obj).setDimension(new Dimension(width, height));
        } else if (obj instanceof GOval) {
            ((GOval) obj).setDimension(new Dimension(width, height));
        } else if (obj instanceof GString) {
            ((GString) obj).setDimension(new Dimension(width, height));
        } else if (obj instanceof GSpace) {
        	//set size ? 
            ((GSpace) obj).changeWindowSize(new Dimension(width, height));
        } else {
            System.err.println("setDim: le type ne supporte pas setDimension()");
        }

        return receiver;
    }
}
