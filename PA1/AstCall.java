import java.util.List;

public class AstCall {
    public void buildAST(List<AstNodes> astNodesList) {
        for (AstNodes astNodes : astNodesList) {
            String type = astNodes.type;
            if (astNodes.type.equals("ASSIGN")) {
                System.out.println("ASSIGN");
                System.out.println("\t" + astNodes.id);
                System.out.println("\t" + astNodes.value);
            }
            else traverseAST(astNodes, 0);
        }
    }

    private void traverseAST(AstNodes astNodes, int depts) {
        String type = astNodes.type;
        for (int i = 0; i < depts; i++) System.out.print("\t");
        if(type.equals("terminal")) {
            System.out.println(astNodes.value);
            return;
        }
        if(type.equals("variable")) {
            System.out.println(astNodes.id);
            return;
        }

        System.out.println(astNodes.type);
        traverseAST(astNodes.left, depts + 1);
        traverseAST(astNodes.right, depts + 1);
    }
}
