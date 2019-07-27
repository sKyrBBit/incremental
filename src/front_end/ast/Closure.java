package front_end.ast;

import back_end.Builder;
import front_end.SymbolList;

import java.util.Iterator;
import java.util.List;

import static front_end.Parser.tab;

public class Closure implements Node {
    private final SymbolList symbols;
    private final List<Statement> statements;
    public Closure(final SymbolList symbols, final List<Statement> statements) {
        this.symbols = symbols;
        this.statements = statements;
    }
    @Override
    public String build() {
        StringBuilder assembly = new StringBuilder();
        assembly.append(Builder.prologue(symbols.symbols.size() * 8));
        statements.forEach(statement -> assembly.append(statement.build()));
        return assembly.toString();
    }
    @Override
    public String toS(int tab) {
        if (statements.isEmpty()) {
            return "(closure)";
        }
        StringBuilder s = new StringBuilder();
        s.append("(closure ");
        tab += 9;
        for(Iterator<Statement> iterator = statements.iterator();;) {
            Statement element = iterator.next();
            s.append(element.toS(tab));
            if (!iterator.hasNext()) {
                break;
            }
            s.append('\n')
                    .append(tab(tab));
        }
        return s.append(')').toString();
    }
}