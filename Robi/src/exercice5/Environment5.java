package exercice5;


import java.util.HashMap;

public class Environment5 {
    private final HashMap<String, Reference5> variables = new HashMap<>();

    public void addReference(String name, Reference5 ref) {
        variables.put(name, ref);
    }

    public Reference5 getReferenceByPath(String path) {
        String[] parts = path.split("\\.");
        Reference5 ref = variables.get(parts[0]);

        if (ref == null) throw new RuntimeException("Référence inconnue : " + parts[0]);

        for (int i = 1; i < parts.length; i++) {
            ref = ref.getChild(parts[i]);
            if (ref == null) throw new RuntimeException("Référence inconnue dans le chemin : " + parts[i]);
        }

        return ref;
    }

    public HashMap<String, Reference5> getAll() {
        return variables;
    }
}
