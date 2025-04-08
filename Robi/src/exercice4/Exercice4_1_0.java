package exercice4;


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
import tools.Tools;

public class Exercice4_1_0 {
    Environment environment = new Environment();
    Dimension lastSize; // ✅ Ajout pour le redimensionnement

    public Exercice4_1_0() {
        GSpace space = new GSpace("Exercice 4", new Dimension(200, 100));
        GRect robi = new GRect();

        space.addElement(robi);
        space.open();

        lastSize = space.getSize(); // ✅ Sauvegarde taille initiale

        // ✅ Gestion du redimensionnement
        space.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                Dimension newSize = space.getSize();
                Point pos = robi.getPosition();
                Dimension dim = robi.getDimension();

                double scaleX = (double) newSize.width / lastSize.width;
                double scaleY = (double) newSize.height / lastSize.height;

                int newWidth = (int) (dim.width * scaleX);
                int newHeight = (int) (dim.height * scaleY);
                int newX = Math.max(0, Math.min((int) (pos.x * scaleX), newSize.width - newWidth));
                int newY = Math.max(0, Math.min((int) (pos.y * scaleY), newSize.height - newHeight));

                final int fx = newX;
                final int fy = newY;
                final int fw = newWidth;
                final int fh = newHeight;

                javax.swing.SwingUtilities.invokeLater(() -> {
                    robi.setPosition(new Point(fx, fy));
                    robi.setDimension(new Dimension(fw, fh));
                });

                lastSize = newSize;
            }
        });

        Reference spaceRef = new Reference(space);
        Reference robiRef = new Reference(robi);

        // Enregistrement des commandes pour space
        spaceRef.primitives.put("setColor", this::spaceSetColor);
        spaceRef.primitives.put("sleep", this::spaceSleep);

        // Enregistrement des commandes pour robi
        robiRef.primitives.put("setColor", this::robiSetColor);
        robiRef.primitives.put("translate", this::robiTranslate);

        environment.addReference("space", spaceRef);
        environment.addReference("robi", robiRef);

        this.mainLoop();
    }

    private Reference spaceSetColor(Reference receiver, SNode method) {
        Color color = getColor(method.get(1).contents());
        ((GSpace) receiver.receiver).setColor(color);
        return receiver;
    }

    private Reference spaceSleep(Reference receiver, SNode method) {
        try {
            Thread.sleep(Integer.parseInt(method.get(1).contents()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return receiver;
    }

    private Reference robiSetColor(Reference receiver, SNode method) {
        Color color = getColor(method.get(1).contents());
        ((GRect) receiver.receiver).setColor(color);
        return receiver;
    }

    private Reference robiTranslate(Reference receiver, SNode method) {
        int dx = Integer.parseInt(method.get(1).contents());
        int dy = Integer.parseInt(method.get(2).contents());
        ((GRect) receiver.receiver).translate(new Point(dx, dy));
        return receiver;
    }

    private Color getColor(String colorName) {
        try {
            return (Color) Color.class.getField(colorName.toUpperCase()).get(null);
        } catch (Exception e) {
            return Color.BLACK;
        }
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
                System.out.println("Erreur de lecture : " + e.getMessage());
                continue;
            }

            Iterator<SNode> itor = compiled.iterator();
            while (itor.hasNext()) {
                try {
                    this.run(itor.next());
                } catch (RuntimeException e) {
                    System.out.println("Erreur : " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Erreur inattendue : " + e.getMessage());
                }
            }
        }
    }
    
    public Command getSetColorCommand() {
        return this::robiSetColor;
    }

    public Command getTranslateCommand() {
        return this::robiTranslate;
    }


    private void run(SNode expr) {
        String receiverName = expr.get(0).contents();
        Reference receiver = environment.getReferenceByName(receiverName);

        SNode commandExpr = new stree.parser.SDefaultNode();
        for (int i = 1; i < expr.size(); i++) {
            commandExpr.addChild(expr.get(i));
        }

        receiver.run(commandExpr);
    }


    public static void main(String[] args) {
        new Exercice4_1_0();
    }
}
