import java.util.ArrayList;

public class SpamPredictor {
    private ArrayList<String> spamWords; // Holds the list of words present in spam emails
    private ArrayList<Integer> spamWordFrequency; // Holds the amount of emails each word is present in
    private ArrayList<Double> probabilityOfSpamGivenWord; // Holds the probability that an email is spam given a word -> P(S|W)
    private ArrayList<String> hamWords; // Holds the list of words present in ham emails
    private ArrayList<Integer> hamWordFrequency; // Holds the amount of emails each word is present in
    private ArrayList<Double> probabilityOfHamGivenWord; // Holds the probability that an email is ham given a word -> P(H|W)
    private int totalHamEmails; // Holds the total amount of ham emails
    private int totalSpamEmails; // Holds the total amount of spam emails

    public SpamPredictor(){
        spamWords = new ArrayList<>();
        hamWords = new ArrayList<>();
        spamWordFrequency = new ArrayList<>();
        hamWordFrequency = new ArrayList<>();
        probabilityOfSpamGivenWord = new ArrayList<>();
        probabilityOfHamGivenWord = new ArrayList<>();
        totalSpamEmails = 0;
        totalHamEmails = 0;
    }

    // Adds data from email to the total data
    public void addData(Email email){
        // Get the words
        String[] emailWords = email.getWordList();
        
        // If the email is spam, add it to the spam data
        if(email.getIsSpam()){
            totalSpamEmails++;

            // For each word, add it to the data
            for(int i = 0; i < emailWords.length; i++){
                String word = emailWords[i];

                // If the word is already in spam, add 1 to its frequency. Otherwise add it to spam with frequency 1
                if(spamWords.contains(word)){
                    int index = spamWords.indexOf(word);
                    spamWordFrequency.set(index, spamWordFrequency.get(index)+1);
                }
                else{
                    spamWords.add(word);
                    spamWordFrequency.add(1);
                }
            }

            return;
        }

        // If the email is not spam, it is ham
        // Add 1 to the total amount of ham emails
        totalHamEmails++;

        // For each word in ham, add its data
        for(int i = 0; i < emailWords.length; i++){
            String word = emailWords[i];

            // If the word is already in h, add 1 to its frequency. Otherwise add it to spam with frequency 1
            if(hamWords.contains(word)){
                int index = hamWords.indexOf(word);
                hamWordFrequency.set(index, hamWordFrequency.get(index)+1);
            }
            else{
                hamWords.add(word);
                hamWordFrequency.add(1);
            }
        }
    }

    // A method to update the probabilityOf(Ham/Spam)GivenWord fields
    public void updateChances(){
        // Holds the chance that any random email is spam or ham
        double spamChance = (totalSpamEmails+0.0)/(totalHamEmails+totalSpamEmails);
        double hamChance = (totalHamEmails+0.0)/(totalHamEmails+totalSpamEmails);

        // Fix potential divide by 0 issue by making totalSpamEmails and totalHamEmails = 1 if they are equal to 0
        totalSpamEmails = Math.max(totalSpamEmails, 1);
        totalHamEmails = Math.max(totalHamEmails, 1);

        // Go through every word in spam
        for(int i = 0; i < spamWords.size(); i++){
            String word = spamWords.get(i);

            // Get the proportion of spam emails that the word is present in
            double spamWordProportion = (spamWordFrequency.get(i)+0.0)/totalSpamEmails;

            // Check if the word is present in ham, if it is get that proportion. Otherwise the proportion is 0 (0/totalHamEmails)
            double hamWordProportion;

            if(hamWords.contains(word)){
                hamWordProportion = (hamWordFrequency.get(hamWords.indexOf(word))+0.0)/totalHamEmails;
            }
            else{
                hamWordProportion = 0;
            }

            // Calculate the chance of the word being spam using formula 2 of the attached paper
            double chanceOfSpam = (spamWordProportion*spamChance)/((spamWordProportion*spamChance)+(hamWordProportion*hamChance));

            probabilityOfSpamGivenWord.add(chanceOfSpam);
        }

        // Loop through every word in ham
        for(int i = 0; i < hamWords.size(); i++){
            String word = hamWords.get(i);

            // Get the proportion of ham emails that the word is present in
            double hamWordProportion = (hamWordFrequency.get(i)+0.0)/totalHamEmails;

            // Check if the word is present in spam, if it is get that proportion. Otherwise the proportion is 0 (0/totalSpamEmails)
            double spamWordProportion;

            if(spamWords.contains(word)){
                spamWordProportion = (spamWordFrequency.get(spamWords.indexOf(word))+0.0)/totalSpamEmails;
            }
            else{
                spamWordProportion = 0;
            }

            // Calculate the chance of the word being ham using formula 2 of the attached paper
            double chanceOfHam = (hamWordProportion*hamChance)/((hamWordProportion*hamChance)+(spamWordProportion*spamChance));

            probabilityOfHamGivenWord.add(chanceOfHam);
        }
    }
    
    // A method to make a prediction about any given email
    public boolean makePrediction(Email email){
        // Get the list of words from the email
        String[] emailWords = email.getWordList();
        
        // Hold the total word chances multiplied together and their inverses for both ham and spam (see formula 3 of attached paper)
        double totalSpamChance = 1;
        double inverseSpamChance = 1;
        double totalHamChance = 1;
        double inverseHamChance = 1;

        // Hold a value for a tiebreaker if the end results are equal
        int tieBreaker = 0;
        
        // Go through every word in the email
        for(String word:emailWords){
            // Check if the word is in the spam data set
            if(spamWords.contains(word)){
                // If it is, update the given spamChance and inverseSpamChance
                totalSpamChance *= probabilityOfSpamGivenWord.get(spamWords.indexOf(word));
                inverseSpamChance *= (1-(probabilityOfSpamGivenWord.get(spamWords.indexOf(word))));
            }
            else if(hamWords.contains(word)){
                // Otherwise if ham does and spam does not, update the tiebreaker
                tieBreaker--;
            }
            if(hamWords.contains(word)){
                // Update ham chance and inverse ham chance
                totalHamChance *= probabilityOfHamGivenWord.get(hamWords.indexOf(word));
                inverseHamChance *= (1-(probabilityOfHamGivenWord.get(hamWords.indexOf(word))));
            }
            else if(spamWords.contains(word)){
                // Update tiebreaker
                tieBreaker++;
            }
        }

        // Calculate the final spam and ham chance using formula 3
        double spamChanceForEmail = totalSpamChance/(totalSpamChance+inverseSpamChance);
        double hamChanceForEmail = totalHamChance/(totalHamChance+inverseHamChance);

        // If the totals are equal, fall back to the tiebreaker
        if(spamChanceForEmail == hamChanceForEmail){
            // If tiebreaker > 0, then more spam words that weren't ham words were found then the inverse
            return tieBreaker > 0;
        }

        // Return true if the spam total is greater then the ham total, or false otherwise
        return spamChanceForEmail > hamChanceForEmail;
    }
}
