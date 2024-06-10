import java.util.List;
import java.util.Map;

public class AstNodes {
    String functionId;
    List<String> paramList;
    AstNodes functionExpr;

    String id;
    String paramId;
    Double value;
    AstNodes left;
    AstNodes right;

    AstNodes x1;
    AstNodes x2;

    AstNodes programExpr;
    AstType type;

    List<AstNodes> exprList;
    List<AstNodes> declList;

    public AstNodes visitFunctionExecution(List<AstNodes> declList, AstNodes programExpr) {
        this.declList = declList;
        this.programExpr = programExpr;
        this.type = AstType.FunctionExecution;
        return this;
    }

    public AstNodes visitSingleExecution(AstNodes programExpr) {
        this.programExpr = programExpr;
        this.type = AstType.SingleExecution;
        return this;
    }

    public AstNodes visitDeclList(List<AstNodes> declList) {
        this.declList = declList;
        this.type = AstType.DeclList;
        return this;
    }

    public AstNodes visitLastDecl(List<AstNodes> declList) {
        this.declList = declList;
        this.type = AstType.LastDecl;
        return this;
    }


    public AstNodes visitFuncDeclareWithParam(String functionId, List<String> paramList, AstNodes functionExpr) {
        this.functionId = functionId;
        this.paramList = paramList;
        this.functionExpr = functionExpr;
        this.type = AstType.FuncDeclareWithParam;
        return this;
    }


    public AstNodes visitFuncDeclare(String functionId,  AstNodes functionExpr) {
        this.functionId = functionId;
        this.functionExpr = functionExpr;
        this.type = AstType.FuncDeclare;
        return this;
    }

    public AstNodes visitParamList(List<String> paramList) {
        this.paramList = paramList;
        this.type = AstType.ParamList;
        return this;
    }


    public AstNodes visitLastParam(List<String> paramList) {
        this.paramList = paramList;
        this.type = AstType.LastParam;
        return this;
    }

    public AstNodes visitFunctionExpr(AstNodes functionExpr) {
        this.functionExpr = functionExpr;
        this.type = AstType.FunctionExpr;
        return this;
    }

    public AstNodes visitVariable(String variableId) {
        this.id = variableId;
        this.type = AstType.Variable;
        return this;
    }

    public AstNodes visitNumber(Double value) {
        this.value = value;
        this.type = AstType.Number;
        return this;
    }

    public AstNodes visitCallFunctionWithParameter(String functionId, List<AstNodes> exprList) {
        this.type = AstType.CallFunctionWithParameter;
        this.functionId = functionId;
        this.exprList = exprList;
        return this;
    }

    public AstNodes visitOperation(AstNodes left, AstNodes right, AstType type) {
        this.left = left;
        this.right = right;
        this.type = type;
        return this;
    }

    public AstNodes visitCallFunction(String functionId) {
        this.functionId = functionId;
        this.type = AstType.CallFunction;
        return this;
    }

    public AstNodes visitLetIn(String variableId, AstNodes x1, AstNodes x2) {
        this.id = variableId;
        this.x1 = x1;
        this.x2 = x2;
        this.type = AstType.LetIn;
        return this;
    }

    public AstNodes visitExprList(List<AstNodes> exprList) {
        this.exprList = exprList;
        this.type = AstType.ExprList;
        return this;
    }

    public AstNodes visitLastExpr(List<AstNodes> exprList) {
        this.exprList = exprList;
        this.type = AstType.LastExpr;
        return this;
    }

    public AstNodes visitInteger(double value) {
        this.value = value;
        this.type = AstType.Integer;
        return this;
    }

    public AstNodes visitFunctionID(String functionId) {
        this.id = functionId;
        this.type = AstType.FunctionID;
        return this;
    }

    public AstNodes visitParamID(String paramId) {
        this.paramId = paramId;
        this.type = AstType.ParamID;
        return this;
    }
}
