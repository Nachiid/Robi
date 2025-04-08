package exercice4;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    Map<String, Reference> variables = new HashMap<>();

    public void addReference(String name, Reference ref) {
        variables.put(name, ref);
    }

    public Reference getReferenceByName(String name) {
        Reference ref = variables.get(name);
        if (ref == null)
            throw new RuntimeException("Référence inconnue : " + name);
        return ref;
    }
}
