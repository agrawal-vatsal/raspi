package video.streaming.settingsactivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<APIData> {

    Integer a, b, c, d;

    TextView ATv, BTv, CTv, DTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ATv = findViewById(R.id.A);
        BTv = findViewById(R.id.B);
        CTv = findViewById(R.id.C);
        DTv = findViewById(R.id.D);

        WebView webView = findViewById(R.id.webView);

        Button onButton = findViewById(R.id.on);
        Button offButton = findViewById(R.id.off);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String address = pref.getString("address", "http://192.168.0.10:8081/");
        webView.loadUrl(address);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        RetrofitClientService service = RetrofitClientInstance.getRetrofitInstance(address.replace("8081", "5000")).create(RetrofitClientService.class);

        onButton.setOnClickListener(view -> {
            Call call = service.sendData(1);
            call.enqueue(null);
        });

        offButton.setOnClickListener(view -> {
            Call call = service.sendData(0);
            call.enqueue(null);
        });

        AsyncTask.execute(() -> {
            while (true) {
                Call<APIData> call = service.getData();
                call.enqueue(this);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void onResponse(@NonNull Call<APIData> call, @NonNull Response<APIData> response) {
        APIData dataObject = response.body();
        if (dataObject != null) {
            a = Integer.parseInt(dataObject.getA());
            b = Integer.parseInt(dataObject.getB());
            c = Integer.parseInt(dataObject.getC());
            d = Integer.parseInt(dataObject.getD());

            ATv.setText(a);
            BTv.setText(b);
            CTv.setText(c);
            DTv.setText(d);
        }
    }


    @Override
    public void onFailure(Call<APIData> call, Throwable t) {
        Log.d("TAG", "onFailure: " + t.getMessage() + " " + t.getCause());
    }


}
