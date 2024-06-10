import java.util.*;

public class AstCall {

    String output = "";

    public String buildAST(List<AstNodes> astNodesList) {
        for (AstNodes astNode : astNodesList) {
            switch (astNode.type) {
                case FunctionExecution -> processFunctionExecution(astNode);
                case SingleExecution -> processProgramExpr(astNode.programExpr, 0);
            }
        }
        return output;
    }

    private void processFunctionExecution(AstNodes functionExecution) {
        List<AstNodes> functionList = functionExecution.declList;
        for (AstNodes functionDecl : functionList) {
            processFunctionDecl(functionDecl);
        }
        AstNodes programExpr = functionExecution.programExpr;
        processProgramExpr(programExpr, 0);
    }

    private void processFunctionDecl(AstNodes functionDecl) {
        AstType type = functionDecl.type;
        switch (type) {
            case FuncDeclareWithParam -> processFuncDeclareWithParam(functionDecl);
            case FuncDeclare -> processFuncDeclare(functionDecl);
        }
    }

    private void processFuncDeclareWithParam(AstNodes funcDeclareWithParam) {
        String functionId = funcDeclareWithParam.functionId;
        List<String> paramList = funcDeclareWithParam.paramList;
        AstNodes functionExpr = funcDeclareWithParam.functionExpr;

        output += "DECL\n";
        output += "\t" + functionId + "\n";
        for (String param : paramList) {
            output += "\t" + param + "\n";
        }
        processFuncionExpr(functionExpr, 1);
    }

    private void processFuncDeclare(AstNodes funcDeclare) {
        String functionId = funcDeclare.functionId;
        AstNodes functionExpr = funcDeclare.functionExpr;

        output += "DECL\n";
        output += "\t" + functionId + "\n";
        processFuncionExpr(functionExpr, 1);
    }

    private void processFuncionExpr(AstNodes functionExpr, int depth) {
        AstType type = functionExpr.type;

        if (type == AstType.Number) {
            for (int i = 0; i < depth; i++) output += "\t";
            output += functionExpr.value + "\n";
        }
        if (type == AstType.Variable) {
            for (int i = 0; i < depth; i++) output += "\t";
            output += functionExpr.id + "\n";
        }
        if (type == AstType.LetIn) processLetIn(functionExpr, depth);
        if (type == AstType.CallFunctionWithParameter) processCallFunctionWithParameter(functionExpr, depth);

        switch (type) {
            case ADD -> {
                for (int i = 0; i < depth; i++) output += "\t";
                output += "ADD" + "\n";
                processProgramExpr(functionExpr.left, depth + 1);
                processProgramExpr(functionExpr.right, depth + 1);
            }

            case SUB -> {
                for (int i = 0; i < depth; i++) output += "\t";
                output += "SUB" + "\n";
                processProgramExpr(functionExpr.left, depth + 1);
                processProgramExpr(functionExpr.right, depth + 1);
            }

            case MUL -> {
                for (int i = 0; i < depth; i++) output += "\t";
                output += "MUL" + "\n";
                processProgramExpr(functionExpr.left, depth + 1);
                processProgramExpr(functionExpr.right, depth + 1);
            }

            case DIV -> {
                for (int i = 0; i < depth; i++) output += "\t";
                output += "DIV" + "\n";
                processProgramExpr(functionExpr.left, depth + 1);
                processProgramExpr(functionExpr.right, depth + 1);
            }
        }
    }

    private void processLetIn(AstNodes letIn, int depth) {
        for (int i = 0; i < depth; i++) output += "\t";
        output += "LETIN" + "\n";
        for (int i = 0; i < depth + 1; i++) output += "\t";
        output += letIn.id + "\n";
        processProgramExpr(letIn.x1, depth + 1);
        processProgramExpr(letIn.x2, depth + 1);
    }

    private void processCallFunctionWithParameter(AstNodes functionCall, int depth) {
        for (int i = 0; i < depth; i++) output += "\t";
        output += "Call" + "\n";
        for (int i = 0; i < depth + 1; i++) output += "\t";
        output += functionCall.functionId + "\n";
        List<AstNodes> exprList = functionCall.exprList;
        for (AstNodes expr : exprList) {
            processProgramExpr(expr, depth + 1);
        }
    }

    private void processCallFunction(AstNodes functionCall, int depth) {
        for (int i = 0; i < depth; i++) output += "\t";
        output += "Call" + "\n";
        for (int i = 0; i < depth + 1; i++) output += "\t";
        output += functionCall.functionId + "\n";
    }

    private void processProgramExpr(AstNodes expr, int depth) {
        AstType type = expr.type;

        if (type == AstType.Number) {
            for (int i = 0; i < depth; i++) output += "\t";
            output += expr.value + "\n";
        }
        if (type == AstType.Variable) {
            for (int i = 0; i < depth; i++) output += "\t";
            output += expr.id + "\n";
        }
        if (type == AstType.LetIn) processLetIn(expr, depth);
        if (type == AstType.CallFunctionWithParameter) processCallFunctionWithParameter(expr, depth);
        if (type == AstType.CallFunction) processCallFunction(expr, depth);

        switch (type) {
            case ADD -> {
                for (int i = 0; i < depth; i++) output += "\t";
                output += "ADD" + "\n";
                processProgramExpr(expr.left, depth + 1);
                processProgramExpr(expr.right, depth + 1);
            }

            case SUB -> {
                for (int i = 0; i < depth; i++) output += "\t";
                output += "SUB" + "\n";
                processProgramExpr(expr.left, depth + 1);
                processProgramExpr(expr.right, depth + 1);
            }

            case MUL -> {
                for (int i = 0; i < depth; i++) output += "\t";
                output += "MUL" + "\n";
                processProgramExpr(expr.left, depth + 1);
                processProgramExpr(expr.right, depth + 1);
            }

            case DIV -> {
                for (int i = 0; i < depth; i++) output += "\t";
                output += "DIV" + "\n";
                processProgramExpr(expr.left, depth + 1);
                processProgramExpr(expr.right, depth + 1);
            }
        }
    }
}
