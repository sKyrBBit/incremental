package ast;

import front_end.LocalVariable;
import front_end.SymbolList;
import middle_end.Function;

import java.util.Iterator;

import static front_end.RecursiveDescentParser.tab;
import static middle_end.IRGenerator.*;

public class FunctionDeclare implements Node {
    private final String name;
    private final SymbolList arguments;
    private final Closure closure;
    public FunctionDeclare(final String name, final SymbolList arguments, final Closure closure) {
        this.name = name;
        this.arguments = arguments;
        this.closure = closure;
    }

    public String generate() {
        clear();
        final int before = new_label();
        final int after = new_label();
        emit_label(name);
        emit_label(before);
        closure.generate(before, after);
        emit_label(after);
        return getIR();
    }
    @Override
    public String build() {
        return ".global " + name + "\n" +
                name + ":\n" +
                closure.build();
    }
    public String toS(int tab) {
        tab += 9;
        StringBuilder builder = new StringBuilder();
        builder.append("(declare ")
                .append(name)
                .append(' ')
                .append('[');
        if (arguments.symbols.size() > 0) {
            for (Iterator<LocalVariable> iterator = arguments.symbols.values().iterator(); ; ) {
                LocalVariable element = iterator.next();
                builder.append(element.name);
                if (!iterator.hasNext()) {
                    break;
                }
                builder.append(' ');
            }
        }
        builder.append("]\n")
                .append(tab(tab))
                .append(closure.toS(tab))
                .append(')');
        return builder.toString();
    }
    public Function gen() {
        return new Function(name, closure.symbols.symbols.size(), closure.gen());
    }
    public ir.Function toIR() {
        return new ir.Function(name, closure.toIR());
    }
}
