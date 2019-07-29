package front_end.ast;

import java.util.Iterator;
import java.util.List;

import static front_end.Parser.tab;

public class FunctionCall implements Expression {
    private final String name;
    private final List<Expression> arguments;
    private static final String[] registers = {"rdi", "rsi", "rdx", "rcx", "r8", "r9"};
    public FunctionCall(final String name, final List<Expression> arguments) {
        this.name = name;
        if (arguments.size() > 4) {
            System.out.println("function, `" + name + "` call has too many arguments");
            System.exit(1);
        }
        this.arguments = arguments;
    }
    @Override
    public String toIR() {
        final StringBuilder ir = new StringBuilder();
        ir.append(name)
                .append('(');
        for(Iterator<Expression> iterator = arguments.iterator();;) {
            Expression element = iterator.next();
            ir.append(element.toIR());
            if (!iterator.hasNext()) {
                break;
            }
            ir.append(", ");
        }
        ir.append(')');
        return ir.toString();
    }
    @Override
    public String build() {
        final StringBuilder assembly = new StringBuilder();
        final int size = arguments.size();
        for (int i = 0; i < size; i++) {
            assembly.append(arguments.get(i).build())
                    .append("  mov  ")
                    .append(registers[i])
                    .append(", rax\n");
        }
        assembly.append("  call ")
                .append(name)
                .append('\n');
        return assembly.toString();
    }

    @Override
    public String toS(int tab) {
        tab += 2 + name.length();
        final StringBuilder builder = new StringBuilder();
        builder.append('(')
                .append(name);
        if (arguments.size() == 0) {
            builder.append(')');
            return builder.toString();
        }
        builder.append(' ');


        for(Iterator<Expression> iterator = arguments.iterator();;) {
            Expression element = iterator.next();
            builder.append(element.toS(tab));
            if (!iterator.hasNext()) {
                break;
            }
            builder.append('\n')
                    .append(tab(tab));
        }
        builder.append(')');
        return builder.toString();
    }
}
