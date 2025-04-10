package exercice5;

import graphicLayer.*;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import stree.parser.SNode;
import stree.parser.SParser;

public class Exercice6 {
    private static Environment5 environment = new Environment5();

    public static Environment5 getEnvironment() {
        return environment;
    }

    public Exercice6() {
        buildEnvironment();
    }

    private void buildEnvironment() {
        GSpace space = new GSpace("Exercice 6", new Dimension(400, 300));
        space.open();

        ScriptableReference spaceRef = new ScriptableReference(space);
        spaceRef.addCommand("setColor", new SetColor5());
        spaceRef.addCommand("add", new AddElement(environment));
        spaceRef.addCommand("setDim", new SetDim5());
        spaceRef.addCommand("sleep", new Sleep5());
        spaceRef.addCommand("addScript", new AddScriptCommand());

        ScriptableReference rectRef = new ScriptableReference(GRect.class);
        rectRef.addCommand("new", new NewElement5());
        rectRef.addCommand("addScript", new AddScriptCommand());

        environment.addReference("space", spaceRef);
        environment.addReference("Rect", rectRef);
    }

    public void oneShot(String script) {
        SParser<SNode> parser = new SParser<>();
        try {
            List<SNode> compiled = parser.parse(script);
            for (SNode node : compiled) {
                Interpreter5.compute(environment, node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Exercice6 exo = new Exercice6();

        String script = """
            (Rect addScript newSquare (
                (self w)
                (^ (self new))
                (self setDim w w)
            ))
            (space add r (Rect newSquare 60))
            (r setColor yellow)
        """;

        exo.oneShot(script);
    }


}
