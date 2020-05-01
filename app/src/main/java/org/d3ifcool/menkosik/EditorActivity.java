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
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditorActivity extends AppCompatActivity {
    private Button signOut;

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

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

        auth =FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(EditorActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        signOut = (Button) findViewById(R.id.logout);
        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        toolbar.setLogoDescription(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        TextView editProfile = (TextView) findViewById(R.id.edit_profile);
//        editProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), EditProfileActivity.class);
//                startActivityForResult(intent, 0);
//                finish();
//            }
//        });

        TextView post = (TextView) findViewById(R.id.post_kosan);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PostKosanActivity.class);
                startActivityForResult(intent, 0);
                finish();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
