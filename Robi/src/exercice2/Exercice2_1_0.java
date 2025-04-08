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
	String script = """
		    (space color white)
		    (robi color red)
		    (robi translate 10 0)
		    (space sleep 1000)
		    (robi translate 0 10)
		    (space sleep 1000)
		    (robi translate -10 0)
		    (space sleep 1000)
		    (robi translate 0 -10)
		    """;


	public Exercice2_1_0() {
		space.addElement(robi);
		space.open();
		addResizeListener();
		new Thread(() -> runScript()).start();
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
	    String target = expr.get(0).contents();
	    String command = expr.get(1).contents();

	    if (target.equals("space")) {
	        if (command.equals("color")) {
	            String colorName = expr.get(2).contents();
	            Color color = getColorByName(colorName);
	            space.setColor(color);
	        } else if (command.equals("sleep")) {
	            int delai = Integer.parseInt(expr.get(2).contents());
	            try {
	                Thread.sleep(delai);
	            } catch (Exception e) {
	                System.out.println("Erreur dans sleep : " + e.getMessage());
	            }
	        }
	    } else if (target.equals("robi")) {
	        if (command.equals("color")) {
	            String colorName = expr.get(2).contents();
	            Color color = getColorByName(colorName);
	            robi.setColor(color);
	        } else if (command.equals("translate")) {
	            int x = Integer.parseInt(expr.get(2).contents());
	            int y = Integer.parseInt(expr.get(3).contents());
	            Point initial = robi.getPosition();
	            robi.setPosition(new Point(initial.x + x, initial.y + y));
	        }
	    }
	}


	 private Color getColorByName(String name) {
	        try {
	            return (Color) Color.class.getField(name.toUpperCase()).get(null);
	        } catch (Exception e) {
	            return null;
	        }
	    }
	 
	 
	 private void addResizeListener() {
			space.addComponentListener(new java.awt.event.ComponentAdapter() {
				@Override
				public void componentResized(java.awt.event.ComponentEvent e) {
					Dimension size = space.getSize();
					Point center = new Point(size.width / 2, size.height / 2);
					robi.setPosition(center);
					System.out.println("Fenêtre redimensionnée. Robi centré en : " + center);
				}
			});
		}


	 

	public static void main(String[] args) {
		new Exercice2_1_0();
		

	}

}