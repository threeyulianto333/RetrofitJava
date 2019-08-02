package com.example.RetrofitJava;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.RetrofitJava.Api.ApiService;
import com.example.RetrofitJava.BukuActivity.BukuActivity;
import com.example.RetrofitJava.Item.SemuabukuItem;
import com.example.RetrofitJava.Response.BukuResponse;
import com.example.RetrofitJava.Service.BukuService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.TextUtils.isEmpty;
import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BukuService bukuService;

    private ProgressBar spinner;
    private EditText etJudul;
    private Button btnKirim;
    private Button btnShow;

//    btnSubmit = (Button) findViewById(R.id.);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        spinner = findViewById(R.id.pbMain);
        btnKirim = findViewById(R.id.btn_kirim);
        btnShow = findViewById(R.id.btn_show);
        etJudul = findViewById(R.id.et_judul);
//        pdMain = new ProgressDialog(this);


        initViews();
        initListener();

        bukuService = ApiService.createBaseService(this, BukuService.class);
    }

    private void initListener() {

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingSpinner(true);
                String judul = etJudul.getText().toString();
                SemuabukuItem BukuJson = new SemuabukuItem("0",judul);
                Toast.makeText(MainActivity.this,"Btn Add clicked", Toast.LENGTH_SHORT);
                if(isEmpty(judul))
                    etJudul.setError("Tidak boleh kosong");
                else
                    addData(BukuJson);
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BukuActivity.newInstance(MainActivity.this);
            }
        });
    }

    private void showLoadingSpinner(Boolean nilaiVisible) {
        ProgressDialog pdMain = new ProgressDialog(this);


        if(!nilaiVisible){
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//            spinner.setVisibility(View.GONE);
//            pdMain.setProgressStyle(ProgressDialog.STYLE_SPINNER);

//            pdMain.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pdMain.dismiss();
            Toast.makeText(MainActivity.this, "Dismiss Progress dialog", LENGTH_SHORT).show();

        } else {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//            spinner.setVisibility(View.VISIBLE);

            pdMain.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Without this user can hide loader by tapping outside screen
            pdMain.setCancelable(false);
            pdMain.setMessage("Loading . . . .");
            pdMain.show();
            Toast.makeText(MainActivity.this, "Show Progress dialog", LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        showLoadingSpinner(false);
    }

    //    private void addData(final String judul) {
    private void addData(final SemuabukuItem BukuJson) {
        Call<BukuResponse> call = bukuService.apiCreateBuku(BukuJson);
        showLoadingSpinner(false);

        call.enqueue(new Callback<BukuResponse>() {
            @Override
            public void onResponse(Call<BukuResponse> call, Response<BukuResponse> response) {
                if(response.code() == 200) {
                    Toast.makeText(MainActivity.this, "Success", LENGTH_SHORT).show();
                    etJudul.setText("");
                } else{
                    Log.e(TAG , BukuJson.getJudul());
                    Log.e(TAG + "Error bagian response" , response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<BukuResponse> call, Throwable t) {
                Log.e(TAG + ".error Bagian failure", t.toString());
            }
        });
    }
}
