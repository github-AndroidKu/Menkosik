package org.d3ifcool.menkosik;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.d3ifcool.menkosik.model.DataKos;

import java.util.UUID;

public class PostKosanActivity extends AppCompatActivity implements View.OnClickListener {

    private String uploadFotoURL = null;
    private EditText namaPemilik;
    private EditText noTelepon;
    private EditText namaKos;
    private EditText alamat;
    private EditText kota;
    private EditText daerah;
    private EditText bulanTahun;
    private EditText lokasi;
    private EditText fasilitasKos;
    private double latitude = 0.0;
    private double longitude = 0.0;

    Button btnInsert;
    FirebaseStorage storage;
    StorageReference storageReference;
    private ProgressBar progressBar;

    int PLACE_PICKER_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_kosan);

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            //Toast.makeText(getApplicationContext(),"Terhubung Dengan Internet", Toast.LENGTH_LONG).show();
        }else{
            AlertDialog.Builder alert = new AlertDialog.Builder(this)
                    .setTitle("Tidak Ada Koneksi Internet")
                    .setMessage("Silakan periksa koneksi internet anda dan coba lagi")
                    .setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
            alert.setCancelable(false);
            alert.show();
        }

        namaPemilik = (EditText) findViewById(R.id.nama_pemilik);
        noTelepon = (EditText) findViewById(R.id.hp_kosan);
        namaKos = (EditText) findViewById(R.id.nama_kosan);
        alamat = (EditText) findViewById(R.id.alamat_kosan);
        kota = (EditText) findViewById(R.id.kota);
        daerah = (EditText) findViewById(R.id.daerah);
        bulanTahun = (EditText) findViewById(R.id.harga);
        lokasi = (EditText) findViewById(R.id.lokasi);
        lokasi.setOnClickListener(this);
        fasilitasKos = (EditText) findViewById(R.id.fasilitas);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(this);


        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_post);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(lokasi)){
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(PostKosanActivity.this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
        if (view.equals(btnInsert)){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Kost");

            String ambilNamaPemilik = namaPemilik.getText().toString();
            String ambilHpPemilik = noTelepon.getText().toString();
            String ambilNamaKos = namaKos.getText().toString();
            String ambilAlamatKos = alamat.getText().toString();
            String ambilKotaKos = kota.getText().toString();
            String ambilDaerahKos = daerah.getText().toString();
            String ambilHargaKos = bulanTahun.getText().toString();
            String ambilLokasiKos = lokasi.getText().toString();
            String ambilFasilitasKos = fasilitasKos.getText().toString();

                if (TextUtils.isEmpty(uploadFotoURL)) {
                    Toast.makeText(getApplicationContext(), "Masukan Gambar!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ambilNamaPemilik)) {
                    Toast.makeText(getApplicationContext(), "Masukan nama anda!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ambilNamaKos)) {
                    Toast.makeText(getApplicationContext(), "Masukan nama Kos!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ambilHpPemilik)) {
                    Toast.makeText(getApplicationContext(), "Masukan no telepon!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ambilAlamatKos)) {
                    Toast.makeText(getApplicationContext(), "Masukan alamat Kos!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ambilKotaKos)) {
                    Toast.makeText(getApplicationContext(), "Masukan kota Kos!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ambilDaerahKos)) {
                    Toast.makeText(getApplicationContext(), "Masukan daerah Kos!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ambilLokasiKos)) {
                    Toast.makeText(getApplicationContext(), "Masukan lokasi Kos!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ambilFasilitasKos)) {
                    Toast.makeText(getApplicationContext(), "Masukan failitas Kos!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ambilHargaKos)) {
                    Toast.makeText(getApplicationContext(), "Masukan harga Kos!", Toast.LENGTH_SHORT).show();
                    return;
                }

            progressBar.setVisibility(View.VISIBLE);
                String idKos = myRef.push().getKey();
                DataKos dataKos = new DataKos(
                        ambilNamaPemilik,
                        ambilHpPemilik,
                        ambilNamaKos,
                        ambilAlamatKos,
                        ambilKotaKos,
                        ambilDaerahKos,
                        ambilHargaKos,
                        ambilLokasiKos,
                        uploadFotoURL,
                longitude, latitude,
                        ambilFasilitasKos);

                myRef.child(idKos).setValue(dataKos);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Penyimpanan Data Berhasil", Toast.LENGTH_LONG).show();
                Intent refresh = new Intent(this, EditorActivity.class);
                startActivity(refresh);
                finish();
            }
        }

    public void changeAvatar(View view){
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                ImageButton avatarImageView = (ImageButton)findViewById(R.id.avatar_image_view);
                avatarImageView.setImageURI(resultUri);
                uploadImage(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                lokasi.setText(place.getAddress());
                longitude = place.getLatLng().longitude;
                latitude = place.getLatLng().latitude;
                //String toastMsg = String.format("Place: %s", place.getName());
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadImage(Uri imageUri ){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                uploadFotoURL = uri.toString();
                            }
                        });
                        Toast.makeText(PostKosanActivity.this, "Persiapan Foto Berhasil", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostKosanActivity.this, "Gagal" , Toast.LENGTH_LONG).show();
            }
        });
    }

}
