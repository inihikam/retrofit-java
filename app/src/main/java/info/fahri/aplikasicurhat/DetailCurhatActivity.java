package info.fahri.aplikasicurhat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import info.fahri.aplikasicurhat.apiclient.ApiClient;
import info.fahri.aplikasicurhat.apiclient.Curhat;
import info.fahri.aplikasicurhat.apiclient.CurhatInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCurhatActivity extends AppCompatActivity {

    TextView txtDetailNama, txtDetailKonten;
    Curhat curhat;
    CurhatInterface curhatInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_curhar);
        txtDetailKonten = findViewById(R.id.txtDetailKonten);
        txtDetailNama = findViewById(R.id.txtNamaDetail);

        Intent it = getIntent();
        curhat = (Curhat) it.getSerializableExtra("current_curhat");
        txtDetailNama.setText(curhat.getNama());
        txtDetailKonten.setText(curhat.getKonten());

        curhatInterface = ApiClient.getClient().create(CurhatInterface.class);
    }

    public void close(View v){
        finish();
    }

    public void deleteCurhat(View v){
        curhatInterface.delCurhat(curhat.getId()).enqueue(new Callback<Curhat>() {
            @Override
            public void onResponse(Call<Curhat> call, Response<Curhat> response) {
                Log.d("delete_response", response.raw().toString());
            }

            @Override
            public void onFailure(Call<Curhat> call, Throwable t) {
                Log.e("response_error", t.getMessage());
            }
        });
        finish();

    }
}
