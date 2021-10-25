package id.el.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText BIDANG, Nama;
    private RadioGroup listOpsiGender;
    private RadioButton radioButton, female, male;
    private CheckBox checkBoxSiswa, checkBoxLainnya;
    private TextView AlBIDANG, AlNama, Algender, AlStatus, AlPresentase;
    private Button btnReset, btnSubmit;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    String bidang,nama,gender,status;
    DBmain dBmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BIDANG = (EditText) findViewById(R.id.BIDANG);
        Nama = (EditText) findViewById(R.id.Nama);
        checkBoxSiswa = (CheckBox) findViewById(R.id.checkBoxSiswa);
        checkBoxLainnya = (CheckBox) findViewById(R.id.checkBoxLainnya);
        listOpsiGender = findViewById(R.id.opsiGender);
        female = findViewById(R.id.female);
        male = findViewById(R.id.male);
        btnReset= (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(clickListener);
        btnSubmit= (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(clickListener);
        dBmain = new DBmain(this);

    }

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSubmit:
                    bidang = BIDANG.getText().toString();
                    nama = Nama.getText().toString();
                    if (!checkBoxSiswa.isChecked() && !checkBoxLainnya.isChecked()){
                        Toast.makeText(getApplicationContext(), "Tidak ada keluhan yang dipilih", Toast.LENGTH_SHORT).show();
                    } else if (bidang.matches("")||nama.matches("")){
                        Toast.makeText(getApplicationContext(), "Kolom NIK dan Nama Lengkap Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    } else if(listOpsiGender.getCheckedRadioButtonId() == -1){
                        Toast.makeText(getApplicationContext(), "Jenis Kelamin Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        int selectedId = listOpsiGender.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        gender = radioButton.getText().toString();
                        status = "";
                        if(checkBoxSiswa.isChecked()){
                            if(status == ""){
                                status = "" + checkBoxSiswa.getText();
                            }else{
                                status = status + "," + checkBoxSiswa.getText();
                            }
                        }if(checkBoxLainnya.isChecked()){
                            if(status == ""){
                                status = "" + checkBoxLainnya.getText();
                            }else{
                                status = status + "," + checkBoxLainnya.getText();
                            }
                        }
                        DialogForm();
                    }
                    break;
                case R.id.btnReset:
                    BIDANG.setText(null);
                    Nama.setText(null);
                    listOpsiGender.clearCheck();
                    checkBoxSiswa.setChecked(false);
                    checkBoxLainnya.setChecked(false);
                    break;
            }
        }
    };

    private void DialogForm(){
        dialog = new AlertDialog.Builder(MainActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.alert_dialogs, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        AlBIDANG = (TextView) dialogView.findViewById(R.id.AlNIK);
        AlNama = (TextView) dialogView.findViewById(R.id.AlNama);
        Algender = (TextView) dialogView.findViewById(R.id.Algender);
        AlStatus = (TextView) dialogView.findViewById(R.id.AlStatus);

        AlBIDANG.setText("NIK\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t: " + bidang);
        AlNama.setText("Nama\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t: " + nama);
        Algender.setText("Jenis Kelamin\t\t\t\t\t\t\t\t: " + gender);
        AlStatus.setText("Keluhan\t\t\t\t\t\t\t\t\t\t\t\t\t:" + status);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ContentValues values = new ContentValues();
                values.put(DBmain.row_bidang, bidang);
                values.put(DBmain.row_nama, nama);
                values.put(DBmain.row_gender, gender);
                values.put(DBmain.row_status, status);
                dBmain.insertData(values);

                Intent intent1 = new Intent(MainActivity.this, HasilForm.class);
                intent1.putExtra("NIK", bidang);
                intent1.putExtra("NAMA", nama);
                intent1.putExtra("GENDER", gender);
                intent1.putExtra("STATUS", status);
                startActivity(intent1);
            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
