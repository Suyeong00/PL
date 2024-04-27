import java.io.IOException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class program {

    public static void main(String[] args) throws IOException {
                
        // Get Lexer
        ExprLexer lexer = new ExprLexer(CharStreams.fromStream(System.in));
        
        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // Pass tokens to parser
        ExprParser parser = new ExprParser(tokens);
        ParseTree antlrAST = parser.prog();

		// Build AST
		// Build the AST with BuildAstVisitor.java
        BuildAstVisitor buildAstVisitor = new BuildAstVisitor();
        buildAstVisitor.visit(antlrAST);


        // Print AST
		// Print built AST with AstCall.java
        AstCall astCall = new AstCall();
        astCall.buildAST(buildAstVisitor.getAstNodesList());

		// Evaluate AST
		// Evaluate AST with traversing AST.
        Evaluate evaluate = new Evaluate();
        evaluate.evaluateAST(buildAstVisitor.getAstNodesList());
	}
}
