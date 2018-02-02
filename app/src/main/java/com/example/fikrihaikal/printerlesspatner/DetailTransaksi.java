package com.example.fikrihaikal.printerlesspatner;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class DetailTransaksi extends AppCompatActivity {
    TextView jenis,pengirim,ukuran,banyak;
    Button accept;
    String id;
    String namaUser;
    String jenisnya;
    String ketentuan;
    String banyaknya;
    String alamatPrint;
    String folderPrint;
    String filePrint;
    String keputusan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);
        jenis = findViewById(R.id.tv_jenis);
        pengirim = findViewById(R.id.tv_pengirim);
        ukuran = findViewById(R.id.tv_ukuran);
        banyak = findViewById(R.id.tv_banyak);
        accept = findViewById(R.id.accept);
        Intent i = getIntent();
        id =  i.getStringExtra(MainPage.TRANSACTION_ID);
        namaUser = i.getStringExtra(MainPage.TRANSACTION_NAME);
        jenisnya = i.getStringExtra(MainPage.TRANSACTION_KIND);
        ketentuan = i.getStringExtra(MainPage.TRANSACTION_AGREEMENT);
        banyaknya = i.getStringExtra(MainPage.TRANSACTION_MANY);
        alamatPrint = i.getStringExtra(MainPage.TRANSACTION_ADDRESS);
        folderPrint = i.getStringExtra(MainPage.TRANSACTION_FOLDER);
        filePrint = i.getStringExtra(MainPage.TRANSACTION_FILE);
        keputusan = i.getStringExtra(MainPage.TRANSACTION_DECISION);
        jenis.setText(jenisnya);
        pengirim.setText(namaUser);
        ukuran.setText(ketentuan);
        banyak.setText(banyaknya);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    downloadFile();
            }
        });
    }
    private void downloadFile(){
        File localFile = null;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://printerless-b2a6d.appspot.com/09lOMUIjZxZxro9PwQFJJtEuGI12/"+filePrint+".pdf");
        //StorageReference  islandRef = storageRef.child(folderPrint+"/"+filePrint+".pdf");
        try {
        File rootPath = new File(Environment.getExternalStorageDirectory(), "/printerless/");
            if (!rootPath.exists()) {
                rootPath.mkdir();
            }
            localFile = new File(rootPath,"/printerless/"+filePrint+".pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ",";local tem file created  created ");
                //  updateDb(timestamp,localFile.toString(),position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ",";local tem file not created  created " +exception.toString());
            }
        });
    }
}
