import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Driver{
    public static void main(String[] args) throws FileNotFoundException{
        // Make an array of all the emails in the csv
        System.out.println("Making emails...");
        Email[] emails = getEmailList();

        // Shuffle the emails
        shuffle(emails);

        // Split the emails into training and predictions
        Email[] training = new Email[emails.length/5*4];
        Email[] predictions = new Email[emails.length-training.length];

        // Fill both arrays with emails
        for(int i = 0; i < training.length; i++){
            training[i] = emails[i];
        }

        for(int i = 0; i < predictions.length; i++){
            predictions[i] = emails[i+training.length];
        }

        // Make the spamPredictor
        SpamPredictor predictor = new SpamPredictor();
        
        // Train and test the predictor
        System.out.println("Training Predictor...");
        train(training, predictor);
        System.out.println("Testing Predictor...");
        test(predictions, predictor);
    }

    // Method to make an array of emails from a csv
    public static Email[] getEmailList() throws FileNotFoundException{
        // Make an array list and make a scanner on the csv
        ArrayList<Email> emailsList = new ArrayList<>();
        File dataFile = new File("Data/spam_or_not_spam.csv");
        Scanner reader = new Scanner(dataFile);
        
        // Get rid of the header line
        reader.nextLine();

        // Add all emails to the email list
        while(reader.hasNextLine()){
            emailsList.add(new Email(reader.nextLine()));
        }

        // Make an array to hold the emails
        reader.close();
        Email[] emails = new Email[emailsList.size()];

        // Convert the arraylist to an array
        for(int i = 0; i < emails.length; i++){
            emails[i] = emailsList.get(i);
        }

        // Return the array of emails
        return emails;
    }

    // A method to train a SpamPredictor object on a set of emails
    public static void train(Email[] emails, SpamPredictor predictor){
        // Add data to the predictor
        for(int i = 0; i < emails.length; i++){
            predictor.addData(emails[i]);
        }

        // Update the word chances after getting the data
        predictor.updateChances();
    }

    // A method to test a SpamPredictor object on a set of emails
    public static void test(Email[] emails, SpamPredictor predictor){
        // Make values to hold the results of each test
        double correct = 0;
        double falsePositive = 0;
        double falseNegative = 0;

        // Test each email and see if the predictor is correct
        for(int i = 0; i < emails.length; i++){
            if(predictor.makePrediction(emails[i]) == emails[i].getIsSpam()){
                correct++;
            }
            else if(emails[i].getIsSpam()){
                falseNegative++;
            }
            else{
                falsePositive++;
            }
        }

        // Print out the results
        System.out.println("% Correct: "+(correct/emails.length));
        System.out.println("% False Negative: "+(falseNegative/emails.length));
        System.out.println("% False Positive: "+(falsePositive/emails.length));
    }

    // A method to shuffle an array of emails
    public static void shuffle(Email[] emails){
        Random random = new Random();

        for(int i = 0; i < emails.length; i++){
            int j = random.nextInt(emails.length);
            Email temp = emails[i];
            emails[i] = emails[j];
            emails[j] = temp;
        }
    }
}