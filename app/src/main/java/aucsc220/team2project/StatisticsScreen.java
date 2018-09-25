package aucsc220.team2project;
/*
 * This class is used to access the statistics for each difficulty. Currently, it only includes
 * the number of wins for each difficulty. It also includes a button that resets all the stats
 * if the user decides to.
 *
 * Method Information:
 *      onCreate(bundle)
 *          Sets initial values for this screen, puts the screen as the shown screen.
 *          It also sets the corresponding stat to its textView.
 *      resetStats(View)
 *          When this button is clicked, it displays a confirmation screen that informs the
 *          user if they would want to reset the stats. If yes, then it sets each stat for each
 *          difficulty to 0. If no, then it changes nothing and the popup disappears
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class StatisticsScreen extends AppCompatActivity {
    SharedPreferences statKeeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_screen);
        statKeeper = getSharedPreferences("Stats", Context.MODE_PRIVATE);
        int easyWinCounter = statKeeper.getInt("easyWords.txt", 0);
        int mediumWinCounter = statKeeper.getInt("mediumWords.txt", 0);
        int hardWinCounter = statKeeper.getInt("hardWords.txt", 0);
        TextView easyWins = findViewById(R.id.easyStats);
        easyWins.setText("You have won on easy " + easyWinCounter + " times.");
        TextView mediumWins = findViewById(R.id.mediumStats);
        mediumWins.setText("You have won on medium " + mediumWinCounter + " times.");
        TextView hardWins = findViewById(R.id.hardStats);
        hardWins.setText("You have won on hard " + hardWinCounter + " times.");
    }


    /**
     * This is a button that confirms with the user if they want to reset their stats
     */
    public void resetStats(View view){
        AlertDialog.Builder resetStatsMessage = new AlertDialog.Builder(this);
        resetStatsMessage.setIcon(R.drawable.warningicon).setTitle("Really Reset Stats?");
        resetStatsMessage
                .setNeutralButton("No, don't reset my stats!           ",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Yes, I want to reset my stats      ",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int defaultWins = 0;
                        SharedPreferences.Editor statEditor = statKeeper.edit();
                        statEditor.putInt("easyWords.txt", defaultWins);
                        statEditor.putInt("mediumWords.txt", defaultWins);
                        statEditor.putInt("hardWords.txt", defaultWins);
                        statEditor.commit();
                        dialogInterface.dismiss();
                        //restarts the activity
                        finish();
                        startActivity(getIntent());
                    }
                })
                .create();
        resetStatsMessage.show();
    }

}

