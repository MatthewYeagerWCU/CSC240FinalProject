import java.util.ArrayList;

public class Email {
    private String[] words; // Holds the email as a String array of the email separated by spaces
    private boolean spam; // True if spam, false if ham
    private Object[][] wordFrequency; // holds word frequency of format {(String -> word), (Int -> word count)}

    public Email(String input){
        // Separate the email into the spam value and list of words
        spam = input.charAt(input.length()-1) == '1';
        input = input.substring(0, input.lastIndexOf(","));

        words = input.split(" ");

        // Calculate word frequency with the list of words
        calculateWordFrequency();
    }

    public void calculateWordFrequency(){
        // Make an array to hold both values and the amount of that value
        ArrayList<String> wordList = new ArrayList<>();
        ArrayList<Integer> wordAmount = new ArrayList<>();

        // For each word, check if the word is already in the list
        for(String word:words){
            // If the word is in the list, increase the amount of that word, if not add it to the list with the value 1
            if(wordList.contains(word)){
                int index = wordList.indexOf(word);
                wordAmount.set(index, wordAmount.get(index)+1);
            }
            else{
                wordList.add(word);
                wordAmount.add(1);
            }
        }

        // Make the final 2d array to hold the values
        wordFrequency = new Object[wordList.size()][2];

        // Each array in the 2d array is an array of size 2 containing {(word), (word frequency)}
        for(int i = 0; i < wordList.size(); i++){
            wordFrequency[i][0] = wordList.get(i);
            wordFrequency[i][1] = wordAmount.get(i);
        }
    }

    public Object[][] getWordFrequency(){
        return wordFrequency;
    }

    public boolean getIsSpam(){
        return spam;
    }
}
