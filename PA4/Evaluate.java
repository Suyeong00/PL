import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Evaluate {
    Map<String, Double> variableStore = new HashMap<>();
    Map<String, FunctionDefinition> functionStore = new HashMap<>();

    public String getOutput() {
        return output;
    }

    String output = "";

    public String buildAST(List<AstNodes> astNodesList) {
        for (AstNodes astNode : astNodesList) {
            switch (astNode.type) {
                case FunctionExecution -> processFunctionExecution(astNode);
                case SingleExecution -> {
                    Double evaluatedValue = processProgramExpr(astNode.programExpr);
                    output += evaluatedValue.toString();
                }
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
        Double evaluatedValue = processProgramExpr(programExpr);
        output += evaluatedValue.toString();
    }

    private void processFunctionDecl(AstNodes functionDecl) {
        AstType type = functionDecl.type;
        switch (type) {
            case FuncDeclareWithParam -> processFuncDeclareWithParam(functionDecl);
            case FuncDeclare -> processFuncDeclare(functionDecl);
        }
        output += "0.0\n";
    }

    private void processFuncDeclareWithParam(AstNodes funcDeclareWithParam) {
        String functionId = funcDeclareWithParam.functionId;
        List<String> paramList = funcDeclareWithParam.paramList;
        AstNodes functionExpr = funcDeclareWithParam.functionExpr;

        FunctionDefinition functionDefinition = new FunctionDefinition(paramList, functionExpr);
        functionStore.put(functionId, functionDefinition);

        processFunctionExpr(functionExpr, functionId);
    }

    private void processFuncDeclare(AstNodes funcDeclare) {
        String functionId = funcDeclare.functionId;
        AstNodes functionExpr = funcDeclare.functionExpr;

        FunctionDefinition functionDefinition = new FunctionDefinition(functionExpr);
        functionStore.put(functionId, functionDefinition);

        processFunctionExpr(functionExpr, functionId);
    }

    private void processFunctionExpr(AstNodes functionExpr, String functionId) {
        AstType type = functionExpr.type;

        if (type == AstType.Variable) {
            String paramId = functionExpr.id;
            FunctionDefinition functionDefinition = functionStore.get(functionId);
            List<String> paramList = functionDefinition.paramList;
            if(paramList.contains(paramId)) return;
            System.out.println("Error: Free identifier " + paramId + " detected.");
            System.exit(0);
        }

        switch (type) {
            case ADD, SUB, MUL, DIV -> {
                processFunctionExpr(functionExpr.left, functionId);
                processFunctionExpr(functionExpr.right, functionId);
            }
        }
    }

    private Double processLetIn(AstNodes letIn) {
        String id = letIn.id;
        Double evaluatedId = processProgramExpr(letIn.x1);
        variableStore.put(id, evaluatedId);
        return processProgramExpr(letIn.x2);
    }

    private Double processCallFunctionWithParameter(AstNodes functionCall) {
        String functionId = functionCall.functionId;

        if (!functionStore.containsKey(functionId)) {
            System.out.println("Error: Undefined function " + functionId + " detected.");
            System.exit(0);
        }

        FunctionDefinition functionDefinition = functionStore.get(functionId);
        AstNodes functionExpr = functionDefinition.functionExpr;

        List<String> paramList = functionDefinition.paramList;
        int paramSize = paramList.size();

        List<AstNodes> exprList = functionCall.exprList;
        int argSize = exprList.size();

        if (paramSize != argSize) {
            System.out.println("Error: The number of arguments of " + functionId + " mismatched, Required "
                    + paramSize + ", Actual: " + argSize);
            System.exit(0);
        }
        functionDefinition.clearArgMap();
        for(int i = 0; i < paramSize; i++) {
            String parameter = paramList.get(i);
            AstNodes expr = exprList.get(i);
            Double evaluatedExpr = processProgramExpr(expr);
            functionDefinition.mappingArg(parameter, evaluatedExpr);
        }
        Map<String, Double> argMap = functionDefinition.argMap;

        return processFunctionExpr(functionExpr, argMap);
    }

    private Double processFunctionExpr(AstNodes functionExpr, Map<String, Double> argMap) {
        AstType type = functionExpr.type;

        if (type == AstType.Number) return functionExpr.value;

        if (type == AstType.Variable) {
            String id = functionExpr.id;
            return argMap.get(id);
        }
        if (type == AstType.LetIn) return processFunctionExpr(functionExpr, argMap);
        if (type == AstType.CallFunctionWithParameter) return processFunctionExpr(functionExpr, argMap);
        if (type == AstType.CallFunction) return processFunctionExpr(functionExpr, argMap);

        switch (type) {
            case ADD -> {
                return processFunctionExpr(functionExpr.left, argMap) + processFunctionExpr(functionExpr.right, argMap);
            }

            case SUB -> {
                return processFunctionExpr(functionExpr.left, argMap) - processFunctionExpr(functionExpr.right, argMap);
            }

            case MUL -> {
                return processFunctionExpr(functionExpr.left, argMap) * processFunctionExpr(functionExpr.right, argMap);
            }

            case DIV -> {
                return processFunctionExpr(functionExpr.left, argMap) / processFunctionExpr(functionExpr.right, argMap);
            }
        }
        return -999.0;
    }

    private Double processCallFunction(AstNodes functionCall) {
        String functionId = functionCall.functionId;
        if (!functionStore.containsKey(functionId)) {
            System.out.println("Error: Undefined function " + functionId + " detected.");
            System.exit(0);
        }
        FunctionDefinition functionDefinition = functionStore.get(functionId);
        int paramSize = functionDefinition.paramList.size();
        if(paramSize != 0) {
            System.out.println("Error: The number of arguments of " + functionId + " mismatched, Required: "
                    + paramSize + ", Actual: 0");
            System.exit(0);
        }
        AstNodes functionExpr = functionDefinition.functionExpr;
        Double value = processProgramExpr(functionExpr);
        return value;
    }

    private Double processProgramExpr(AstNodes expr) {
        AstType type = expr.type;

        if (type == AstType.Number) return expr.value;

        if (type == AstType.Variable) {
            String id = expr.id;
            if (variableStore.containsKey(id)) return variableStore.get(id);
            System.out.println("Error: Free identifier " + id + " detected.");
            System.exit(0);
        }
        if (type == AstType.LetIn) return processLetIn(expr);
        if (type == AstType.CallFunctionWithParameter) return processCallFunctionWithParameter(expr);
        if (type == AstType.CallFunction) return processCallFunction(expr);

        switch (type) {
            case ADD -> {
                return processProgramExpr(expr.left) + processProgramExpr(expr.right);
            }

            case SUB -> {
                return processProgramExpr(expr.left) - processProgramExpr(expr.right);
            }

            case MUL -> {
                return processProgramExpr(expr.left) * processProgramExpr(expr.right);
            }

            case DIV -> {
                return processProgramExpr(expr.left) / processProgramExpr(expr.right);
            }
        }
        return -999.0;
    }
}