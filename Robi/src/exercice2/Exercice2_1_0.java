package exercice2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;

public class Exercice2_1_0 {
    GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100));
    GRect robi = new GRect();

    //script
    String script = """
        (space setColor white)
        (robi setColor red)
        (robi translate 10 0)
        (space sleep 100)
        (robi translate 0 10)
        (space sleep 100)
        (robi translate -10 0)
        (space sleep 100)
        (robi translate 0 -10)
    """;

    public Exercice2_1_0() {
        space.addElement(robi);
        robi.setPosition(new Point(50, 50)); // Position initiale
        space.open();
        this.runScript();
    }

    private void runScript() {
        SParser<SNode> parser = new SParser<>();
        List<SNode> rootNodes = null;

        try {
            rootNodes = parser.parse(script);  // Parse les S-expressions
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator<SNode> itor = rootNodes.iterator();
        while (itor.hasNext()) {
            this.run(itor.next());  // Interprétation de chaque commande
        }
    }

    private void run(SNode expr) {
        if (expr.isLeaf() || expr.size() < 2)
            return; // invalide

        String target = expr.get(0).contents();
        String action = expr.get(1).contents();

        // Commande: setColor (space ou robi)
        if (action.equals("setColor") && expr.size() == 3) {
            Color color = getColorByName(expr.get(2).contents());
            if (color == null) {
                System.out.println("Couleur non reconnue : " + expr.get(2).contents());
                return;
            }

            if (target.equals("space"))
                space.setColor(color);
            else if (target.equals("robi"))
                robi.setColor(color);

        // Commande: translate (robi uniquement)
        } else if (action.equals("translate") && expr.size() == 4 && target.equals("robi")) {
            try {
                int dx = Integer.parseInt(expr.get(2).contents());
                int dy = Integer.parseInt(expr.get(3).contents());
                Point p = robi.getPosition();
                robi.setPosition(new Point(p.x + dx, p.y + dy));
            } catch (NumberFormatException e) {
                System.out.println("Erreur : paramètres de déplacement invalides.");
            }

        // Commande: sleep (target = "space" uniquement)
        } else if (action.equals("sleep") && expr.size() == 3 && target.equals("space")) {
            try {
                int delay = Integer.parseInt(expr.get(2).contents());
                Thread.sleep(delay);
            } catch (Exception e) {
                System.out.println("Erreur dans sleep : " + e.getMessage());
            }

        } else {
            System.out.println("Commande inconnue ou mauvaise syntaxe : " + expr);
        }
    }

    // Conversion string → Color
    private Color getColorByName(String name) {
        try {
            return (Color) Color.class.getField(name.toUpperCase()).get(null);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        new Exercice2_1_0();
    }
}
