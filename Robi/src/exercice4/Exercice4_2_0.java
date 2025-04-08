package exercice4;


import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import exercice4.commands.SetColor;
import exercice4.commands.Sleep;
import exercice4.commands.AddElement;
import exercice4.commands.DelElement;
import exercice4.commands.NewElement;
import exercice4.commands.NewImage;
import exercice4.commands.NewString;

import exercice4.interpreter.Interpreter;


import graphicLayer.*;
import stree.parser.SDefaultNode;
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
        spaceRef.addCommand("add", new AddElement());
        spaceRef.addCommand("del", new DelElement());

        rectClassRef.addCommand("new", new NewElement());
        ovalClassRef.addCommand("new", new NewElement());
        imageClassRef.addCommand("new", new NewImage());
        stringClassRef.addCommand("new", new NewString());

        environment.addReference("space", spaceRef);
        environment.addReference("rect.class", rectClassRef);
        environment.addReference("oval.class", ovalClassRef);
        environment.addReference("image.class", imageClassRef);
        environment.addReference("label.class", stringClassRef);

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
