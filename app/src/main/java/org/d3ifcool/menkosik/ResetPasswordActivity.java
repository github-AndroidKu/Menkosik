package org.d3ifcool.menkosik;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button btnReset;
    private FirebaseAuth auth;
    private EditText inputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

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

        Toolbar ToolBarAtas1 = (Toolbar)findViewById(R.id.toolbar_reset);
        setSupportActionBar(ToolBarAtas1);
        ToolBarAtas1.setLogoDescription(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        inputEmail = (EditText) findViewById(R.id.email_reset_password);
        btnReset = (Button) findViewById(R.id.button_reset_password);
        auth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Masukan Email anda", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPasswordActivity.this, "Silahkan cek email anda untuk mereset password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this, "Gagal kirim ke email email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}


