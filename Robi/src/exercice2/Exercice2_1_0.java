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
import javax.swing.SwingUtilities;

public class Exercice2_1_0 {
    GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100));
    GRect robi = new GRect();
    String script = "(space color white) (robi color red) (robi translate 10 0) (space sleep 100)"
            + " (robi translate 0 10) (space sleep 100) (robi translate -10 0) (space sleep 100)"
            + " (robi translate 0 -10) ";

    public Exercice2_1_0() {
        space.addElement(robi);
        space.open();
        // Exécution du script dans un thread séparé
        new Thread(() -> this.runScript()).start();
    }

    private void runScript() {
        SParser<SNode> parser = new SParser<>();
        List<SNode> rootNodes = null;
        try {
            rootNodes = parser.parse(script);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<SNode> itor = rootNodes.iterator();
        while (itor.hasNext()) {
            this.run(itor.next());
        }
    }

    private void run(SNode expr) {
        List<SNode> children = expr.children();
        if (children.size() < 2) return;

        String target = children.get(0).contents();
        String command = children.get(1).contents();

        try {
            switch (command) {
                case "color":
                    handleColor(target, children);
                    break;
                case "translate":
                    handleTranslate(target, children);
                    break;
                case "sleep":
                    handleSleep(target, children);
                    break;
                default:
                    System.err.println("Commande inconnue: " + command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleColor(String target, List<SNode> children) {
        if (children.size() < 3) return;
        String colorName = children.get(2).contents();
        Color color = getColorFromName(colorName);
        // Mise à jour thread-safe de la couleur
        SwingUtilities.invokeLater(() -> {
            if ("space".equals(target)) {
                space.setColor(color);
            } else if ("robi".equals(target)) {
                robi.setColor(color);
            }
        });
    }

    private void handleTranslate(String target, List<SNode> children) {
        if (children.size() < 4 || !"robi".equals(target)) return;
        int dx = Integer.parseInt(children.get(2).contents());
        int dy = Integer.parseInt(children.get(3).contents());
        // Mise à jour thread-safe de la position
        SwingUtilities.invokeLater(() -> {
            robi.translate(new Point(dx, dy));
        });
    }

    private void handleSleep(String target, List<SNode> children) throws InterruptedException {
        if (!"space".equals(target)) {
            System.err.println("Erreur: 'sleep' ne peut être appliqué qu'à 'space'");
            return;
        }
        if (children.size() < 3) return;
        int duration = Integer.parseInt(children.get(2).contents());
        Thread.sleep(duration); // Bloque le thread secondaire
    }

    private Color getColorFromName(String name) {
        try {
            return (Color) Color.class.getField(name.toUpperCase()).get(null);
        } catch (Exception e) {
            return Color.BLACK;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Exercice2_1_0());
    }
}