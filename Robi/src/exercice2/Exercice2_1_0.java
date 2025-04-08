package exercice2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import javax.swing.SwingUtilities;
import stree.parser.SNode;
import stree.parser.SParser;

public class Exercice2_1_0 {
    GSpace space = new GSpace("Exercice 2_1", new Dimension(400, 400));
    GRect robi = new GRect();
    String script = "(space color white)"
    		+ "(robi color red)"
    		+ "(robi translate 10 0)"
    		+ "(space sleep 100)\n"
    		+ "(robi translate 0 10)"
    		+ "(space sleep 100)"
    		+ "(robi translate -10 0)"
    		+ "(space sleep 100)"
    		+ "(robi translate 0 -10)";
    
    private Dimension lastSize;

    public Exercice2_1_0() {
        space.addElement(robi);
        robi.setPosition(new Point(0, 0));
        robi.setDimension(new Dimension(50, 50));
        space.open();
        lastSize = space.getSize();

        // Ajout du gestionnaire de redimensionnement
        space.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = space.getSize();
                Point pos = robi.getPosition();
                Dimension dim = robi.getDimension();

                double scaleX = (double) newSize.width / lastSize.width;
                double scaleY = (double) newSize.height / lastSize.height;

                int newWidth = (int)(dim.width * scaleX);
                int newHeight = (int)(dim.height * scaleY);
                int newX = Math.max(0, Math.min((int)(pos.x * scaleX), newSize.width - newWidth));
                int newY = Math.max(0, Math.min((int)(pos.y * scaleY), newSize.height - newHeight));

                SwingUtilities.invokeLater(() -> {
                    robi.setPosition(new Point(newX, newY));
                    robi.setDimension(new Dimension(newWidth, newHeight));
                    checkBoundaries();
                });

                lastSize = newSize;
            }
        });

        new Thread(() -> this.runScript()).start();
    }

    private void checkBoundaries() {
        SwingUtilities.invokeLater(() -> {
            Point pos = robi.getPosition();
            Dimension dim = robi.getDimension();
            int spaceWidth = space.getWidth();
            int spaceHeight = space.getHeight();

            if (pos.x < 0) pos.x = 0;
            else if (pos.x + dim.width > spaceWidth) 
                pos.x = spaceWidth - dim.width;

            if (pos.y < 0) pos.y = 0;
            else if (pos.y + dim.height > spaceHeight) 
                pos.y = spaceHeight - dim.height;

            robi.setPosition(pos);
        });
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
        SwingUtilities.invokeLater(() -> {
            robi.translate(new Point(dx, dy));
            checkBoundaries(); // Vérification après déplacement
        });
    }

    private void handleSleep(String target, List<SNode> children) throws InterruptedException {
        if (!"space".equals(target)) {
            System.err.println("Erreur: 'sleep' ne peut être appliqué qu'à 'space'");
            return;
        }
        if (children.size() < 3) return;
        int duration = Integer.parseInt(children.get(2).contents());
        Thread.sleep(duration);
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