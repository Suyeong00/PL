public class AstNodes {
    AstNodes left;
    AstNodes right;
    double value;
    String type;

    String id;

    public AstNodes(AstNodes left, AstNodes right, String type) {
        this.left = left;
        this.right = right;
        this.type = type;
    }

    public AstNodes(double value) {
        this.value = value;
        this.type = "terminal";
    }

    public AstNodes(double value, String id) {
        this.value = value;
        this.id = id;
        this.type = "variable";
    }

    public AstNodes(String type, String id, double value) { // for Declaration
        this.type = type;
        this.id = id;
        this.value = value;
    }

}
