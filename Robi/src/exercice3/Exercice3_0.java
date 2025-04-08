 package exercice3;

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

public class Exercice3_0 {
	GSpace space = new GSpace("Exercice 3", new Dimension(200, 100));
	GRect robi = new GRect();
	String script = "" +
	"   (space setColor black) " +
	"   (robi setColor yellow)" +
	"   (space sleep 1000)" +
	"   (space setColor white)\n" + 
	"   (space sleep 1000)" +
	"	(robi setColor red) \n" + 
	"   (space sleep 1000)" +
	"	(robi translate 100 0)\n" + 
	"	(space sleep 1000)\n" + 
	"	(robi translate 0 50)\n" + 
	"	(space sleep 1000)\n" + 
	"	(robi translate -100 0)\n" + 
	"	(space sleep 1000)\n" + 
	"	(robi translate 0 -40)";

	public Exercice3_0() {
		space.addElement(robi);
		space.open();
		this.runScript();
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
			throw new Error("unable to get command for: " + expr);
		cmd.run();
	}

	Command getCommandFromExpr(SNode expr) {
        String target = expr.get(0).contents(); // Récupère le nom de l'objet cible
        String command = expr.get(1).contents(); // Récupère la commande à exécuter
        
        if (target.equals("space")) {
            if (command.equals("setColor")) {
                String colorName = expr.get(2).contents();
                Color color = getColorByName(colorName);
                return new SpaceChangeColor(color);
            } else if (command.equals("sleep")) {
                int delay = Integer.parseInt(expr.get(2).contents());
                return new SpaceSleep(delay);
            }
        } else if (target.equals("robi")) {
            if (command.equals("setColor")) {
                String colorName = expr.get(2).contents();
                Color color = getColorByName(colorName);
                return new RobiChangeColor(color);
            } else if (command.equals("translate")) {
                int x = Integer.parseInt(expr.get(2).contents());
                int y = Integer.parseInt(expr.get(3).contents());
                return new RobiTranslate(x, y);
            }
        }
        return null; // retourne null si aucune commande ne correspond
    }
	public class RobiChangeColor implements Command {
        Color newColor;

        public RobiChangeColor(Color newColor) {
            this.newColor = newColor;
        }

        @Override
        public void run() {
            robi.setColor(newColor);
        }
    }
	 public class RobiTranslate implements Command {
	        int x, y;

	        public RobiTranslate(int x, int y) {
	            this.x = x;
	            this.y = y;
	        }

	        @Override
	        public void run() {
	            Point currentPosition = robi.getPosition();
	            robi.setPosition(new Point(currentPosition.x + x, currentPosition.y + y));
	        }
	    }
	private Color getColorByName(String name) {
        try {
            return (Color) Color.class.getField(name.toUpperCase()).get(null);
        } catch (Exception e) {
            System.out.println("Unknown color: " + name + ". Defaulting to BLACK.");
            return null;
        }
    }

	public static void main(String[] args) {
		new Exercice3_0();
	}

	public interface Command {
		abstract public void run();
	}

	public class SpaceChangeColor implements Command {
		Color newColor;

		public SpaceChangeColor(Color newColor) {
			this.newColor = newColor;
		}

		@Override
		public void run() {
			space.setColor(newColor);
		}

	}
	public class SpaceSleep implements Command {
        int delay;

        public SpaceSleep(int delay) {
            this.delay = delay;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}