package pedro.emotvote;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import info.plux.pluxapi.Constants;
import pl.droidsonroids.gif.GifTextView;


public class Definicoes extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    final int[] digitalChannels = new int[2];

    ServiceBitalino mService;
    boolean mBounded;
    Intent mIntent;
    TextView text;
    Button button;
    GifTextView imageLoading, imageCorrect, imageIncorrect;

    EditText textConexao;



    Button buttonLigar;
    private Constants.States currentState = Constants.States.DISCONNECTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definicoes);

        initElements();
        initUIElements();

        doBindService();

        final Handler handler = new Handler();
        final int delay = 500; //milliseconds


        /*
        handler.postDelayed(new Runnable(){
            public void run(){
                bitalinoInformacao();
                handler.postDelayed(this, delay);
            }
        }, delay);
        */
    }

    private void initUIElements() {
        buttonLigar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean formatoMAC = textConexao.getText().toString().matches("\\d\\d:\\d\\d:\\d\\d:\\d\\d:\\d\\d:\\d\\d");
                if(formatoMAC) {
                    Intent intent = new Intent(Definicoes.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    // findViewById(R.id.imageView_loading).setVisibility(View.VISIBLE);
                    // mService.ligar(textConexao.getText().toString());
                }
                else {
                    // findViewById(R.id.imageView_loading).setVisibility(View.VISIBLE);
                    Intent intent = new Intent(Definicoes.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "MAC Address tem de estar no formato \"00:00:00:00:00:00\"", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void bitalinoInformacao() {
        String aux = mService.informacao();

        String[] informacao = aux.split("/");
        String bitalinoState = informacao[0];
        String bitalinoName = informacao[1];


        if(bitalinoState.equals("null")){
            imageLoading.setVisibility(View.INVISIBLE);
            imageIncorrect.setVisibility(View.VISIBLE);
        }
        else {
            String[] bitalinoInfo = bitalinoState.split(" "); // String retornada: Device 20:15:05:29:21:77: CONNECTED
            imageLoading.setVisibility(View.INVISIBLE);
            imageIncorrect.setVisibility(View.INVISIBLE);
            imageCorrect.setVisibility(View.VISIBLE);

        }

    }

    private void initElements(){
        mIntent = new Intent(this, ServiceBitalino.class);

        textConexao = (EditText) findViewById(R.id.editText_MACAdress);
        buttonLigar = (Button) findViewById(R.id.button_ligar);
        imageCorrect = (GifTextView) findViewById(R.id.imageView_correct);
        imageIncorrect = (GifTextView) findViewById(R.id.imageView_incorrect);
        imageLoading = (GifTextView) findViewById(R.id.imageView_loading);
    }



    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("OnResume Definicoes");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy Definicoes");
    }

    @Override
    protected void onPause(){
        super.onPause();
        doUnbindService();
        System.out.println("OnPause Definicoes");
    }

    /*
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Definicoes.this, MainActivity.class);
        intent.putExtra("Back Pressed",true);
        startActivity(intent);
        finish();

    }
    */


    ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceDisconnected(ComponentName name) {
            mBounded = false;
            mService = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            mBounded = true;
            ServiceBitalino.LocalBinder mLocalBinder = (ServiceBitalino.LocalBinder)service;
            mService = mLocalBinder.getServerInstance();
        }
    };

    public void doBindService() {
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
        mBounded = true;
    }

    public void doUnbindService() {
        if (mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    }

}