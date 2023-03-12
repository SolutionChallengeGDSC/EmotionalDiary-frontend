package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    TextView txt_login;
    AppCompatImageButton btn_google;
    boolean isLogin;


    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;


    public static int RC_SIGN_IN = 1000; // 임의의 값

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        isLogin = false;
        txt_login = findViewById(R.id.txt_login);
        btn_google = findViewById(R.id.btn_google);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        txt_login.setTypeface(txt_login.getTypeface(), Typeface.ITALIC);


        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = true;
                signIn();
            }
        });

    }


    public void updateUI(GoogleSignInAccount account){
        if(account != null){
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            Toast.makeText(this, "personName : "+ personName, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "personGivenName : "+ personGivenName, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "personFamilyName : "+ personFamilyName, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "personEmail : "+ personEmail, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "personId : "+ personId, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "personPhoto : "+ personPhoto, Toast.LENGTH_SHORT).show();
            startActivity( new Intent(this, MainActivity.class));
            finish();
        }
        else{
            Toast.makeText(this, "You Didn't Sing in", Toast.LENGTH_SHORT).show();

        }

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        }catch (ApiException e){
            Log.w(TAG,"signInResult:failed code = " + e.getStatusCode());
            updateUI(null);
        }
    }
}
