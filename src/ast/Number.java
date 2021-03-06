package ast;

public class Number extends Expression {
    final int value;
    public Number(final int value) {
        super(Integer.toString(value));
        this.value = value;
    }
    @Override
    public void jumping(final int _true,  final int _false) {}
    @Override
    public String build() {
        return "  push " + value + "\n";
    }
    @Override
    public String toS(int tab) {
        return "" + value;
    }
    @Override
    public ir.Operand toIR() {
        return new ir.Immediate(value);
    }
}
