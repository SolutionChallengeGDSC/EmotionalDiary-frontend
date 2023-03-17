package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// Todo : Google Login access token 처리 + 일기 비밀번호 설정 dialog + profile img -> google 계정 사진 + 무한스크롤 ..

public class LoginActivity extends AppCompatActivity {
    TextView txt_login;
    AppCompatImageButton btn_google;
    boolean isLogin;
    ImageView logo_login;

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;


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
            String idToken = account.getIdToken(); // 로그인 토큰을 가져옵니다.
            String name = account.getGivenName();
            String authToken = account.getServerAuthCode(); //

            // TODO: 이제이 토큰을 사용하여 서버에서 사용자를 인증하십시오.
            Toast.makeText(this, "idToken : " + idToken, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "authToken : " + authToken, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "name : " + name, Toast.LENGTH_SHORT).show();

        } else {
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Toast.makeText(this, "onActivityResult success ", Toast.LENGTH_SHORT).show();
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "handleSignInResult success ", Toast.LENGTH_SHORT).show();
            Uri ur = account.getPhotoUrl();
            logo_login.setImageURI(ur);
            Toast.makeText(this,"photoUrl : "+ ur.toString(), Toast.LENGTH_SHORT).show();

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "handleSignInResult fail " + "signInResult:failed code=" + e.getMessage(), Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }

}
