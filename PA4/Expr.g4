grammar Expr;

// parser rules
prog : (statement ';' NEWLINE?)*;

statement : decl_list prog_expr      # FunctionExecution
          | prog_expr                # SingleExecution
          ;

decl_list : decl decl_list    # DeclList
          | decl              # LastDecl
          ;

decl : 'def' function_id param_list '=' func_expr 'endef'     # FuncDeclareWithParam
     | 'def' function_id '=' func_expr 'endef'              # FuncDeclare
     ;

param_list : param_id param_list          # ParamList
           | param_id                     # LastParam
           ;

prog_expr : expr                          # ProgramExpr
          ;

func_expr : expr                          # FunctionExpr
          ;

expr : '(' expr ')'                   # Parenthesis
     | 'let' ID '=' expr 'in' expr    # LetIn
     | ID '(' ')'                     # CallFunction
     | ID '(' expr_list ')'           # CallFunctionWithParameter
     | expr ('*'|'/') expr            # Operation
     | expr ('+'|'-') expr            # Operation
     | num                            # Number
     | ID                             # Variable
     | '(' expr ')'                   # Parenthesis
     ;

expr_list : expr ',' expr_list        # ExprList
     | expr                           # LastExpr
     ;

num  : INT                  # Integer
     ;

function_id : ID            # FunctionID
            ;
param_id : ID               # ParamID
         ;

// lexer rules
NEWLINE: [\r\n]+ ;
INT: '0' | '-'?[1-9][0-9]* ;          // should handle negatives
ID: [a-zA-Z_][a-zA-Z0-9_]* ;
WS: [ \t\r\n]+ -> skip ;
