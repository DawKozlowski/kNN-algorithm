import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> trainingList = readFile(args[0]);
        ArrayList<String> testList = readFile(args[1]);
        ArrayList<String> testRightAnswers=rightAnswers(testList);
        int numberOfNeighbors=Integer.parseInt(args[2]);
        ArrayList<String> answers= calculatingDecisionAttribute(trainingList, testList, numberOfNeighbors);

        int j=0;
        for(int i=0; i<testList.size(); i++){
            if(answers.get(i).equals(testRightAnswers.get(i))){
                j++;
            }
        }
        double precision=j*100/testList.size();
        System.out.println("Accuracy: "+precision+"%");
        System.out.println("Right Answers");
        System.out.println(testRightAnswers);
        System.out.println("Answers");
        System.out.println(answers);
        while(true) {
            System.out.println("\n Insert vector");
            Scanner sc = new Scanner(System.in);
            String vector = sc.next();
            if(vector.equals("quit")){
                System.exit(1);
            }
            if(vector.split(",").length!=testList.get(1).split(",").length-1){
                throw new IllegalArgumentException("Wrong number of attributes");
            }
            ArrayList<String> vectorArray= new ArrayList<>();
            vectorArray.add(vector);
            ArrayList<String> answer= calculatingDecisionAttribute(trainingList, vectorArray, numberOfNeighbors);
            System.out.println("ANSWER-> "+answer);
        }
    }



    public static ArrayList<String> readFile(String file){
        ArrayList<String> list =  new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                list.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<String> rightAnswers(ArrayList<String> testList){
        int indexOfAttribute = testList.get(0).split(",").length;
        ArrayList<String> listOfAnswers = new ArrayList<>();
        for(String line : testList){
            String answer= line.split(",")[indexOfAttribute-1];
            listOfAnswers.add(answer);
        }
        return  listOfAnswers;
    }

    public static double[] convert(String[] arr){
        int size = arr.length;
        double [] arr2 = new double [size];
        for(int i=0; i<size-1; i++) {
            arr2[i] = Double.parseDouble(arr[i]);
        }
        return arr2;
    }


    public static double euclideanDistance(String[] testAttributes, String[] attributes, int length){
        double[] test=convert(testAttributes);
        double[] atr=convert(attributes);
        double distance=0;
        for(int i=0;i<length;i++){
            distance+=Math.sqrt(Math.pow(test[i]-atr[i], 2));
        }
        return distance;
    }

    private static String mostCommonElement(ArrayList<String> list) {
        Map<String, Integer> map = new HashMap<>();
        for(int i=0; i< list.size(); i++) {
            Integer frequency = map.get(list.get(i));
            if(frequency == null) {
                map.put(list.get(i), 1);
            } else {
                map.put(list.get(i), frequency+1);
            }
        }
        String mostCommon = null;
        int maxVal = 0;
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            if(entry.getValue() > maxVal) {
                mostCommon = entry.getKey();
                maxVal = entry.getValue();
            }
        }
        return mostCommon;
    }


    public static ArrayList<String> calculatingDecisionAttribute(ArrayList<String> trainingList, ArrayList<String> testList, int numberOfNeighbors){
        int lengthOfAttributes = trainingList.get(0).split(",").length;
        ArrayList<String> answers = new ArrayList<>();
        for(String line : testList){
            ArrayList<Neighbor> neighbors = new ArrayList<>();
            String[] testAttributes=line.split(",");
            for(String trainingLine : trainingList){
                String[] attributes=trainingLine.split(",");
                double dist = euclideanDistance(testAttributes, attributes, lengthOfAttributes-1);
                neighbors.add(new Neighbor(dist, attributes[lengthOfAttributes-1]));
            }
            Collections.sort(neighbors);
            ArrayList<String> result = new ArrayList<>();
            for(int i =0; i<numberOfNeighbors;i++){
                result.add(neighbors.get(i).atribute);
            }
            answers.add(mostCommonElement(result));
        }
        return answers;
    }



}
