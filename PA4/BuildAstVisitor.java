import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class BuildAstVisitor extends ExprBaseVisitor<AstNodes> {
    List<AstNodes> astNodesList = new ArrayList<>();

    public List<AstNodes> getAstNodesList() {
        return astNodesList;
    }

    @Override
    public AstNodes visitFunctionExecution(ExprParser.FunctionExecutionContext ctx) {
        AstNodes functionDecls = visit(ctx.getChild(0));
        AstNodes programExpr = visit(ctx.getChild(1));
        List<AstNodes> declList = new ArrayList<>(functionDecls.declList);
        AstNodes functionExecution = new AstNodes().visitFunctionExecution(declList, programExpr);
        astNodesList.add(functionExecution);
        return functionExecution;
    }

    @Override
    public AstNodes visitSingleExecution(ExprParser.SingleExecutionContext ctx) {
        AstNodes programExpr = visit(ctx.getChild(0));
        AstNodes singleExecution = new AstNodes().visitSingleExecution(programExpr);
        astNodesList.add(singleExecution);
        return singleExecution;
    }

    @Override
    public AstNodes visitDeclList(ExprParser.DeclListContext ctx) {
        ArrayList<AstNodes> declList = new ArrayList<>();
        AstNodes firstDecl = visit(ctx.getChild(0));
        AstNodes secondDecl = visit(ctx.getChild(1));
        declList.add(firstDecl);
        declList.addAll(secondDecl.declList);
        return new AstNodes().visitDeclList(declList);
    }

    @Override
    public AstNodes visitLastDecl(ExprParser.LastDeclContext ctx) {
        AstNodes functionDecl = visit(ctx.getChild(0));
        List<AstNodes> declList = new ArrayList<>();
        declList.add((functionDecl));
        return new AstNodes().visitLastDecl(declList);
    }

    @Override
    public AstNodes visitFuncDeclareWithParam(ExprParser.FuncDeclareWithParamContext ctx) {
        String functionID = visit(ctx.getChild(1)).id;
        List<String> paramList = visit(ctx.getChild(2)).paramList;
        AstNodes functionExpr = visit(ctx.getChild(4)).functionExpr;
        return new AstNodes().visitFuncDeclareWithParam(functionID, paramList, functionExpr);
    }

    @Override
    public AstNodes visitFuncDeclare(ExprParser.FuncDeclareContext ctx) {
        String functionID = visit(ctx.getChild(1)).id;
        AstNodes functionExpr = visit(ctx.getChild(3)).functionExpr;
        return new AstNodes().visitFuncDeclare(functionID, functionExpr);
    }

    @Override
    public AstNodes visitParamList(ExprParser.ParamListContext ctx) {
        List<String> paramList = new ArrayList<>();
        AstNodes firstParam = visit(ctx.getChild(0));
        AstNodes secondParam = visit(ctx.getChild(1));
        paramList.add(firstParam.paramId);
        paramList.addAll(secondParam.paramList);
        return new AstNodes().visitParamList(paramList);
    }

    @Override
    public AstNodes visitLastParam(ExprParser.LastParamContext ctx) {
        String paramId = visit(ctx.getChild(0)).paramId;
        List<String> paramList = new ArrayList<>();
        paramList.add(paramId);
        return new AstNodes().visitLastParam(paramList);
    }

    @Override
    public AstNodes visitProgramExpr(ExprParser.ProgramExprContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public AstNodes visitFunctionExpr(ExprParser.FunctionExprContext ctx) {
        AstNodes functionExpr = visit(ctx.getChild(0));
        return new AstNodes().visitFunctionExpr(functionExpr);
    }

    @Override
    public AstNodes visitVariable(ExprParser.VariableContext ctx) {
        String variableId = ctx.getChild(0).getText();
        return new AstNodes().visitVariable(variableId);
    }

    @Override
    public AstNodes visitNumber(ExprParser.NumberContext ctx) {
        Double value = visit(ctx.getChild(0)).value;
        return new AstNodes().visitNumber(value);
    }

    @Override
    public AstNodes visitCallFunctionWithParameter(ExprParser.CallFunctionWithParameterContext ctx) {
        String functionId = ctx.getChild(0).getText();
        List<AstNodes> exprList = visit(ctx.getChild(2)).exprList;
        return new AstNodes().visitCallFunctionWithParameter(functionId, exprList);
    }

    @Override
    public AstNodes visitOperation(ExprParser.OperationContext ctx) {
        AstNodes left = visit(ctx.getChild(0));
        AstNodes right = visit(ctx.getChild(2));
        String operator = ctx.getChild(1).getText();
        return switch (operator) {
            case "*" -> new AstNodes().visitOperation(left, right, AstType.MUL);
            case "/" -> new AstNodes().visitOperation(left, right, AstType.DIV);
            case "+" -> new AstNodes().visitOperation(left, right, AstType.ADD);
            case "-" -> new AstNodes().visitOperation(left, right, AstType.SUB);
            default -> super.visitOperation(ctx);
        };
    }

    @Override
    public AstNodes visitCallFunction(ExprParser.CallFunctionContext ctx) {
        String functionId = ctx.getChild(0).getText();
        return new AstNodes().visitCallFunction(functionId);
    }

    @Override
    public AstNodes visitLetIn(ExprParser.LetInContext ctx) {
        String id = ctx.getChild(1).getText();
        AstNodes x1 = visit(ctx.getChild(3));
        AstNodes x2 = visit(ctx.getChild(5));
        return new AstNodes().visitLetIn(id, x1, x2);
    }

    @Override
    public AstNodes visitExprList(ExprParser.ExprListContext ctx) {
        ArrayList<AstNodes> exprList = new ArrayList<>();
        AstNodes firstExpr = visit(ctx.getChild(0));
        AstNodes secondExpr = visit(ctx.getChild(2));
        exprList.add(firstExpr);
        exprList.addAll(secondExpr.exprList);
        return new AstNodes().visitExprList(exprList);
    }

    @Override
    public AstNodes visitLastExpr(ExprParser.LastExprContext ctx) {
        AstNodes expr = visit(ctx.getChild(0));
        List<AstNodes> exprList = new ArrayList<>();
        exprList.add(expr);
        return new AstNodes().visitLastExpr(exprList);
    }

    @Override
    public AstNodes visitInteger(ExprParser.IntegerContext ctx) {
        String numText = ctx.getChild(0).getText();
        double num = Double.parseDouble(numText);
        return new AstNodes().visitInteger(num);
    }

    @Override
    public AstNodes visitFunctionID(ExprParser.FunctionIDContext ctx) {
        String functionId = ctx.getChild(0).getText();
        return new AstNodes().visitFunctionID(functionId);
    }

    @Override
    public AstNodes visitParamID(ExprParser.ParamIDContext ctx) {
        String paramId = ctx.getChild(0).getText();
        return new AstNodes().visitParamID(paramId);
    }
}