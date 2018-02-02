package com.example.fikrihaikal.printerlesspatner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class companyProfile extends AppCompatActivity {

    EditText name, email, nomor, alamat, kota;
    Button save;
    DatabaseReference databaseCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        databaseCompany = FirebaseDatabase.getInstance().getReference("Company");
        name = findViewById(R.id.namaJasa);
        email = findViewById(R.id.emailAdd);
        nomor = findViewById(R.id.nomorTelpon);
        alamat = findViewById(R.id.alamat);
        kota = findViewById(R.id.kota);
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCompanyProfile();
            }
        });
    }

    private void AddCompanyProfile() {
        String namajasa = name.getText().toString();
        String emailjasa = email.getText().toString();
        String nomorjasa = nomor.getText().toString();
        String alamatjasa = alamat.getText().toString();
        String kotajasa = kota.getText().toString();
        if (namajasa.isEmpty()){
            name.setError("Masukan Nama");
            name.requestFocus();
            return;
        }if (emailjasa.isEmpty()){
            email.setError("Masukan Email");
            email.requestFocus();
            return;
        }if (nomorjasa.isEmpty()){
            nomor.setError("Masukan Nomor Telepon");
            nomor.requestFocus();
            return;
        }if (alamatjasa.isEmpty()){
            alamat.setError("Masukan Alamat Jasa");
            alamat.requestFocus();
            return;
        }if (kotajasa.isEmpty()){
            kota.setError("Masukan Nama Kota");
            kota.requestFocus();
            return;
        }else{
            String id = databaseCompany.push().getKey();
            SaveComProfile saveComProfile = new SaveComProfile(id,namajasa,emailjasa,nomorjasa,alamatjasa,kotajasa);
            databaseCompany.child(id).setValue(saveComProfile);
            Toast.makeText(companyProfile.this,"Database Updated",Toast.LENGTH_SHORT).show();
        }
    }
}
