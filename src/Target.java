import java.util.*;

public class Target {
    private String targetName;
    private ArrayList<Double> ctArray = new ArrayList<Double>();

    public Target(String targetName) {
        this.targetName = targetName;
    }

    public Target(String targetName, Double ct) {
        this.targetName = targetName;
        this.ctArray.add(ct);
    }

    public Target(Target target) {
        this.targetName = target.targetName;
        this.ctArray = target.ctArray;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public ArrayList<Double> getCtArray() {
        return ctArray;
    }

    public void setCtArray(ArrayList<Double> ctArray) {
        this.ctArray = ctArray;
    }
}
