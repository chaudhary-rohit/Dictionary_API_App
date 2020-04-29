package com.example.dictionary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity implements DictAsyncResponse
{
    Button button;
    EditText word;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.bt_show);
        word   = findViewById(R.id.word);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startDictAPI();
            }
        });
    }

    public void startDictAPI()
    {
        DictAsyncTask dictAPItask = new DictAsyncTask();
        dictAPItask.delegate = this;
        dictAPItask.execute(word.getText().toString());
        Log.i("DICTAPI", "Invoked DICTAPI task");
    }

    @Override
    public void dictProcessFinish(DictData result)
    {
        Log.i("DICTAPI", "dictProcessFinish() triggered ");
        createBottomSheetDialogue(result);
    }

    private void createBottomSheetDialogue(DictData dictData)
    {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        TextView word = bottomSheetDialog.findViewById(R.id.word);
        TextView phonetic = bottomSheetDialog.findViewById(R.id.phonetic);
        ImageButton audio = bottomSheetDialog.findViewById(R.id.audio_btn);

        word.setText(dictData.word);
        phonetic.setText(dictData.phonetic);

        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new AdapterRecyclerView(dictData.subData, MainActivity.this));

        //skipping audio_btn for now

        bottomSheetDialog.show();
    }

}
