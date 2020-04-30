package com.example.dictionary;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.recycler_views.L1RecyclerView;
import com.example.dictionary.recycler_views.L1DictData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements DictAsyncResponse
{
    Button button;
    EditText word;

    FloatingActionButton add, bookmark, copy, share, translate;
    Animation fab_open, fab_close, rot_forward, rot_backward;

    Boolean isOpen = false;

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
    public void dictProcessFinish(L1DictData result)
    {
        Log.i("DICTAPI", "dictProcessFinish() triggered ");
        createBottomSheetDialogue(result);
    }

    private void createBottomSheetDialogue(final L1DictData l1DictData)
    {
        if(l1DictData != null)
        {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
            bottomSheetDialog.setCanceledOnTouchOutside(false);

            TextView word = bottomSheetDialog.findViewById(R.id.word);
            TextView phonetic = bottomSheetDialog.findViewById(R.id.phonetic);
            ImageButton audio = bottomSheetDialog.findViewById(R.id.audio_btn);

            add = bottomSheetDialog.findViewById(R.id.floatingButton);
            bookmark = bottomSheetDialog.findViewById(R.id.bmFloatingButton);
            copy = bottomSheetDialog.findViewById(R.id.copyFloatingButton);
            share = bottomSheetDialog.findViewById(R.id.shareFloatingButton);
            translate = bottomSheetDialog.findViewById(R.id.translateFloatingButton);

            fab_open  = AnimationUtils.loadAnimation(this, R.anim.fab_open);
            fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);
            rot_forward  = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
            rot_backward = AnimationUtils.loadAnimation(this, R.anim.rotate_reverse);

            String url = l1DictData.audio_link.substring(2); // your URL here
            Log.d("AUDIO", l1DictData.audio_link.substring(2));
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animateFab();
                }
            });

            word.setText(l1DictData.word);
            phonetic.setText(l1DictData.phonetic);

            RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            recyclerView.setAdapter(new L1RecyclerView(l1DictData.l2DictData, MainActivity.this));

            audio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.start();
                }
            });

            bottomSheetDialog.show();
        }
        else
        {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_negative);
            bottomSheetDialog.setCanceledOnTouchOutside(false);

            TextView word = bottomSheetDialog.findViewById(R.id.negative_bsd);
            word.setText("Sorry! Word not found");
            bottomSheetDialog.show();
        }

    }

    private void animateFab()
    {
        Log.i("FAB", "Touched");
        if(isOpen)
        {
            Log.i("FAB", "Closing");
            add.startAnimation(rot_backward);
            bookmark.startAnimation(fab_close);
            copy.startAnimation(fab_close);
            share.startAnimation(fab_close);
            translate.startAnimation(fab_close);

            bookmark.setClickable(false);
            copy.setClickable(false);
            share.setClickable(false);
            translate.setClickable(false);

            isOpen = false;
        }
        else
        {
            Log.i("FAB", "Opening");
            add.startAnimation(rot_forward);
            bookmark.startAnimation(fab_open);
            copy.startAnimation(fab_open);
            share.startAnimation(fab_open);
            translate.startAnimation(fab_open);

            bookmark.setClickable(true);
            copy.setClickable(true);
            share.setClickable(true);
            translate.setClickable(true);

            isOpen = true;
        }
    }

}
