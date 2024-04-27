grammar Expr;

// parser rules
prog : (statement ';' NEWLINE?)*;

statement : (expr | decl)     # Instruction;

expr : '(' expr ')'         # Parenthesis
     | expr ('*'|'/') expr  # Operation
     | expr ('+'|'-') expr  # Operation
     | num                  # Number
     | ID                   # Variable
     | '(' expr ')'         # Parenthesis
     ;

decl : ID '=' INT           # IntDeclaration
     | ID '=' REAL          # RealDeclaration
     ;

num  : INT                  # Integer
     | REAL                 # Real
     ;

// lexer rules
NEWLINE: [\r\n]+ ;
INT: '0' | '-'?[1-9][0-9]* ;          // should handle negatives
REAL: ('0' | '-'?[1-9][0-9]*) '.'[0-9]+ ; // should handle signs(+/-)
ID: [a-zA-Z_][a-zA-Z0-9_]* ;
WS: [ \t\r\n]+ -> skip ;
