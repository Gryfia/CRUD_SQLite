package id.el.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HasilForm extends AppCompatActivity {
    private TextView hasilForm;
    private Button btn_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_form);
        hasilForm = (TextView) findViewById(R.id.hasil);
        btn_other = (Button) findViewById(R.id.btn_other);

        btn_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ListDataRegistrasi.class);
                startActivity(intent1);
            }
        });

        Intent intent1 = getIntent();
        String bidang = intent1.getStringExtra("BIDANG");
        String nama = intent1.getStringExtra("NAMA");
        String gender = intent1.getStringExtra("GENDER");
        String status = intent1.getStringExtra("STATUS");

        hasilForm.setText("NIK\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t: " +bidang+"\nNama\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t: " +nama+"\nJenis Kelamin\t\t\t\t\t\t\t\t\t: " +gender+"\nKeluhan\t\t\t\t\t\t\t\t\t\t\t\t\t\t :"+status);
    }
}