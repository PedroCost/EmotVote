package pedro.emotvote;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import pl.droidsonroids.gif.GifTextView;

public class MainActivity extends AppCompatActivity {


    SeekBar seekBar;
    TextView textView_SeekBarValue;
    GifTextView coracaoNormal, coracaoLento, coracaoRapido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initElements();
        initUIElements();
    }

    private void initUIElements() {

        // Métodos da seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress > 0 && progress < 40) {
                    coracaoNormal.setVisibility(View.INVISIBLE);
                    coracaoLento.setVisibility(View.VISIBLE);
                    coracaoRapido.setVisibility(View.INVISIBLE);

                    textView_SeekBarValue.setTextSize(15);
                    textView_SeekBarValue.setTextColor(Color.WHITE);
                    textView_SeekBarValue.setText(String.valueOf(progress).trim());
                }
                if(progress > 40 && progress < 80) {
                    coracaoNormal.setVisibility(View.VISIBLE);
                    coracaoLento.setVisibility(View.INVISIBLE);
                    coracaoRapido.setVisibility(View.INVISIBLE);

                    textView_SeekBarValue.setTextSize(25);
                    textView_SeekBarValue.setTextColor(Color.rgb(181, 43, 119));
                    textView_SeekBarValue.setText(String.valueOf(progress).trim());
                }
                if(progress > 80) {
                    coracaoNormal.setVisibility(View.INVISIBLE);
                    coracaoLento.setVisibility(View.INVISIBLE);
                    coracaoRapido.setVisibility(View.VISIBLE);

                    textView_SeekBarValue.setTextSize(40);
                    textView_SeekBarValue.setTextColor(Color.RED);
                    textView_SeekBarValue.setText(String.valueOf(progress).trim() + "!");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });


    }


    private void initElements() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView_SeekBarValue = (TextView) findViewById(R.id.textView_valor);
        seekBar.setProgress(0);
        seekBar.incrementProgressBy(1);
        seekBar.setMax(100);

        coracaoNormal = (GifTextView) findViewById(R.id.imageView_coracaoNormal);
        coracaoLento = (GifTextView) findViewById(R.id.imageView_coracaoLento);
        coracaoRapido = (GifTextView) findViewById(R.id.imageView_coracaoRapido);
    }
}
