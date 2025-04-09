package exercice4;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.*;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;

public class Exercice4_2_0 {
    Environment environment = new Environment();

    public Exercice4_2_0() {
        GSpace space = new GSpace("Exercice 4", new Dimension(200, 100));
        space.open();

        Reference spaceRef = new Reference(space);
        Reference rectClassRef = new Reference(GRect.class);
        Reference ovalClassRef = new Reference(GOval.class);
        Reference imageClassRef = new Reference(GImage.class);
        Reference stringClassRef = new Reference(GString.class);


        spaceRef.addCommand("setColor", new SetColor());
        spaceRef.addCommand("sleep", new Sleep());
        spaceRef.addCommand("add", new AddElement(environment));
        rectClassRef.addCommand("setDim", new SetDim());
        spaceRef.addCommand("del", new DelElement());


        rectClassRef.addCommand("new", new NewElement());
        ovalClassRef.addCommand("new", new NewElement());
        imageClassRef.addCommand("new", new NewImage());
        stringClassRef.addCommand("new", new NewString());

        environment.addReference("space", spaceRef);
        environment.addReference("Rect", rectClassRef);
        environment.addReference("Oval", ovalClassRef);
        environment.addReference("Image", imageClassRef);
        environment.addReference("Label", stringClassRef);

        this.mainLoop();
    }

    private void mainLoop() {
        while (true) {
            System.out.print("> ");
            String input = Tools.readKeyboard();
            SParser<SNode> parser = new SParser<>();
            List<SNode> compiled = null;
            try {
                compiled = parser.parse(input);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Iterator<SNode> itor = compiled.iterator();
            while (itor.hasNext()) {
                try {
                    Interpreter.compute(environment, itor.next());
                } catch (Exception e) {
                    System.out.println("Erreur : " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {


        new Exercice4_2_0();
    }
}



/*(space add shape (Rect new)) (shape translate 100 50) (shape setColor blue) (shape setDim 20 20) (space sleep 1000) (shape setDim 40 40) (space sleep 1000) (shape setDim 60 30) (space sleep 1000) (shape setDim 100 60) (space sleep 1000) (shape setDim 150 80)


(space add robi (Rect new)) (robi translate 130 50) (robi setColor yellow) (robi setDim 80 40) (space sleep 2000) (space add momo (Oval new)) (momo setColor red) (momo translate 80 80) (momo setDim 200 200) (space sleep 2000) (space add pif (Image new resources/pacman.png)) (pif translate 100 0) (space sleep 2000) (space add hello (Label new "Hello world")) (hello translate 10 10) (hello setColor black) 

*/
