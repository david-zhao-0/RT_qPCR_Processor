public class AlternateProcessor {
    public static void alternativelyProcess() {
        // while (currentLine != null) {

            //     currentLine = reader.readLine();
            //     String[] delimitedLine = currentLine.split(",");

            //     String currentSampleGroup = delimitedLine[1];
            //     String currentSampleTarget = delimitedLine[2];
            //     Double currentSampleCt;

            //     try {
            //         currentSampleCt = Double.parseDouble(delimitedLine[9]);
            //     } catch (NumberFormatException e) {
            //         currentSampleCt = 0.0;
            //     }

            //     Target currentTarget = new Target(currentSampleTarget, currentSampleCt);
            //     Sample currentSample = new Sample(currentSampleGroup, currentTarget);

            //     boolean groupExists = false;
            //     boolean targetExists = false;

            //     // Checks for existing groups
            //     for (int i = 0; i < allGroups.size(); i++) {
            //         Sample comparedSample = allGroups.get(i);
            //         if (currentSampleGroup.equals(comparedSample.getGroupName())) {
            //             groupExists = true;

            //             //Checks for existing targets within group
            //             for (int j = 0; j < comparedSample.getTargets().size(); j++) {
            //                 Target comparedTarget = comparedSample.getTargets().get(j);
            //                 if (currentSampleTarget.equals(comparedTarget.getTargetName())) {
            //                     targetExists = true;
            //                     comparedTarget.getCtArray().add(currentSampleCt);
            //                     break;
            //                 }
            //             }

            //             if (!targetExists) {
            //                 comparedSample.addTarget(currentTarget);
            //                 break;
            //             }

            //         }

            //         if (groupExists) {
            //             break;
            //         }

            //     }
                
            //     if (!groupExists) {
            //         allGroups.add(currentSample);
            //     }
            // }        
    }
}