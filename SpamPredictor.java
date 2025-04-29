public class SpamPredictor {

    private String[] spamWordArray = {"Free", "Winner", "Winning", "Congratulations", "Urgent", "Act now", "Limited time", "Guarantee", "Risk-free", "Click here", "Exclusive deal", "Offer expires", "Investment", "$$$", "Make money", "Cash bonus", "No cost", "Work from home", "Discount", "Buy now", "Call now", "Credit card", "Debt", "Earn", "Eliminate", "Cheap", "Luxury", "Trial", "Viagra", "Cialis", "Pharmacy", "Weight loss", "Miracle", "Lose fat", "Satisfaction", "Unsecured", "Pre-approved", "Important information"};
    List<String> spamWordsList = Arrays.asList(spamWordsArray);
    private int spamWordCount = 0;
    private int hamWordCount;
    private int wordCount = 0;
    private Email mail; 

    public SpamPredictor(Email mail){

        this.mail = mail;

           for (int i = 0; i < mail.wordFrequency.length; i++) {
    String word = ((String) wordFrequency[i][0]).toLowerCase();
    int count = (int) wordFrequency[i][1];
    wordCount += count;
    
    if (spamWordList.contains(word)) {
        spamWordCount += count;
    }
}

        
    }

    public boolean makePrediction(){

     double spamRatio = (double) spamWordCount / wordCount;

if (spamRatio > 0.1) { // Threshold can be adjusted
    return true;
} else {
    return false;
}

        
    }
    
    
}
