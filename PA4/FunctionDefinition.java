import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionDefinition {
    List<String> paramList;
    AstNodes functionExpr;

    Map<String, Double> argMap = new HashMap<>();

    String type;

    public FunctionDefinition(List<String> paramList, AstNodes functionExpr) {
        this.paramList = paramList;
        this.functionExpr = functionExpr;
        this.type = "FunctionWithParam";
    }

    public FunctionDefinition(AstNodes functionExpr) {
        this.paramList = new ArrayList<>();
        this.functionExpr = functionExpr;
        this.type = "FunctionNoParam";
    }

    public void mappingArg(String id, Double value) {
        argMap.put(id, value);
    }

    public void clearArgMap() {
        argMap.clear();
    }
}
