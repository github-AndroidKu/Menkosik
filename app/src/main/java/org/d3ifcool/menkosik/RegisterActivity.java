package org.d3ifcool.menkosik;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword, inputNama, inputTelepon, inputAlamat;
    private ProgressBar progressBar;
    private Button btnSignUp;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail = (EditText) findViewById(R.id.email_registered);
        inputPassword = (EditText) findViewById(R.id.password_registered);

        auth = FirebaseAuth.getInstance();
        btnSignUp = (Button) findViewById(R.id.button_registered);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Toolbar ToolBarAtas1 = (Toolbar)findViewById(R.id.toolbar_register);
        setSupportActionBar(ToolBarAtas1);
        ToolBarAtas1.setLogoDescription(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        TextView text = (TextView) findViewById(R.id.login);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(intent, 0);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Masukan Email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Masukan Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password minimal 6 karakter!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "Registrasi Berhasil, Silahkan Login." + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Registrasi Gagal, Coba ulangi kembali." + task.getException(),Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
