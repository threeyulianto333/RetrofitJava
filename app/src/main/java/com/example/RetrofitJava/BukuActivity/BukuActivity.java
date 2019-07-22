package com.example.RetrofitJava.BukuActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RetrofitJava.Adapter.BukuAdapter;
import com.example.RetrofitJava.Api.ApiService;
import com.example.RetrofitJava.Item.SemuabukuItem;
import com.example.RetrofitJava.Listener.OnDeleteClickListener;
import com.example.RetrofitJava.Listener.OnUpdateClickListener;
import com.example.RetrofitJava.R;
import com.example.RetrofitJava.Response.BukuResponse;
import com.example.RetrofitJava.Service.BukuService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BukuActivity extends AppCompatActivity implements OnDeleteClickListener, OnUpdateClickListener {

    private static final String TAG = BukuActivity.class.getSimpleName();

    private RecyclerView rvBuku;
    private BukuAdapter adapter;
    private BukuService service;

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, BukuActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku);
        initViews();

        // Initialization adapter
        adapter = new BukuAdapter(this);
        rvBuku.setLayoutManager(new LinearLayoutManager(this));
        service = ApiService.createBaseService(this, BukuService.class);

        rvBuku.setAdapter(adapter);
        loadBukuItem();
    }

    private void loadBukuItem() {
        Call<BukuResponse<List<SemuabukuItem>>> call = service.apiReadBuku();
        call.enqueue(new Callback<BukuResponse<List<SemuabukuItem>>>() {
            @Override
            public void onResponse(Call<BukuResponse<List<SemuabukuItem>>> call, Response<BukuResponse<List<SemuabukuItem>>> response) {
                if(response.code() == 200) {
                    adapter.addAll(response.body().getData());
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<BukuResponse<List<SemuabukuItem>>> call, Throwable t) {
                Log.e(TAG+".error", t.toString());
            }
        });
    }

    private void initListener() {
        adapter.setOnDeleteClickListener(this);
        adapter.setOnUpdateClickListener(this);
    }

    private void initViews() {
        rvBuku = (RecyclerView) findViewById(R.id.rv_buku);
    }

    private void doDelete(final int position, String id) {
        Call<BukuResponse> call = service.apiDeleteBuku(id);
        call.enqueue(new Callback<BukuResponse>() {
            @Override
            public void onResponse(Call<BukuResponse> call, Response<BukuResponse> response) {
                if(response.code() == 200)
                    adapter.remove(position);
            }

            @Override
            public void onFailure(Call<BukuResponse> call, Throwable t) {
                Log.e(TAG+".errorDelete", t.toString());
            }
        });
    }

    @Override
    public void onDeleteClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda Yakin Ingin Menghapusnya?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                doDelete(position, adapter.getSemuabukuItem(position).getId());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onUpdateClick(int position) {
        SemuabukuItem dataBuku = adapter.getSemuabukuItem(position);
        UpdateBukuActivity.newInstance(this, dataBuku);
    } 
}
