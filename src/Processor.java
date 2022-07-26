import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Processor {

    public static void process() {
        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader("src/rawdata.csv"));
            writer = new BufferedWriter(new FileWriter("src/processeddata.csv"));

            for (int i = 0; i < 8; i++) {
                reader.readLine();
            }

            String currentLine = reader.readLine();
            ArrayList<Sample> allSamples = new ArrayList<Sample>();
            Pattern pattern = Pattern.compile("[^,]");
            boolean matchFound = true;

            while (matchFound) {

                String[] delimitedLine = currentLine.split(",");
                String sampleName = new String(delimitedLine[1]);
                String targetName = new String(delimitedLine[2]);
                ArrayList<Double> ctArray = new ArrayList<>();
                boolean groupExists = false;

                try {
                    ctArray.add(Double.parseDouble(delimitedLine[9]));
                } catch (NumberFormatException e) {
                    ctArray.add(0.0);
                }

                Sample currentSample = new Sample(sampleName, targetName, ctArray);

                // Check for existing groups and targets
                for (int i = 0; i < allSamples.size(); i++) {
                    Sample comparedSample = allSamples.get(i);
                    if (currentSample.getGroupName().equals(comparedSample.getGroupName())) {
                        groupExists = true;
                        // Existing group AND target:
                        if (comparedSample.getTargetMap().containsKey(targetName)) {
                            try {
                                comparedSample.getTargetMap().get(targetName).add(Double.parseDouble(delimitedLine[9]));
                            } catch (NumberFormatException e) {
                                comparedSample.getTargetMap().get(targetName).add(0.0);
                            }
                            break;
                            // Contains an existing group, but not an existing target
                        } else {
                            comparedSample.getTargetMap().put(targetName, ctArray);
                            break;
                        }
                    }
                }

                // Does not contain an existing group
                if (!groupExists) {
                    allSamples.add(currentSample);
                }

                currentLine = reader.readLine();
                Matcher matcher = pattern.matcher(currentLine);
                matchFound = matcher.find();
            }

            writer.write(",Group,");

            // Writes target names for header
            for (Map.Entry<String, ArrayList<Double>> targetEntry : allSamples.get(0).getTargetMap().entrySet()) {
                writer.write(targetEntry.getKey() + ",");
            }

            writer.write("dCt,dCt Average,dCt SD,ddCt,Fold Change,Fold Change Average,Fold Change SD,p value");
            writer.newLine();

            // Creation of dCt ArrayList
            for (Sample sample : allSamples) {
                sample.calculatedCt();
                sample.calculatedCtAverage();

                // Chooses which sample to normalize to
                sample.setNormalizationValue(allSamples.get(0).getdCtAverage());

                sample.calculatedCtStdDev();
                sample.calculateddCt();
                sample.calculateFoldChanges();
                sample.calculateFoldChangeAverage();
                sample.calculateFoldChangeStdDev();
            }

            // All the data
            for (int i = 0; i < allSamples.size(); i++) {

                Sample currentSample = allSamples.get(i);

                for (int j = 0; j < 3; j++) {
                    writer.write(",");
                    writer.write(currentSample.getGroupName() + ",");

                    for (Map.Entry<String, ArrayList<Double>> targetMap : currentSample.getTargetMap().entrySet()) {
                        writer.write(Double.toString(targetMap.getValue().get(j)) + ",");
                    }
                    writer.write(Double.toString(currentSample.getdCtArray().get(j)) + ",");

                    if (j == 0) {
                        writer.write(Double.toString(currentSample.getdCtAverage()) + ",");
                        writer.write(Double.toString(currentSample.getdCtStdDev()) + ",");
                        writer.write(Double.toString(currentSample.getddCtArray().get(j))+ ",");
                        writer.write(Double.toString(currentSample.getFoldChanges().get(j)) + ",");
                        writer.write(Double.toString(currentSample.getFoldChangeAverage()) + ",");
                        writer.write(Double.toString(currentSample.getFoldChangeStdDev()) + ",");
                    } else {
                        writer.write(",,");
                        writer.write(Double.toString(currentSample.getddCtArray().get(j)) + ",");
                        writer.write(Double.toString(currentSample.getFoldChanges().get(j)) + ",");
                    }

                    writer.newLine();
                }
            
            }

            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
