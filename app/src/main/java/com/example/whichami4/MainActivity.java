package com.example.whichami4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    private int totalAnswers = 9;

    private ArrayList<String> answers = new ArrayList<>();

    // GUI
    private ImageButton[] colorBtns;

    @Override
    public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
        TextView ageText = findViewById(R.id.ageText);
        if (progress == seekbar.getMax())
            ageText.setText(seekbar.getMax() + "+");
        else
            ageText.setText(Integer.toString(progress));


        if (progress <= 17)
            this.answers.set(0, "pokemon");
        else if (progress >= 18 && progress <= 22)
            this.answers.set(0, "overwatch");
        else if (progress >= 23 && progress <= 30)
            this.answers.set(0, "league");
        else
            this.answers.set(0, "destiny");
        System.out.println("Current answers: " + this.answers);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.doneBtn) {
            System.out.println(this.answers);
            int pokemonScore = 0;
            int overwatchScore = 0;
            int leagueScore = 0;
            int destinyScore = 0;
            for (String answer : this.answers) {
                pokemonScore += (answer.equals("pokemon")) ? 1 : 0;
                overwatchScore += (answer.equals("overwatch")) ? 1 : 0;
                leagueScore += (answer.equals("league")) ? 1 : 0;
                destinyScore += (answer.equals("destiny")) ? 1 : 0;
            }
            String result;
            System.out.println("Scores: " + pokemonScore + ", " + overwatchScore + ", " + leagueScore + ", " + destinyScore);
            if (pokemonScore >= overwatchScore && pokemonScore >= leagueScore && pokemonScore >= destinyScore)
                result = "Pokemon";
            else if (overwatchScore >= leagueScore && overwatchScore >= destinyScore)
                result = "Overwatch";
            else if (leagueScore >= destinyScore)
                result = "League";
            else
                result = "Destiny";
            Intent intent = new Intent(this, DisplayGameActivity.class);
            intent.putExtra("result", result);
            startActivity(intent);
        }
        else {
            this.answers.set(1, v.getTag().toString());
            for (ImageButton btn : this.colorBtns) {
                btn.setEnabled(false);
                if (btn.getId() != v.getId())
                    btn.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Switch
        if (buttonView.getTag().toString().contains("switch")) {
            if (isChecked) {
                buttonView.setText("Yes");
                this.answers.set(6, "pokemon");
                this.answers.set(7, "overwatch");
            }
            else {
                buttonView.setText("No");
                this.answers.set(6, "league");
                this.answers.set(7, "destiny");
            }
        }
        // Checkboxes
        else {
            String tag = buttonView.getTag().toString();
            if (isChecked)
                this.answers.set(Integer.parseInt(tag.substring(tag.length() - 1)), tag.substring(0, tag.length() - 1));
            else
                this.answers.set(Integer.parseInt(tag.substring(tag.length() - 1)), "null");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0)
            this.answers.set(8, "pokemon");
        if (position == 1)
            this.answers.set(8, "overwatch");
        if (position == 2)
            this.answers.set(8, "league");
        if (position == 3)
            this.answers.set(8, "destiny");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar ageBar = findViewById(R.id.ageSeekBar);
        ageBar.setOnSeekBarChangeListener(this);

        this.colorBtns = new ImageButton[]{findViewById(R.id.yellowBtn), findViewById(R.id.whiteBtn), findViewById(R.id.greenBtn), findViewById(R.id.purpleBtn)};
        for (ImageButton btn : this.colorBtns)
            btn.setOnClickListener(this);

        CheckBox[] rainBoxes = new CheckBox[]{findViewById(R.id.runningBox), findViewById(R.id.workoutBox), findViewById(R.id.sleepingBox), findViewById(R.id.cookingBox)};
        for (CheckBox box : rainBoxes)
            box.setOnCheckedChangeListener(this);

        Switch sleepSwitch = findViewById(R.id.sleepSwitch);
        sleepSwitch.setOnCheckedChangeListener(this);

        Spinner foodDropDown = findViewById(R.id.foodDropDown);
        foodDropDown.setOnItemSelectedListener(this);

        Button doneBtn = findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(this);

        for (int i = 0; i < this.totalAnswers; i++)
            this.answers.add("null");

        // No switch default
        this.answers.set(6, "league");
        this.answers.set(7, "destiny");
    }
}
