package exercice4;

import stree.parser.SNode;

public interface Command {
    Reference run(Reference receiver, SNode method);
}
