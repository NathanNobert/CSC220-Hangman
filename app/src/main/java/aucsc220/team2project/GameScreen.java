package aucsc220.team2project;

/*
 * Created by Nathan on 2017-11-06.
 *
 * This class will be used as our file for the actual gameplay. Most activities and functions will
 * be from this class. The MainActivity class will be our home screen and this will be where the
 * user plays the game
 *
 * Method Information:
 *      onCreate(Bundle)
 *          Sets initial values on start of activity
 *
 *      keyBoardButtonClicked(View view)
 *          This is the method that sets a character value for each keyboard click. On each click
 *          it will call methods to check if the letter is in the word
 *
 *      displayLettersGuessed()
 *          This method will display a letter on the screen over the underscore if the keyboard
 *          letter selected is in the word
 *
 *      showGameOverMessage()
 *          When the amount of fails has been exceeded and the hangman is built, a popup will
 *          tell the user the game is over, and give them the option to return to main menu
 *          or to play again
 *
 *      hintButtonClicked(View view)
 *          This method activates when the hint button has been clicked, it will show the hint
 *          message as a pop up
 *
 *      inCurrentWord(char value)
 *          This method checks the letter passed in from keyBoardButtonClicked and compares
 *          the letter to the word. Return a true if the letter exists in the word, false if not
 *
 *      showPopup(View v)
 *          This is the menu on the top left of the screen to show a popup to reach the main menu
 *          or the options screen
 *
 *      onMenuItemClick(MenuItem item)
 *          This actually sends the user to the main menu or the options screen
 *
 *      displayCurrentHangman()
 *          This is a switch case to change the hangman image based on the amount of fails the user
 *          has made
 *
 *      checkHintButton()
 *          This will change the color of the hint button after the user has more than 3 fails
 */


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import at.markushi.ui.CircleButton;
import pl.droidsonroids.gif.GifImageView;


/**
 *This is the GameScreen class, all the functions for running the game are in this class
 */
