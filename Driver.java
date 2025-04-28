import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver{
    public static void main(String[] args) throws FileNotFoundException{
        // Make an array of all the emails in the csv
        Email[] emails = getEmailList();

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
        train(training, predictor);
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
        for(int i = 0; i < emails.length; i++){
            predictor.updateChances(emails[i]);
        }
    }

    // A method to test a SpamPredictor object on a set of emails
    public static void test(Email[] emails, SpamPredictor predictor){
        for(int i = 0; i < emails.length; i++){
            predictor.makePrediction(emails[i]);
        }
    }
}