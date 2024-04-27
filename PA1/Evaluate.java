import java.util.List;

class Evaluate {
    public void evaluateAST(List<AstNodes> astNodesList) {
        for (AstNodes astNodes : astNodesList) {
            String type = astNodes.type;
            if (astNodes.type.equals("ASSIGN")) {
                System.out.println("0.0");
            }
            else System.out.println(traverseAST(astNodes));
        }
    }

    private double traverseAST(AstNodes astNodes) {
        String type = astNodes.type;
        if(type.equals("terminal")) {
            return astNodes.value;
        }
        if(type.equals("variable")) {
            return astNodes.value;
        }

        switch (type) {
            case "ADD": return traverseAST(astNodes.left) + traverseAST(astNodes.right);
            case "SUB": return traverseAST(astNodes.left) - traverseAST(astNodes.right);
            case "MUL": return traverseAST(astNodes.left) * traverseAST(astNodes.right);
            case "DIV": return traverseAST(astNodes.left) / traverseAST(astNodes.right);
            default: return  0;
        }
    }
}