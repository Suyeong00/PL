import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildAstVisitor extends ExprBaseVisitor<AstNodes> {
    private Map<String, Double> vars;
    private List<AstNodes> astNodesList;

    public BuildAstVisitor() {
        vars = new HashMap<>();
        astNodesList = new ArrayList<>();
    }

    public List<AstNodes> getAstNodesList() {
        return astNodesList;
    }
    @Override
    public AstNodes visitInstruction(ExprParser.InstructionContext ctx) {
        AstNodes astNodes = visit(ctx.getChild(0));
        astNodesList.add(astNodes);
        return super.visitInstruction(ctx);
    }

    @Override
    public AstNodes visitParenthesis(ExprParser.ParenthesisContext ctx) {
        return visit(ctx.getChild(1));
    }

    @Override
    public AstNodes visitVariable(ExprParser.VariableContext ctx) {
        String varName = ctx.getChild(0).getText();
        double num = Integer.MIN_VALUE;
        if(vars.containsKey(varName)) num = vars.get(varName);
        return new AstNodes(num, varName);
    }

    @Override
    public AstNodes visitNumber(ExprParser.NumberContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public AstNodes visitOperation(ExprParser.OperationContext ctx) {
        AstNodes left = visit(ctx.getChild(0));
        AstNodes right = visit(ctx.getChild(2));
        String operator = ctx.getChild(1).getText();
        switch(operator) {
            case "*": return new AstNodes(left, right, "MUL");
            case "/": return new AstNodes(left, right, "DIV");
            case "+": return new AstNodes(left, right, "ADD");
            case "-": return new AstNodes(left, right, "SUB");
            default: return super.visitOperation(ctx);
        }
    }

    @Override
    public AstNodes visitIntDeclaration(ExprParser.IntDeclarationContext ctx) {
        String varName = ctx.getChild(0).getText();
        String numText = ctx.getChild(2).getText();
        Double num = Double.parseDouble(numText);

        if(vars.containsKey(varName)) vars.replace(varName, num);
        else vars.put(varName, num);

        return new AstNodes("ASSIGN", varName, num);
    }

    @Override
    public AstNodes visitRealDeclaration(ExprParser.RealDeclarationContext ctx) {
        String varName = ctx.getChild(0).getText();
        String numText = ctx.getChild(2).getText();
        Double num = Double.parseDouble(numText);

        if(vars.containsKey(varName)) vars.replace(varName, num);
        else vars.put(varName, num);

        return new AstNodes("ASSIGN", varName, num);
    }

    @Override
    public AstNodes visitInteger(ExprParser.IntegerContext ctx) {
        String numText = ctx.getChild(0).getText();
        Double num = Double.parseDouble(numText);
        return new AstNodes(num);
    }

    @Override
    public AstNodes visitReal(ExprParser.RealContext ctx) {
        String numText = ctx.getChild(0).getText();
        Double num = Double.parseDouble(numText);
        return new AstNodes(num);
    }
}
