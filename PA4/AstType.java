public enum AstType {
    FunctionExecution, SingleExecution,
    DeclList, LastDecl,
    FuncDeclareWithParam, FuncDeclare,
    ParamList, LastParam,

    ProgramExpr, FunctionExpr,
    Parenthesis, LetIn, CallFunction, CallFunctionWithParameter,
    Operation, Number, Variable,
    ADD, SUB, MUL, DIV,
    ExprList, LastExpr,
    Integer, FunctionID, ParamID
}
