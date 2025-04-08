package exercice4;

// 
//	(space setColor black)  
//	(robi setColor yellow) 
//	(space sleep 2000) 
//	(space setColor white)  
//	(space sleep 1000) 	
//	(robi setColor red)		  
//	(space sleep 1000)
//	(robi translate 100 0)
//	(space sleep 1000)
//	(robi translate 0 50)
//	(space sleep 1000)
//	(robi translate -100 0)
//	(space sleep 1000)
//	(robi translate 0 -40) ) 
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;

public class Exercice4_1_0 {
	// Une seule variable d'instance
	Environment environment = new Environment();

	public Exercice4_1_0() {
		// space et robi sont temporaires ici
		GSpace space = new GSpace("Exercice 4", new Dimension(200, 100));
		GRect robi = new GRect();

		space.addElement(robi);
		space.open();

		Reference spaceRef = new Reference(space);
		Reference robiRef = new Reference(robi);

		// Initialisation des references : on leur ajoute les primitives qu'elles
		// comprennent
		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("sleep", new Sleep());
		robiRef.addCommand("setColor", new SetColor());
		robiRef.addCommand("translate", new Translate());

		// Enregistrement des references dans l'environnement par leur nom
		environment.addReference("space", spaceRef);
		environment.addReference("robi", robiRef);

		this.mainLoop();
	}

	private void mainLoop() {
		while (true) {
			// prompt
			System.out.print("> ");
			// lecture d'une série de s-expressions au clavier (return = fin de la série)
			String input = Tools.readKeyboard();
			// création du parser
			SParser<SNode> parser = new SParser<>();
			// compilation
			List<SNode> compiled = null;
			try {
				compiled = parser.parse(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// exécution des s-expressions compilées
			Iterator<SNode> itor = compiled.iterator();
			while (itor.hasNext()) {
				this.run((SNode) itor.next());
			}
		}
	}

	private void run(SNode expr) {
		// quel est le nom du receiver
		String receiverName = expr.get(0).contents();
		// quel est le receiver
		Reference receiver = environment.getReferenceByName(receiverName);
		// demande au receiver d'exécuter la s-expression compilée
		receiver.run(expr);
	}

	public static void main(String[] args) {
		new Exercice4_1_0();
	}

}

// Classes complémentaires

class Environment {
    private Map<String, Reference> references = new HashMap<>();
    
    public void addReference(String name, Reference ref) {
        references.put(name, ref);
    }
    
    public Reference getReferenceByName(String name) {
        return references.get(name);
    }
}

class Reference {
    private Object receiver;
    private Map<String, Command> commands = new HashMap<>();
    
    public Reference(Object receiver) {
        this.receiver = receiver;
    }
    
    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }
    
    public void run(SNode expr) {
        String commandName = expr.get(1).contents();
        Command command = commands.get(commandName);
        if (command != null) {
            command.run(receiver, expr);
        } else {
            System.out.println("Unknown command: " + commandName);
        }
    }
}

interface Command {
    void run(Object receiver, SNode expr);
}

class SetColor implements Command {
    @Override
    public void run(Object receiver, SNode expr) {
        String colorName = expr.get(2).contents();
        Color color = getColorByName(colorName);
        if (receiver instanceof GSpace) {
            ((GSpace) receiver).setColor(color);
        } else if (receiver instanceof GRect) {
            ((GRect) receiver).setColor(color);
        }
    }

    private Color getColorByName(String name) {
        try {
            return (Color) Color.class.getField(name.toUpperCase()).get(null);
        } catch (Exception e) {
            return Color.BLACK; // Default color
        }
    }
}

class Sleep implements Command {
    @Override
    public void run(Object receiver, SNode expr) {
        int delay = Integer.parseInt(expr.get(2).contents());
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Translate implements Command {
    @Override
    public void run(Object receiver, SNode expr) {
        int x = Integer.parseInt(expr.get(2).contents());
        int y = Integer.parseInt(expr.get(3).contents());
        if (receiver instanceof GRect) {
            GRect rect = (GRect) receiver;
            rect.setPosition(new Point(rect.getPosition().x + x, rect.getPosition().y + y));
        }
    }
}
