package video.streaming.settingsactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity implements Callback<Void> {

    String address;

    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final EditText et = findViewById(R.id.et);
        Button btn = findViewById(R.id.btn);

        Button gotoButton = findViewById(R.id.button);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        address = pref.getString("address", "http://192.168.0.10:8081/");

        aSwitch = findViewById(R.id.switch1);
        aSwitch.setChecked(false);

        RetrofitClientService service = RetrofitClientInstance.getRetrofitInstance(address.replace("8081", "5000")).create(RetrofitClientService.class);

        aSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            Call<Void> call = service.sendData(aSwitch.isChecked() ? 1 : 0);
            call.enqueue(this);
        });

        btn.setOnClickListener(v -> {
            address = et.getText().toString();
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("address", address);
            editor.commit();
            Toast.makeText(this, address, Toast.LENGTH_LONG).show();
            et.setText("");
        });

        gotoButton.setOnClickListener(view -> {
            Intent i = new Intent(SettingsActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });


    }


    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {

    }
}
