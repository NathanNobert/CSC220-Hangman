/**
 * This is the Database java class.
 * This class gets the random word and sets that up on the screen. It reads through the text files
 * called and sends this information to the screen and to the GameScreen.java class
 *
 * Methods:
 *      database()
 *          getter method for database
 *
 *      getWord()
 *          getter method for the word
 *
 *      getHint()
 *          getter method for the hint
 *
 *      getWordToGuess()
 *          getter method for the random word
 *
 *      showHiddenRandomWord()
 *          calls the methods to get the random word
 *
 *      getRandomNumber()
 *          Gets a number between 1 and length of words file size(currently 30 for each difficulty)
 *
 *      gettingRandomWordFromDatabase()
 *          Reads the file, sets the word to the line number of the random number. Also does the
 *          same for the hint, the hint is on the same line number as the word
 *
 *      outputRandomWordToScreen()
 *          Display the word from gettingRandomWordFromDataBase() to the screen as a textview.
 *          Then sets the alpha of the word to 0.0, which makes the word invisible
 *
 *      makeHiddenWordFromDatabase()
 *          This does a copy of outputRandomWordToScreen(), except it changes the word to
 *          underscores to show how many letters in each word.
 *
 */
package aucsc220.team2project;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * This class is used to access our database of random words. Currently we are only using one
 * text file of words. This class is called by the getRandomWord() method. From there it
 * gets a random number and will iterate through the list of words and assign the random word
 * to be returned back.
 *
 * Created by Nathan on 2017-11-14.
 *
 * Update: Moved all the methods for our database that starts the game.
 * Paolo 12/7/2017
 */
public class Database extends AppCompatActivity {
    final static int LENGTH_OF_WORDS_FILE = 30;
    private String wordFromDatabase;
    private String hintForWord;
    Context mContext;
    TextView wordToGuess;
    Activity nActivity;

    /*
    ========================================
    Getter and setter methods
     */
    public Database(Context context, Activity activity) {
        this.mContext = context;
        this.nActivity = activity;
    }

    public String getWord() {
        return wordFromDatabase;
    }

    public String getHint() {
        return hintForWord;
    }

    public TextView getWordToGuess() {
        return wordToGuess;
    }
    /*
    ========================================
    Getter and setter methods
     */
    
    public void showHiddenRandomWord() {
        gettingRandomWordFromDatabase();
        outputRandomWordToScreen();
        makeHiddenWordFromDatabase();
    }

    /**
     * This method gets a random number between 1 and the file size.
     */
    private static int getRandomNumber() {
        int randomNumberToGetRandomWord;
        Random randomNum = new Random();
        randomNumberToGetRandomWord = randomNum.nextInt(LENGTH_OF_WORDS_FILE) + 1;
        return randomNumberToGetRandomWord;
    }//getRandomNumber

    /**
     * This function uses a random number, iterates through the file until it reaches that
     * random number, then the word at that line number will be used for outputRandomWordToScreen()
     */
    private void gettingRandomWordFromDatabase() {
        BufferedReader readerForWord = null;
        BufferedReader readerForHint;
        try {
            readerForWord = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open(MainActivity.wordFile)));
            readerForHint = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open(MainActivity.hintFile)));

            //read one line at a time
            String lineInDatabaseFile;
            String lineInHintFile;
            int counter = getRandomNumber();
            int iterate = 1;
            //go through whole file, but only append
            while ((lineInDatabaseFile = readerForWord.readLine()) != null
                    && ((lineInHintFile = readerForHint.readLine()) != null)) {
                if (counter == iterate) {
                    wordFromDatabase = lineInDatabaseFile;
                    hintForWord = lineInHintFile;
                }
                iterate++;
            }
        } catch (IOException e) {
            //popup error
            Toast.makeText(getApplicationContext(), "Error reading file!", Toast.LENGTH_LONG).show();
        } finally {
            if (readerForWord != null) {
                try {
                    readerForWord.close();
                } catch (IOException e) {
                    //popup error
                    Toast.makeText(getApplicationContext(), "Reached a null area in file!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * This function prints out the random word read in from the file as each character.
     */
    private void outputRandomWordToScreen() {
        wordToGuess = nActivity.findViewById(R.id.randomWordTextView);
        wordToGuess.setText("");
        //printing out the char array to text view
        for (int i = 0; i < wordFromDatabase.length(); i++) {
            wordToGuess.append(" ");
        }
        //set word invisible
        wordToGuess.setAlpha(1.0f);
    }//outputRandomWordToScreen

    /**
     * This function makes a copy of the random word, but instead of showing the word, at each
     * character it places an underscore
     */
    private void makeHiddenWordFromDatabase() {
        TextView HiddenWordToGuess = nActivity.findViewById(R.id.hiddenRandomWordTextView);
        HiddenWordToGuess.setText("");
        //printing out the char array to text view
        for (int i = 0; i < wordFromDatabase.length(); i++) {
            //make underscores for the length of the word
            HiddenWordToGuess.append("_");
        }
    }//makeHiddenWordFromDatabase
}