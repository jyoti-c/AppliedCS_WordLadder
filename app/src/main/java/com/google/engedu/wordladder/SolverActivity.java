package com.google.engedu.wordladder;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.engedu.worldladder.R;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by JKC on 20/02/2018.
 */

public class SolverActivity extends AppCompatActivity {
    String[] words;
    TextView startView,endView;
    EditText[] editTexts;
    LinearLayout linearLayout;
    PathDictionary dictionary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_solver);
        Intent intent = getIntent();
       // Bundle data = intent.getExtras();

           // words = data.getStringArray("words");
        final String[] words = intent.getStringArrayExtra("path");
        startView=(TextView)findViewById(R.id.startTextView);
        endView=(TextView)findViewById(R.id.endTextView);
        linearLayout=(LinearLayout)findViewById(R.id.ll_editTexts);
        startView.setText(words[0]);
        endView.setText(words[words.length-1]);
        editTexts=new EditText[words.length-2];
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new PathDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        for(int i=0;i<editTexts.length;i++)
        {
            editTexts[i]=new EditText(SolverActivity.this);
            linearLayout.addView(editTexts[i]);
        }
    }
    public void onSolve(View view)
    {
        for(int i=1;i<words.length-1;i++) {
            editTexts[i - 1].setText(words[i]);
        }
        Toast.makeText(this,"Solved",Toast.LENGTH_SHORT).show();
    }
}
