package middle_end;

import ast.FunctionDeclare;

import java.util.List;
import java.util.stream.Collectors;

public class Builder {
    private static final StringBuilder assembly = new StringBuilder();
    public static void clear() {
        assembly.delete(0, assembly.length());
    }
    static void append(final String message) {
        assembly.append(message);
    }
    public static String build() {
        return assembly.toString();
    }
    public static Module generate(List<FunctionDeclare> trees) {
        return new Module(trees.stream()
                .map(FunctionDeclare::gen)
                .collect(Collectors.toList()));
    }
}
