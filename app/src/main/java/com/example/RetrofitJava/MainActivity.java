package com.example.RetrofitJava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.RetrofitJava.Api.ApiService;
import com.example.RetrofitJava.BukuActivity.BukuActivity;
import com.example.RetrofitJava.Response.BukuResponse;
import com.example.RetrofitJava.Service.BukuService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.TextUtils.isEmpty;
import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BukuService bukuService;

    private EditText etJudul;
    private Button btnKirim;
    private Button btnShow;

//    btnSubmit = (Button) findViewById(R.id.);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnKirim = findViewById(R.id.btn_kirim);
        btnShow = findViewById(R.id.btn_show);
        etJudul = findViewById(R.id.et_judul);

        initViews();
        initListener();

        bukuService = ApiService.createBaseService(this, BukuService.class);
    }

    private void initListener() {


        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul = etJudul.getText().toString();
                Toast.makeText(MainActivity.this,"Btn Add clicked", Toast.LENGTH_SHORT);
                if(isEmpty(judul))
                    etJudul.setError("Tidak boleh kosong");
                else
                    addData(judul);
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BukuActivity.newInstance(MainActivity.this);
            }
        });
    }

    private void initViews() {

    }

    private void addData(String judul) {
        Call<BukuResponse> call = bukuService.apiCreateBuku(judul);

        call.enqueue(new Callback<BukuResponse>() {
            @Override
            public void onResponse(Call<BukuResponse> call, Response<BukuResponse> response) {
                if(response.code() == 200) {
                    Toast.makeText(MainActivity.this, "Success", LENGTH_SHORT).show();
                    etJudul.setText("");
                } else{
                    Log.e(TAG + "Error bagian response =========================", response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<BukuResponse> call, Throwable t) {
                Log.e(TAG + ".error Bagian failure", t.toString());
            }
        });
    }
}
