package com.GDSC.emotionaldiary;

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
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


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
            String url = "http://34.64.254.35/user/sign-up";
            String urlGetUserid = "http://34.64.254.35/user?email=" + emailToken; // get UserId

            new Thread(() ->{
                int userId = 0;
                try{
                    HttpClient getUserId = new HttpClient();
                    String responsGetUserId = getUserId.get(urlGetUserid);
                    JSONObject jsonGetUserId = new JSONObject(responsGetUserId); // get json
                    Log.e("jsonGetUserId",jsonGetUserId.toString());
                    int statusGetUserId = jsonGetUserId.getInt("status");
                    if(statusGetUserId == 200){ // 존재하는 유저
                        Log.e("status", Integer.toString(statusGetUserId));
                        JSONObject resultGetUserId = jsonGetUserId.getJSONObject("result");
                        userId = resultGetUserId.getInt("id");
                    }
//                    else if(statusGetUserId == 403){ // 존재하지 않는 유저 -> sign up
//
//                        HttpPost userInfo = new HttpPost();
//                        JSONObject jsonPostSignUp = new JSONObject(); // post json
//
//                        jsonPostSignUp.put("email",emailToken);
//                        jsonPostSignUp.put("nickname", nameToken);
//                        jsonPostSignUp.put("picture", photoUri);
//                        String responsePostSignUp = userInfo.post(url,jsonPostSignUp.toString());
//                        System.out.println("json : "+ jsonPostSignUp);
//                        Log.e("json_posted", jsonPostSignUp.toString());
//
//                        System.out.println(responsePostSignUp);
//                        Log.e("responsePostSignUp", responsePostSignUp);
//
//                        JSONObject jObject = new JSONObject(responsePostSignUp.toString());;
//                        int statusPost = jObject.getInt("status");
//                        if(statusPost == 200){ // 새로운 유저 DB 생성
//                            Log.e("status", Integer.toString(statusPost));
//                            JSONObject resultPostSignUp = jObject.getJSONObject("result");
//                            userId = resultPostSignUp.getInt("id"); // user id
//                        }
//                    }

                    // 프레퍼런스로 userEmail 저장
                    SharedPreferences prefs = getSharedPreferences("user_info", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("userEmail", emailToken);
                    editor.apply();

                    Intent signInIntent = new Intent(LoginActivity.this, MainActivity.class);
                    signInIntent.putExtra("userId",userId);
                    signInIntent.putExtra("name", nameToken);
                    signInIntent.putExtra("profileImg",photoUri);
                    signInIntent.putExtra("userEmail",emailToken);
                    startActivity(signInIntent);
                    finish();





//
//                    if(status == 200){ // 새로운 유저 DB 생성
//                        Log.e("status", Integer.toString(status));
//
//                        JSONObject result = jObject.getJSONObject("result");
//                        int userId = result.getInt("id"); // user id
//
//                        Log.e("userId", Integer.toString(userId));
//
//                        Intent signInIntent = new Intent(LoginActivity.this, MainActivity.class);
//                        signInIntent.putExtra("name", nameToken);
//                        signInIntent.putExtra("profileImg",photoUri);
//                        startActivity(signInIntent);
//                        finish();
//                    }
//                    else if(status == 403){
//                        // 같은 이메일의 유저 존재
//                        Log.e("status", Integer.toString(status));
//                        Intent signInIntent = new Intent(LoginActivity.this, MainActivity.class);
//                        signInIntent.putExtra("name", nameToken);
//                        signInIntent.putExtra("profileImg",photoUri);
//                        startActivity(signInIntent);
//                        finish();
//                    }
                }catch (IOException e){
                    Log.e("IOException : ", e.getMessage());
                }catch (JSONException e) {
                    Log.e("RuntimeException :", e.getMessage());
                    throw new RuntimeException(e);
                }
            }).start();

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
            Toast.makeText(this, "handleSignInResult fail " + "signInResult:failed code=" + e, Toast.LENGTH_SHORT).show();
            Log.e("handleSignInResult fail", e.getMessage());
            updateUI(null);
        }
    }



}
