import java.util.*;

public class Sample {
    private String groupName;
    private ArrayList<Target> targets = new ArrayList<Target>();

    // Experimental Group Constructor
    public Sample(String groupName) {
        this.groupName = groupName;
        }

    public Sample(String groupName, Target target) {
        this.groupName = groupName;
        targets.add(target);
    }

    // Copy constructor
    public Sample(Sample sample) {
        this.groupName = sample.groupName;
        this.targets = sample.targets;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String sampleName) {
        this.groupName = sampleName;
    }

    public ArrayList<Target> getTargets() {
        return targets;
    }

    public void setTarget(ArrayList<Target> targets) {
        this.targets = targets;
    }

    public void addTarget(Target target) {
        this.targets.add(target);
    }


}
