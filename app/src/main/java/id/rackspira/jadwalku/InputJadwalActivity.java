package id.rackspira.jadwalku;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import id.rackspira.jadwalku.Database.DbHelper;
import id.rackspira.jadwalku.Model.Jadwal;

public class InputJadwalActivity extends AppCompatActivity {
    public static int showPage = 0;

    EditText editTextNamaMakul;
    EditText editTextDosen;
    EditText editTextRuangan;
    EditText editTextHari;
    EditText editTextJamMulai;
    EditText editTextJamSelesai;
    Button buttonInputJadwal;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_jadwal);

        editTextNamaMakul= findViewById(R.id.editTextNamaMakul);
        editTextDosen= findViewById(R.id.editTextDosen);
        editTextRuangan= findViewById(R.id.editTextRuangan);
        editTextHari= findViewById(R.id.editTexthari);
        editTextJamMulai= findViewById(R.id.editTextJamMulai);
        editTextJamSelesai= findViewById(R.id.editTextJamSelesai);
        buttonInputJadwal= findViewById(R.id.buttonInputJadwal);

        dbHelper=DbHelper.getInstance(getApplicationContext());

        editTextHari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] charSequence={"Senin", "Selasa", "Rabu", "Kamis", "Jumat"};
                AlertDialog.Builder builder=new AlertDialog.Builder(InputJadwalActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showPage(i);
                    }
                });
                builder.create().show();
            }
        });

        editTextJamMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(InputJadwalActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editTextJamMulai.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        editTextJamSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(InputJadwalActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editTextJamSelesai.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        buttonInputJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Jadwal jadwal=new Jadwal();

                jadwal.setNamaMakul(editTextNamaMakul.getText().toString());
                jadwal.setDosen(editTextDosen.getText().toString());
                jadwal.setRuang(editTextRuangan.getText().toString());
                jadwal.setHari(editTextHari.getText().toString());
                jadwal.setJamMulai(editTextJamMulai.getText().toString());
                jadwal.setJamSelesai(editTextJamSelesai.getText().toString());

                dbHelper.insertJadwal(jadwal);

                Intent intent=new Intent(InputJadwalActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showPage(int i){
        switch (i){
            case 0 :
                editTextHari.setText("Senin");

                showPage = 0;
                break;
            case 1 :
                editTextHari.setText("Selasa");

                showPage = 1;
                break;
            case 2 :
                editTextHari.setText("Rabu");

                showPage = 2;
                break;
            case 3 :
                editTextHari.setText("Kamis");

                showPage = 3;
                break;
            case 4 :
                editTextHari.setText("Jumat");

                showPage = 4;
                break;
        }
    }
}
