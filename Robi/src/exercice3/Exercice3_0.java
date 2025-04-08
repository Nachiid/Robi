package exercice3;

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

public class Exercice3_0 {
    GSpace space = new GSpace("Exercice 3", new Dimension(400, 400));
    GRect robi = new GRect();
    String script = "" +
        "(space setColor black) " +
        "(robi setColor yellow)" +
        "(space sleep 1000)" +
        "(space setColor white)" +
        "(space sleep 1000)" +
        "(robi setColor red)" +
        "(space sleep 1000)" +
        "(robi translate 100 0)" +
        "(space sleep 1000)" +
        "(robi translate 0 50)" +
        "(space sleep 1000)" +
        "(robi translate -100 0)" +
        "(space sleep 1000)" +
        "(robi translate 0 -40)";
    
    private Dimension lastSize;

    public Exercice3_0() {
        space.addElement(robi);
        robi.setPosition(new Point(0, 0));
        robi.setDimension(new Dimension(50, 50));
        space.open();
        lastSize = space.getSize();

        // Gestion du redimensionnement
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

        new Thread(this::runScript).start();
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
        Command cmd = getCommandFromExpr(expr);
        if (cmd == null)
            throw new Error("Commande non reconnue : " + expr);
        cmd.run();
    }

    private Command getCommandFromExpr(SNode expr) {
        List<SNode> nodes = expr.children();
        if (nodes.size() < 2) return null;
        
        String cible = nodes.get(0).contents();
        String commande = nodes.get(1).contents();

        try {
            if (cible.equals("space")) {
                return handleEspace(commande, nodes);
            } else if (cible.equals("robi")) {
                return handleRobi(commande, nodes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Command handleEspace(String commande, List<SNode> nodes) {
        switch(commande) {
            case "setColor": 
                return new ChangementCouleurEspace(getCouleur(nodes.get(2).contents()));
            case "sleep": 
                return new PauseEspace(Integer.parseInt(nodes.get(2).contents()));
        }
        return null;
    }

    private Command handleRobi(String commande, List<SNode> nodes) {
        switch(commande) {
            case "setColor": 
                return new ChangementCouleurRobi(getCouleur(nodes.get(2).contents()));
            case "translate": 
                return new DeplacementRobi(
                    Integer.parseInt(nodes.get(2).contents()), 
                    Integer.parseInt(nodes.get(3).contents()));
        }
        return null;
    }

    private Color getCouleur(String nomCouleur) {
        try {
            return (Color) Color.class.getField(nomCouleur.toUpperCase()).get(null);
        } catch (Exception e) {
            return Color.BLACK;
        }
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

    public interface Command {
        void run();
    }

    class ChangementCouleurEspace implements Command {
        private Color couleur;
        
        public ChangementCouleurEspace(Color c) { 
            this.couleur = c; 
        }
        
        @Override
        public void run() { 
            SwingUtilities.invokeLater(() -> space.setColor(couleur));
        }
    }

    class PauseEspace implements Command {
        private int duree;
        
        public PauseEspace(int d) { 
            this.duree = d; 
        }
        
        @Override
        public void run() { 
            try { Thread.sleep(duree); } 
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    class ChangementCouleurRobi implements Command {
        private Color couleur;
        
        public ChangementCouleurRobi(Color c) { 
            this.couleur = c; 
        }
        
        @Override
        public void run() { 
            SwingUtilities.invokeLater(() -> robi.setColor(couleur));
        }
    }

    class DeplacementRobi implements Command {
        private Point deplacement;
        
        public DeplacementRobi(int dx, int dy) { 
            this.deplacement = new Point(dx, dy); 
        }
        
        @Override
        public void run() { 
            SwingUtilities.invokeLater(() -> {
                robi.translate(deplacement);
                checkBoundaries();
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Exercice3_0());
    }
}