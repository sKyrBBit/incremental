package ast;

import front_end.LocalVariable;
import ir.Operand;
import ir.Register;

public class VariableCall extends Expression {
    private final LocalVariable local;
    public VariableCall(final LocalVariable local) {
        super(local.toString());
        this.local = local;
    }
    @Override
    public String build() {
        return "  mov  rax, rbp\n" +
                "  sub  rax, " +
                local.offset +
                "\n" +
                "  push [rax]\n";
    }
    @Override
    public String toS(int tab) {
        return local.name;
    }
    @Override
    public Operand toIR() {
        return new Register(toString());
    }
}
