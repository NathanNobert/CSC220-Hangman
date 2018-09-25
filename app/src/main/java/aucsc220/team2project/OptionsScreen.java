package aucsc220.team2project;

/**
 * Created by Nathan on 2017-11-09.
 *
 * This class will be used as our options screen where currently the user can turn the sound on
 * or off.
 *
 * method information:
 *      onCreate(bundle)
 *          This sets up the activity and displays the switch for sound options.
 *      isSoundOn()
 *          Returns the value of the state of the switch. True if the switch is turned on
 *          and false if the switch is turned off. This method is used in GameScreen to check
 *          if sound should be played when a button is pressed.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * OptionsScreen
 * Created by Mark Kaziro
 * Edited by Katharina Reddecop 2017-12-04
 */
public class OptionsScreen extends AppCompatActivity {

    public static Boolean switchState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_screen);
        Switch soundSwitch = findViewById(R.id.soundSwitch);
        //Created Listener for the switch button.
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    switchState = true;
                }else{
                    switchState = false;
                }//else
            }
        });
        //Code for switch listener found on the following website:
        // https://android--code.blogspot.ca/2015/08/android-switch-button-listener.html
    }//onCreate

    /**
     * isSoundOn()
     *      Returns the value of the state of the switch. True if the switch is turned on
     *      and false if the switch is turned off. This method is used in GameScreen to check
     *      if sound should be played when a button is pressed.
     * @return True if switch is on or false if switch is off.
     */
    public static boolean isSoundOn(){
        return switchState;
    }//isSoundOn
}//OptionsScreen class
