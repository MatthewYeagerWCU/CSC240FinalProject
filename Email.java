import java.util.ArrayList;

public class Email {
    private String[] words; // Holds the email as a String array of the email separated by spaces
    private boolean spam; // True if spam, false if ham
    private Object[][] wordFrequency;

    public Email(String input){
        spam = input.charAt(input.length()-1) == '1';
        input = input.substring(0, input.lastIndexOf(","));

        words = input.split(" ");

        calculateWordFrequency();
    }

    public void calculateWordFrequency(){
        ArrayList<String> wordList = new ArrayList<>();
        ArrayList<Integer> wordAmount = new ArrayList<>();

        for(String word:words){
            if(wordList.contains(word)){
                int index = wordList.indexOf(word);
                wordAmount.set(index, wordAmount.get(index)+1);
            }
            else{
                wordList.add(word);
                wordAmount.add(1);
            }
        }

        wordFrequency = new Object[wordList.size()][2];

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
