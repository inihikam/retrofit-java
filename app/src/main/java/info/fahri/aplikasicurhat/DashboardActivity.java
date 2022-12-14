package info.fahri.aplikasicurhat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;

import info.fahri.aplikasicurhat.adapter.CurhatAdapter;
import info.fahri.aplikasicurhat.apiclient.ApiClient;
import info.fahri.aplikasicurhat.apiclient.Curhat;
import info.fahri.aplikasicurhat.apiclient.CurhatInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    RecyclerView recCurhat;
    CurhatAdapter adapter;

    CurhatInterface curhatInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initFab();

        recCurhat = findViewById(R.id.rec_curhat);
        recCurhat.setLayoutManager(new LinearLayoutManager(this));

        sharedPref = getSharedPreferences(MainActivity.KEY_USER, Context.MODE_PRIVATE);
        String namaUser = sharedPref.getString(MainActivity.KEY_USER, null);
        Snackbar.make(toolbar, "Anda login sebagai: "+namaUser, Snackbar.LENGTH_LONG).show();

        curhatInterface = ApiClient.getClient().create(CurhatInterface.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllCurhat();
    }

    private void getAllCurhat(){
        Call<List<Curhat>> allCurhat = curhatInterface.getCurhat();
        allCurhat.enqueue(new Callback<List<Curhat>>() {
            @Override
            public void onResponse(Call<List<Curhat>> call, Response<List<Curhat>> response) {
                ArrayList<Curhat> listCurhat = (ArrayList<Curhat>) response.body();
                adapter = new CurhatAdapter(listCurhat);
                recCurhat.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Curhat>> call, Throwable t) {
                Log.e("response_error", t.getMessage());
            }
        });

    }

    private void initFab(){
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), FormCurhatActivity.class));
            }
        });

        FloatingActionButton fabLogOff = findViewById(R.id.fabLogoff);
        fabLogOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(MainActivity.KEY_USER);
                editor.apply();
                finish();
            }
        });
    }

}
