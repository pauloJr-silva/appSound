package com.example.som;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // primeiramento temos que criar essa pasta raw no projeto
        // toda a musica tem que estar na pasta raw

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.teste);
        inicializarSeekBar();
    }

    //creação do Play
    public void play(View view){
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    //pausar
    public  void pause(View view){
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    public void parar(View view){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        }
    }

    // volume

    private void inicializarSeekBar(){
        seekVolume = findViewById(R.id.seekVolume);

        //configurar o audio
        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);

        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // configurar os valores para seekBar

        // volume maximo
        seekVolume.setMax(volumeMaximo);
        // volume atual
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,
                                                    AudioManager.FLAG_SHOW_UI);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
        protected void onStop(){
        super.onStop();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    //distruir uma activity
    @Override
        protected void onDestroy(){
            super.onDestroy();
            if (mediaPlayer!=null && mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
    }


}