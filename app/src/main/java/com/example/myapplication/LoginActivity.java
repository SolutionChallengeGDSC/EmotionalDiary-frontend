package com.example.myapplication;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.squareup.picasso.Picasso;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;


// Todo : Google Login access token 처리 + 일기 비밀번호 설정 dialog + profile img -> google 계정 사진 + 무한스크롤 ..

public class LoginActivity extends AppCompatActivity {
    TextView txt_login;
    AppCompatImageButton btn_google;
    boolean isLogin;
    ImageView logo_login;

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "TestActivity";
    String nameToken;
    String emailToken;

    public static int RC_SIGN_IN = 1000; // 임의의 값

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        isLogin = false;
        txt_login = findViewById(R.id.txt_login);
        logo_login = findViewById(R.id.logo_login);
        btn_google = findViewById(R.id.btn_google);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleSignInClient.signOut(); // 로그인 초기화


        txt_login.setTypeface(txt_login.getTypeface(), Typeface.ITALIC);


        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = true;
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }
    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            // 사용자가 로그인한 경우
            nameToken = account.getGivenName();
            emailToken = account.getEmail();
            Uri photoUri = account.getPhotoUrl();
            Picasso.get().load(photoUri).into(logo_login);
            String url = "http://10.0.2.2:8080/user/sign-up";
            AtomicReference<String> responseUserInfo = new AtomicReference<>("");

            new Thread(() ->{
                try{
                    HttpPost userInfo = new HttpPost();
                    String json = "{\"email\":\"" + emailToken + "\",\"nickname\":\""+nameToken + "\"}";
                    String response = userInfo.post(url,json);
                    System.out.println("json : "+ json);
                    responseUserInfo.set(response);
                    System.out.println(response);
                }catch (IOException e){
                    Log.e("IOException : ", e.getMessage());
                }

            }).start();

            Log.e("responseUserInfo",responseUserInfo.toString());
            Intent signInIntent = new Intent(LoginActivity.this, MainActivity.class);
            signInIntent.putExtra("name", nameToken);
            signInIntent.putExtra("profileImg",photoUri);
            startActivity(signInIntent);
            finish();
        } else {

        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            updateUI(account);
        } catch (ApiException e) {
            Toast.makeText(this, "handleSignInResult fail " + "signInResult:failed code=" + e.getMessage(), Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }



}