public class GameScreen extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    //class variables
    static int lettersGuessed;
    static int fails;
    TextView wordToGuess;
    String hintForWord;
    String wordFromDatabase;

    /**
     * This method occurs whenever the activity is started. This is where we restart our class
     * variables such as the letters guessed and fails. When a second game is played, the
     * variables will be reset to prevent errors or bugs.
     * @param savedInstanceState - the state of the activity when it starts
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        onStart();
        displayCurrentDifficulty();
        lettersGuessed = 0;
        fails = 0;
        Database database = new Database(this, this);
        database.showHiddenRandomWord();
        wordFromDatabase = database.getWord();
        hintForWord = database.getHint();
        wordToGuess = database.getWordToGuess();

    }//onCreate

    /**
     * Function to bring to the main menu, had to make it a function since
     * the code is used in multiple methods
     * Created by Paolo Reyes
     * November 22, 2017
     */
    public void goToMenuScreen (){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }//goToMenuScreen


    /**
     * keyBoardButtonClicked(view)
     * This function takes the value of the button clicked and calls inCurrentWord(char)
     * to check if the value of the button is in the word. And then increases the number
     * of fails and lettersGuessed respectively. It also checks the hintbutton to check if it
     * needs to change colors.
     *
     * started by Katharina Reddecop 2017-11-18
     *
     * After every click, it checks the state of the game, if it won,
     * call the showGameOverMessage() method when all the letters have been guessed.
     *
     * Edited by Paolo Reyes 2017/11/22
     * Edited by Mark Kaziro
     *
     * @param view keyboard Button.
     */
    public void keyBoardButtonClicked(View view){
        Button tempButton = (Button)view;
        char buttonValue;
        buttonValue = tempButton.getText().charAt(0);

        //play the sound when button is clicked
        if (OptionsScreen.isSoundOn()){
            final MediaPlayer clickSoundMP = MediaPlayer.create(this, R.raw.clicksound);
            clickSoundMP.start();
        }
        //Make alphabet button invisible, so that user can’t click it again.
        tempButton.setVisibility(View.INVISIBLE);

        if (!inCurrentWord(buttonValue)){

            fails += 1;
            displayCurrentHangman();
        }//if

        checkHintButton();

    }//keyBoardButtonClicked


    /**
     * displayLettersGuessed(char[] lettersGuessed)
     *     Updates the text variable of the textview that displays the letters guessed.
     *
     *     Created by Katharina Reddecop
     *
     */
    private void displayLettersGuessed(char[] lettersGuessed){
        StringBuilder newWordToDisplay = new StringBuilder();
        //Creates a new string with the new value added to display the letters guessed on the
        //screen
        for (int i = 0; i < lettersGuessed.length; i++) {
            newWordToDisplay.append(lettersGuessed[i]);
        }//for
        //Update the letters guessed on the screen
        wordToGuess.setText(newWordToDisplay);

    }//displayLettersGuessed

    /**
     * showGameOverMessage()
     * This function shows the game over message once called.
     * This activates if the amount of fails have exceeded the allowed amount (6).
     *
     * Created by Mark Kaziro on November 21, 2017
     * Edited by Paolo
     */
    private void showGameOverMessage(){
        int maxNumberOfFails = 6;
        AlertDialog.Builder gameOverMessage = new AlertDialog.Builder(this);
            //makes the end game screen not cancelable by outside touch
            gameOverMessage.setCancelable(false);
        if (fails == maxNumberOfFails){
            gameOverMessage.setIcon(R.drawable.warningicon).setTitle("GAMEOVER!")
            .setMessage("You have ran out of guesses! The word was " +
                    wordFromDatabase.toString().toUpperCase() + ".");
        }//if
        else{
            winForEachDifficulty(MainActivity.wordFile);
            gameOverMessage.setIcon(R.drawable.winningicon).setTitle("You Win!")
                    .setMessage("You have solved the hidden word! It was " +
                            wordFromDatabase.toString().toUpperCase() + ". " +
                            "Would you like to play again?");
        }// else
        gameOverMessage
                .setNeutralButton("Go to Main Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToMenuScreen();
                        dialogInterface.dismiss();
                    }
                })

                .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //restarts the activity
                        finish();
                        startActivity(getIntent());
                    }
                })
                .create();
        gameOverMessage.show();
        fails = 0;
    }//showGameOverMessage


    /**
     * This method sets the stats for each difficulty after a win occurs
     * @param difficultyLevel - the user chosen difficulty word file as a string
     */
    public void winForEachDifficulty(String difficultyLevel){
        SharedPreferences statKeeper = getSharedPreferences("Stats", Context.MODE_PRIVATE);
        SharedPreferences.Editor statKeeperEditor = statKeeper.edit();
        int easyWinCounter = statKeeper.getInt("easyWords.txt", 0);
        int mediumWinCounter = statKeeper.getInt("mediumWords.txt", 0);
        int hardWinCounter = statKeeper.getInt("hardWords.txt", 0);

        switch (difficultyLevel) {
            case "easyWords.txt":
                statKeeperEditor.putInt("easyWords.txt", (easyWinCounter + 1)).apply();
                break;
            case "mediumWords.txt":
                statKeeperEditor.putInt("mediumWords.txt", (mediumWinCounter + 1)).apply();
                break;
            case "hardWords.txt":
                statKeeperEditor.putInt("hardWords.txt", (hardWinCounter + 1)).apply();
                break;
        }
    }
    /**
     * hintButtonClicked(view)
     * This function displays a dialogue box that contains the hint to the word that is
     * being guessed on the screen.
     *
     * Started by Mark Kaziro on November 21, 2017
     * Finished by Paolo Reyes on November 21, 2017
     * @param view hint Button
     */
    public void hintButtonClicked(View view){
        //play sound if user wants
        if (OptionsScreen.isSoundOn()) {
            final MediaPlayer hintClickSoundMP = MediaPlayer.create(this, R.raw.hintbuttonclicksound);
            hintClickSoundMP.start();
        }
        //open dialogue box
        AlertDialog.Builder hint = new AlertDialog.Builder(this);
        hint.setMessage(hintForWord)
                .setNeutralButton("Continue Playing", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                })
                .setIcon(R.drawable.ic_lightbulb_outline_black_24dp)
                .setTitle("HINT")
                .create();
        hint.show();
    }//hintButtonClicked

    /**
     * inCurrentWord(char)
     * Goes through the list of characters that make up the word that is being guessed
     * and checks if the value entered as the parameter matches any of the letters in the
     * word.
     * started by Katharina Reddecop 2017-11-18
     *
     * Added the ability to count the number of letters to be used in showGameOverMessage.
     * This counter will be used to determine if the user has guessed all the letters in the word.
     * edited by Paolo Reyes 2017-11-22
     *
     * @param value letter that we are checking
     * @return if letter is in the word: true, else: false.
     */
    public boolean inCurrentWord(char value) {
        //Set up for loop to go through the list of characters of the current word to check
        //if the value is in it.
        String currentWord = wordFromDatabase.toUpperCase();
        Boolean inWord = false;

        char[] letterArray = currentWord.toCharArray();
        char[] lettersCorrectlyGuessed = wordToGuess.getText().toString().toCharArray();

        for(int i = 0; i < letterArray.length; i++){
            //This for loop needs to go through the whole loop in case there are two letters
            //That are the same in the word.
            if(letterArray[i] == value){
                lettersCorrectlyGuessed[i] = value;
                lettersGuessed += 1;
                inWord = true;
            }
        }
        displayLettersGuessed(lettersCorrectlyGuessed);
        if(lettersGuessed == (wordFromDatabase.length())){
            showGameOverMessage();
        }//If
        return inWord;

    }//inCurrentWord


    /**
     * showPopup(View)
     * Shows the menu in the game screen to allow the user to get back to the main menu or
     * go to options.
     * Paolo Reyes
     * @param v - the view for the popup
     */
    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(GameScreen.this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
    }//showPopup

    /**
     * onMenuItemClick(MenuItem)
     * Adds functionality to every option in the popup menu, each button redirects
     * user to the desired screen.
     * Paolo Reyes
     * @param item - The item selected
     * @return - true or false, true if item has been selected
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainmenu:
                goToMenuScreen();
                return true;
            case R.id.options:
                Intent goToOptions = new Intent (this, OptionsScreen.class);
                startActivity(goToOptions);
                return true;
            default:
                return false;
        }//switch
    }//onMenuClick


    /**
     * displayCurrentHangman(int)
     * Changed the displayed hangman based on how many wrong answers the user has given.
     * the default hangman is noose_1. It changes it from noose_1 to whichever noose based on
     * number of fails.
     * Started by Mark Kaziro on November 20, 2017
     */

    public void displayCurrentHangman(){
        GifImageView nooseImageView = findViewById(R.id.hangmanImage);
        switch(fails){
            case 1:
                nooseImageView.setImageResource(R.drawable.first);
                break;
            case 2:
                nooseImageView.setImageResource(R.drawable.second);
                break;
            case 3:
                nooseImageView.setImageResource(R.drawable.third);
                break;
            case 4:
                nooseImageView.setImageResource(R.drawable.fourth);
                break;
            case 5:
                nooseImageView.setImageResource(R.drawable.fifth);
                break;
            case 6:
                nooseImageView.setImageResource(R.drawable.ending);
                //while the animation is playing, disable all buttons on the screen
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                delayGameOverScreen();
                break;
            default:
                nooseImageView.setBackgroundResource(R.drawable.start);
                break;

        }//switch
    }//displayCurrentHangman


    /**
     * delayGameOverScreen()
     * This function delays the alertDialog for the game ending screen so that the animation
     * can be fully played before it pops up.
     *
     * Started by Paolo Reyes on December 12, 2017
     */
    private void delayGameOverScreen() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                showGameOverMessage();
            }
        }, 2000);

    }
    /**
     * checkHintButton()
     * This function changes the color of the hint button  in order to get the user's attention
     * so they can press the button and get a hint
     *
     * Started by Mark Kaziro on November 21, 2017
     */
    public void checkHintButton(){
        CircleButton hintButton = findViewById(R.id.hintButton);
        if(fails > 2) {
            hintButton.setColor(Color.GREEN);
        }//if
    }//checkHintButton

    /**
     * This method displays stars on the screen to show the current difficulty level the user
     * is on. 1 star for easy, 2 for medium, 3 for hard.
     */
    public void displayCurrentDifficulty(){
        ImageView difficultyImageView = findViewById(R.id.difficultyImage);
        switch(MainActivity.difficultyNum){
            case 1:
                difficultyImageView.setImageResource(R.drawable.onestar);
                break;
            case 2:
                difficultyImageView.setImageResource(R.drawable.twostars);
                break;
            case 3:
                difficultyImageView.setImageResource(R.drawable.threestars);
                break;
            default:
                difficultyImageView.setImageResource(R.drawable.onestar);
                break;
        }//switch
    }//displayCurrentHangman

}//class GameScreen
