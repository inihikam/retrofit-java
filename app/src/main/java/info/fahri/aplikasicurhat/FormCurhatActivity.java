package info.fahri.aplikasicurhat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import info.fahri.aplikasicurhat.apiclient.ApiClient;
import info.fahri.aplikasicurhat.apiclient.Curhat;
import info.fahri.aplikasicurhat.apiclient.CurhatInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormCurhatActivity extends AppCompatActivity {

    SharedPreferences pref;
    EditText edtCurhat;

    CurhatInterface curhatInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_curhat);

        pref = getSharedPreferences(MainActivity.KEY_USER, Context.MODE_PRIVATE);
        edtCurhat = findViewById(R.id.edtCurhat);
        curhatInterface = ApiClient.getClient().create(CurhatInterface.class);
    }

    public void postCurhat(View v){

        String username = pref.getString(MainActivity.KEY_USER, "");
        String konten = edtCurhat.getText().toString();

        curhatInterface.postCurhat(username, konten).enqueue(new Callback<Curhat>() {
            @Override
            public void onResponse(Call<Curhat> call, Response<Curhat> response) {
                Log.i("post_response", response.raw().toString());
            }

            @Override
            public void onFailure(Call<Curhat> call, Throwable t) {
                Log.e("response_error", t.getMessage());
            }
        });

        finish();
    }
}
