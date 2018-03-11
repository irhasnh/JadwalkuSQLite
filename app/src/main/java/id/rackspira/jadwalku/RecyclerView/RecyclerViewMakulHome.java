package id.rackspira.jadwalku.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.rackspira.jadwalku.Database.DbHelper;
import id.rackspira.jadwalku.MainActivity;
import id.rackspira.jadwalku.Model.Jadwal;
import id.rackspira.jadwalku.R;
import id.rackspira.jadwalku.UpdateActivity;

/*
 * Created by kikiosha on 3/5/18.
 */

public class RecyclerViewMakulHome extends RecyclerView.Adapter<RecyclerViewMakulHome.ViewHolder> {
    private Context context;

    private List<Jadwal> jadwalList = new ArrayList<>();

    private DbHelper dbHelper;

    public RecyclerViewMakulHome(Context context, List<Jadwal> jadwalList) {
        this.context = context;
        this.jadwalList = jadwalList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewMakulHome.ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_makul,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dbHelper=DbHelper.getInstance(context);

        final Jadwal jadwal = jadwalList.get(position);
        holder.textViewNamaMakul.setText(jadwal.getNamaMakul());
        holder.textViewRuangan.setText("Ruang : "+jadwal.getRuang());
        holder.textViewDosen.setText("Dosen : "+jadwal.getDosen());
        holder.textViewWaktu.setText(jadwal.getJamMulai() +" - "+jadwal.getJamSelesai());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] charSequence={"Update", "Delete"};
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Pilihan");
                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showPage(i, jadwal.getId());
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return jadwalList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamaMakul;
        TextView textViewRuangan;
        TextView textViewDosen;
        TextView textViewWaktu;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            textViewNamaMakul= itemView.findViewById(R.id.textViewMakul);
            textViewDosen= itemView.findViewById(R.id.textViewDosen);
            textViewRuangan= itemView.findViewById(R.id.textViewRuang);
            textViewWaktu= itemView.findViewById(R.id.textViewWaktu);
            cardView= itemView.findViewById(R.id.card_view);
        }
    }

    @SuppressLint("SetTextI18n")
    private void showPage(int i, String id){
        switch (i){
            case 0 :
                Intent intent=new Intent(context, UpdateActivity.class);
                intent.putExtra("id_jadwal",id);
                context.startActivity(intent);
                ((Activity)context).finish();
                break;
            case 1 :
                dbHelper.deleteJadwal(id);
                Intent refresh=new Intent(context, MainActivity.class);
                context.startActivity(refresh);
                ((Activity)context).finish();
                break;
            case 2 :
        }
    }
}
