import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor {

    public static void process() {
        BufferedReader reader;
        BufferedWriter writer;
        try {

            reader = new BufferedReader(new FileReader("src/rawdata.csv"));
            writer = new BufferedWriter(new FileWriter("src/processed_data.csv"));

            // Skips the first 7 lines of the csv -> we don't need this information
            for (int i = 0; i < 8; i++) {
                reader.readLine();
            }

            HashMap<String, ArrayList<Target>> allSamples = new HashMap<String, ArrayList<Target>>();
            String currentLine = reader.readLine();

            Pattern pattern = Pattern.compile("[^,]");
            boolean matchFound = true;

            while (matchFound) {

                String[] delimitedLine = currentLine.split(",");
                String currentSampleGroup = delimitedLine[1];
                String currentTargetName = delimitedLine[2];
                Double currentSampleCt;

                try {
                    currentSampleCt = Double.parseDouble(delimitedLine[9]);
                } catch (NumberFormatException e) {
                    currentSampleCt = 0.0;
                }

                Target currentTarget = new Target(currentTargetName, currentSampleCt);
                Sample currentSample = new Sample(currentSampleGroup, currentTarget);
                boolean targetExists = false;

                // Checks for existing groups:
                if (allSamples.containsKey(currentSampleGroup)) {
                    ArrayList<Target> sampleTargets = new ArrayList<Target>(allSamples.get(currentSampleGroup));
                    for (int i = 0; i < sampleTargets.size(); i++) {

                        // Checks for existing targets within group
                        if (sampleTargets.get(i).getTargetName().equals(currentTargetName)) {
                            targetExists = true;
                            sampleTargets.get(i).getCtArray().add(currentSampleCt);
                            break;
                        }

                    }

                    if (!targetExists) {
                        allSamples.get(currentSampleGroup).add(currentTarget);
                    }

                } else {
                    allSamples.put(currentSampleGroup, currentSample.getTargets());
                }
                currentLine = reader.readLine();
                Matcher matcher = pattern.matcher(currentLine);
                matchFound = matcher.find();
            }

            // Writes headers into processed file
            // We assume that each experimental group has the same amount of targets as the first.
            writer.write(",Group,");

            Iterator<Map.Entry<String, ArrayList<Target>>> sampleIterator = allSamples.entrySet().iterator();
        
            Map.Entry<String, ArrayList<Target>> sampleElement = sampleIterator.next();
            ArrayList<Target> targetNameList = sampleElement.getValue();
            
            for (Target target : targetNameList) {
                writer.write(target.getTargetName() + ",");
            }

            writer.write("dCt,dCt Average,dCt SD,ddCt,Fold Change,Fold Change Average,Fold Change SD,p value");

            reader.close();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}