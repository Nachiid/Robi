package serveur;


import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SParser;
import stree.parser.SNode;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;

public class ScriptExecutor {
    static GSpace space;
    static GRect robi;

    static {
        space = new GSpace("Robi Serveur", new Dimension(300, 200));
        robi = new GRect();
        space.addElement(robi);
        space.open();
    }

    public static String executeScript(String script) {
        SParser<SNode> parser = new SParser<>();
        List<SNode> rootNodes;
        try {
            rootNodes = parser.parse(script);
        } catch (IOException e) {
            return "{\"status\":\"error\",\"message\":\"Erreur parsing: " + e.getMessage() + "\"}";
        }

        Iterator<SNode> itor = rootNodes.iterator();
        while (itor.hasNext()) {
            run(itor.next());
        }

        return "{\"status\":\"ok\",\"message\":\"Script exécuté\"}";
    }

    private static void run(SNode expr) {
        String target = expr.get(0).contents();
        String cmd = expr.get(1).contents();

        if (target.equals("space")) {
            if (cmd.equals("setColor") || cmd.equals("color")) {
                Color color = Color.decodeOrName(expr.get(2).contents());
                space.setColor(color);
                space.repaint();
            } else if (cmd.equals("sleep")) {
                int duration = Integer.parseInt(expr.get(2).contents());
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException ignored) {}
            }
        } else if (target.equals("robi")) {
            if (cmd.equals("setColor") || cmd.equals("color")) {
                Color color = Color.decodeOrName(expr.get(2).contents());
                robi.setColor(color);
                space.repaint();
            } else if (cmd.equals("translate")) {
                int dx = Integer.parseInt(expr.get(2).contents());
                int dy = Integer.parseInt(expr.get(3).contents());
                robi.translate(dx, dy);
                space.repaint();
            }
        }
    }

    // Petite méthode utilitaire
    private static class Color {
        public static java.awt.Color decodeOrName(String name) {
            try {
                return java.awt.Color.decode(name);
            } catch (NumberFormatException e) {
                try {
                    return (java.awt.Color) java.awt.Color.class.getField(name.toLowerCase()).get(null);
                } catch (Exception ex) {
                    return java.awt.Color.BLACK;
                }
            }
        }
    }
}