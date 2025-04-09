package exercice5;

import graphicLayer.*;

import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import stree.parser.SNode;
import stree.parser.SParser;

public class Exercice5 {
	public static Environment5 environment = new Environment5();

    public Exercice5() {
        buildEnvironment();
        runScript();
    }

    public static Environment5 getEnvironment() {
        return environment;
    }

    private void buildEnvironment() {
        GSpace space = new GSpace("Exercice 5", new Dimension(400, 300));
        space.open();

        Reference5 spaceRef = new Reference5(space);
        spaceRef.addCommand("setColor", new SetColor5());
        spaceRef.addCommand("add", new AddElement(environment));
        spaceRef.addCommand("setDim", new SetDim5());
        spaceRef.addCommand("sleep", new Sleep5());

        Reference5 rectRef = new Reference5(GRect.class);
        rectRef.addCommand("new", new NewElement5());

        Reference5 imageRef = new Reference5(GImage.class);
        imageRef.addCommand("new", new NewImage5());

        environment.addReference("space", spaceRef);
        environment.addReference("Rect", rectRef);
        environment.addReference("Image", imageRef);
    }

    private void runScript() {
        String script = """
    (space setDim 150 120)
    (space add robi (Rect new))
    (space.robi setColor white)
    (space.robi setDim 100 100)
    (space.robi translate 20 10)
    (space.robi add alien (Image new alien.gif))
    (space.robi.alien translate 20 10)
    """;

        SParser<SNode> parser = new SParser<>();
        try {
            List<SNode> compiled = parser.parse(script);
            Iterator<SNode> it = compiled.iterator();
            while (it.hasNext()) {
                Interpreter5.compute(environment, it.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Exercice5();
    }
}




/*
        String script = """
    (space setDim 150 120)
    (space sleep 2000)
    (space add robi (Rect new))
    (space sleep 2000)
    (space.robi setColor white)
    (space sleep 2000)
    (space.robi setDim 100 100)
    (space sleep 2000)
    (space.robi translate 20 10)
    (space sleep 2000)
    (space.robi add alien (Image new alien.gif))
    (space sleep 2000)
    (space.robi.alien translate 20 10)
    """;
*/