package exercice5;

import java.util.HashMap;
import java.util.Map;
import stree.parser.SNode;

public class ScriptManager {
    private final Map<String, SNode> scripts = new HashMap<>();

    public void addScript(String name, SNode script) {
        scripts.put(name, script);
    }

    public SNode getScript(String name) {
        return scripts.get(name);
    }

    public boolean hasScript(String name) {
        return scripts.containsKey(name);
    }
}
