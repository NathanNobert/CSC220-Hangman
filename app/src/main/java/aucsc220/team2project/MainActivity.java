/*
 * Team 2's Hangman Project.
 * Project start date for program section was November 6, 2017.
 *
 * Team members:
 *      Nathan Nobert
 *      Katharina Reddecop
 *      Mark Kaziro
 *      Paolo Reyes
 *
 * Method Information:
 *      onCreate(bundle)
 *          Sets initial values for this screen, puts the screen as the main screen
 *      gotoGameScreen()
 *          When a button is clicked from main screen, this sends an intent to start the game
 *      gotoOptionsScreen()
 *          When the options button is clicked from main screen, this sends an intent to options
 *      changeDifficulty()
 *          When the difficulty button is clicked, a popup screen appears giving you the ability
 *          to choose the difficulty between easy, medium, or hard
 *
 */
package aucsc220.team2project;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    //created a string to store the selected difficulty. default is easy. by mark
    public static String wordFile = "easyWords.txt"; //default difficulty
    public static String hintFile = "easyWordsHints.txt";
    public static int difficultyNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called with the "QuickPlay" button.
     * Starts a new activity to open the GameScreen class from the MainActivity.
     * @param view
     */
    public void gotoGameScreen (View view){
        Intent intent = new Intent (this, GameScreen.class);
        startActivity(intent);
    }

    /**
     * This method is called with the "QuickPlay" button.
     * Starts a new activity to open the GameScreen class from the MainActivity.
     * @param view
     */
    public void gotoStatisticsScreen (View view){
        Intent intent = new Intent (this, StatisticsScreen.class);
        startActivity(intent);
    }
    /**
     * This method is called with the "Options" button.
     * Starts a new activity to open the OptionsScreen class from the MainActivity.
     * @param view
     */
    public void gotoOptionsScreen (View view){
        Intent intent = new Intent (this, OptionsScreen.class);
        startActivity(intent);

    }

    /**
     * changeDifficulty(view)
     * This function changes the difficulty of the game for the uesr using an
     * alert dialogue to pick from 3 levels of difficulty
     * started by Mark Kaziro
     * Edited by Paolo Reyes 12/1/2017
     * Used this method to also change to the corresponding hint list for the chosen difficulty
     * @param view DIFFICULTY Button
     */
    public void changeDifficulty(View view){
        AlertDialog.Builder difficultyMessage = new AlertDialog.Builder(this);
        difficultyMessage.setMessage("Please choose the difficulty you prefer")
                .setNeutralButton("Easy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wordFile = "easyWords.txt";
                        hintFile = "easyWordsHints.txt";
                        difficultyNum = 1;
                        Toast.makeText(getApplicationContext(),"Difficulty has " +
                                        "been set to easy.",
                                Toast.LENGTH_SHORT).show();

                        //set difficulty to easy
                    }
                })

                .setNegativeButton("Medium           ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wordFile = "mediumWords.txt";
                        hintFile = "mediumWordsHints.txt";
                        difficultyNum = 2;
                        Toast.makeText(getApplicationContext(),"Difficulty has been " +
                                        "set to medium.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Hard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wordFile = "hardWords.txt";
                        hintFile = "hardWordsHints.txt";
                        difficultyNum = 3;
                        Toast.makeText(getApplicationContext(),"Difficulty has been set " +
                                        "to hard.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        difficultyMessage.show();
    }//changeDifficulty

}
