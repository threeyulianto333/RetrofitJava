package com.example.RetrofitJava.BukuActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.RetrofitJava.Api.ApiService;
import com.example.RetrofitJava.Item.SemuabukuItem;
import com.example.RetrofitJava.R;
import com.example.RetrofitJava.Response.BukuResponse;
import com.example.RetrofitJava.Service.BukuService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.TextUtils.isEmpty;

public class UpdateBukuActivity extends AppCompatActivity {
    private static final String TAG = UpdateBukuActivity.class.getSimpleName();

    private EditText etName;
    private Button btnSubmit;
    private BukuService service;
    private SemuabukuItem dataBuku;

    public static void newInstance(Context context, SemuabukuItem dataBuku) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG+".data", dataBuku);
        Intent intent = new Intent(context, UpdateBukuActivity.class);
        intent.putExtras(bundle);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        if(getIntent() != null) {
            dataBuku = getIntent().getParcelableExtra(TAG+".data");
        }

        initViews();

        service = ApiService.createBaseService(this, BukuService.class);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String id = dataBuku.getId();
                Log.e("name", name);

                if(isEmpty(name))
                    etName.setError("Must not be empty");
                else
                    updateData(id,name);
            }
        });
    }

    private void updateData(String id,String name) {
        Call<BukuResponse> call = service.apiUpdateBuku(dataBuku.getId(), name);

        call.enqueue(new Callback<BukuResponse>() {
            @Override
            public void onResponse(Call<BukuResponse> call, Response<BukuResponse> response) {
                if(response.code() == 200) {
                    Toast.makeText(UpdateBukuActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                }
            }

            @Override
            public void onFailure(Call<BukuResponse> call, Throwable t) {
                Log.e(TAG + ".error", t.toString());
            }
        });
    }

    private void initViews() {
        etName = (EditText) findViewById(R.id.et_judul);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        etName.setText(dataBuku.getJudul());
    }
}
