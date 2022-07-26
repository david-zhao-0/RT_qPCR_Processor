import java.util.*;

public class Sample {
    private String groupName;
    private HashMap<String, ArrayList<Double>> targetMap = new HashMap<String, ArrayList<Double>>();
    private ArrayList<Double> dCtArray = new ArrayList<>();
    private Double dCtAverage = 0.0, dCtStDev = 0.0, dCtSum = 0.0;
    private ArrayList<Double> ddCtArray = new ArrayList<>();
    private ArrayList<Double> foldChanges = new ArrayList<>();
    private Double normalizationValue = 0.0;
    private Double foldChangeAverage = 0.0, foldChangeStdDev = 0.0;
    // private ArrayList<Double> normalizationArray = new ArrayList<Double>();


    public Sample() {
        this.groupName = null;
        this.targetMap = null;
    }

    public Sample(String groupName, String target, ArrayList<Double> ct) {
        this.groupName = groupName;
        this.targetMap.put(target, ct);
    }

    public Sample(Sample sample) {
        this.groupName = sample.groupName;
        this.targetMap = sample.targetMap;
        this.dCtArray = sample.dCtArray;
        this.dCtAverage = sample.dCtAverage;
        this.dCtStDev = sample.dCtStDev;
        this.dCtSum = sample.dCtSum;
        this.foldChangeAverage = sample.foldChangeAverage;
        this.ddCtArray = sample.ddCtArray;
        this.foldChanges = sample.foldChanges;
    }

    public String getGroupName() {
        return groupName;
    }

    public HashMap<String, ArrayList<Double>> getTargetMap() {
        return targetMap;
    }

    public ArrayList<Double> getdCtArray() {
        return dCtArray;
    }

    public void setdCtArray(ArrayList<Double> dCtArray) {
        this.dCtArray = dCtArray;
    }

    public Double getdCtAverage() {
        return dCtAverage;
    }

    public Double getdCtStdDev() {
        return dCtStDev;
    }

    public ArrayList<Double> getddCtArray() {
        return ddCtArray;
    }

    public ArrayList<Double> getFoldChanges() {
        return foldChanges;
    }

    public Double getFoldChangeAverage() {
        return foldChangeAverage;
    }

    public Double getFoldChangeStdDev() {
        return foldChangeStdDev;
    }

    public void setNormalizationValue(Double normalizationValue) {
        this.normalizationValue = normalizationValue;
    }

    // public void setNormalizationArray(ArrayList<Double> normalizationArray) {
    //     this.normalizationArray = normalizationArray;
    // }

    public void calculatedCt() {
        ArrayList<Double> calculatedCt = new ArrayList<Double>();
        ArrayList<Double> controlCt = new ArrayList<Double>();
        ArrayList<Double> experimentalCt = new ArrayList<Double>();
        targetMap.forEach(
            (k, v) -> {
                
                for (int i = 0; i < v.size(); i++) {
                    if (k.equals("GAPDH")) {
                        controlCt.add(v.get(i));
                    } else {
                        experimentalCt.add((v.get(i)));
                    }
                }

            }
        
        );

        for (int i = 0; i < controlCt.size(); i++) {
            calculatedCt.add(experimentalCt.get(i) - controlCt.get(i));
        }  

        this.dCtArray = calculatedCt;
    }

    public void calculatedCtAverage() {
        // This method assumes that the samples will always be run in triplicate (i < 3)
        for (int i = 0; i < 3; i++) {
            dCtSum += dCtArray.get(i);
        }
        this.dCtAverage = dCtSum / 3.0;
    }

    public void calculatedCtStdDev() {
        // This method assumes that the samples will always be run in triplicate (i < 3) 
        for (Double dCt : dCtArray) {
            dCtStDev += Math.pow(dCt - dCtAverage, 2);
        }
        dCtStDev = Math.sqrt(dCtStDev/3.0);
    }

    public void calculateddCt() {
        // This method assumes that the samples will always be run in triplicate (i < 3) 
        for (int i = 0; i < 3; i++) {
            this.ddCtArray.add(this.dCtArray.get(i) - normalizationValue);
        }
    }

    public void calculateFoldChanges() {
        for (int i = 0; i < 3; i++) {
            foldChanges.add(Math.pow(0.5, ddCtArray.get(i)));
        }
    }

    public void calculateFoldChangeAverage() {
        for (int i = 0; i < 3; i++) {
           foldChangeAverage += foldChanges.get(i);
        }
        foldChangeAverage = foldChangeAverage / 3.0;
    }

    public void calculateFoldChangeStdDev() {
        for (Double foldChange : foldChanges) {
            foldChangeStdDev += Math.pow(foldChange - foldChangeAverage, 2);
        }
        foldChangeStdDev = Math.sqrt(foldChangeStdDev / 3.0);
    }

}
