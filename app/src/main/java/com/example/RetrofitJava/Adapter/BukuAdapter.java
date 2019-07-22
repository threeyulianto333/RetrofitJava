package com.example.RetrofitJava.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.RetrofitJava.Item.SemuabukuItem;
import com.example.RetrofitJava.Listener.OnDeleteClickListener;
import com.example.RetrofitJava.Listener.OnUpdateClickListener;
import com.example.RetrofitJava.R;

import java.util.ArrayList;
import java.util.List;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.ViewHolder> {
    Context context;
    List<SemuabukuItem> dataBuku;

    OnDeleteClickListener onDeleteClickListener;
    OnUpdateClickListener onUpdateClickListener;

    public BukuAdapter(Context context) {
        this.context = context;
        dataBuku = new ArrayList<>();
    }

    public void add(SemuabukuItem item) {
        dataBuku.add(item);
        notifyItemInserted(dataBuku.size() - 1);
    }

    public void addAll(List<SemuabukuItem> items) {
        for (SemuabukuItem item : items) {
            add(item);
        }
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setOnUpdateClickListener(OnUpdateClickListener onUpdateClickListener) {
        this.onUpdateClickListener = onUpdateClickListener;
    }

    public SemuabukuItem getSemuabukuItem(int position) {
        return dataBuku.get(position);
    }

    public void remove(int position) {
        if (position >= 0 && position < dataBuku.size()) {
            dataBuku.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(dataBuku.get(position));
    }

    @Override
    public int getItemCount() {
        return dataBuku.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomer;
        TextView tvName;
        Button btnEdit;
        Button btnRemove;

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_buku, parent, false));
            initViews();

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUpdateClickListener.onUpdateClick(getAdapterPosition());
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClickListener.onDeleteClick(getAdapterPosition());
                }
            });
        }

        public void bind(SemuabukuItem item) {
            int nomer = getAdapterPosition() + 1;
            tvNomer.setText(String.valueOf(nomer));
            tvName.setText(item.getJudul());
        }

        public void initViews() {
            tvNomer = (TextView) itemView.findViewById(R.id.tv_nomer);
            tvName = (TextView) itemView.findViewById(R.id.tv_judul);
            btnEdit = (Button) itemView.findViewById(R.id.btn_edit);
            btnRemove = (Button) itemView.findViewById(R.id.btn_delete);
        }
    }
}
