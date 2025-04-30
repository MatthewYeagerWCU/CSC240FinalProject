import java.util.ArrayList;

public class Email {
    private boolean spam; // True if spam, false if ham
    private String[] words; // Contains each unique word
    private String[] wordData; // Contains the words as a " " separated list

    public Email(String input){
        // Separate the email into the spam value and list of words
        spam = input.charAt(input.length()-1) == '1';
        input = input.substring(0, input.lastIndexOf(","));

        wordData = input.split(" ");

        // Get the list of unique words
        getUniqueWords();
    }

    public void getUniqueWords(){
        // Make an array to hold the list of unique words
        ArrayList<String> wordList = new ArrayList<>();

        // For each word, check if the word is already in the list
        for(String word:wordData){
            // If the word isn't in the list, add it
            if(!wordList.contains(word)){
                wordList.add(word);
            }
        }

        // Make the final array to hold the values
        words = new String[wordList.size()];

        // add each value to the final array
        for(int i = 0; i < wordList.size(); i++){
            words[i] = wordList.get(i);
        }
    }

    public String[] getWordList(){
        return words;
    }

    public String[] getWordData(){
        return wordData;
    }

    public boolean getIsSpam(){
        return spam;
    }

    public String toString(){
        String output = " ";
        for(String word:wordData){
            output += " "+word;
        }

        return output.substring(2);
    }
}
