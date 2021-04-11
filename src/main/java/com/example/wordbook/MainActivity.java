package com.example.wordbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText mailEditText,passwordEditText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mailEditText = findViewById(R.id.mailId);
        passwordEditText = findViewById(R.id.passwordId);

        firebaseAuth = FirebaseAuth.getInstance();

        ///////////////DAHA ÖNCEDEN GİRİŞ YAPILDI MI DİYE KONTROL EDİYORUZ.
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            Intent intent = new Intent(MainActivity.this , ListPage.class);
            startActivity(intent);
            finish();
        }
    }


    public void signInF (View view){

        String mail = mailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        System.out.println(mail+password);

        firebaseAuth.signInWithEmailAndPassword(mail , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this , "Giriş Başarılı." , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this , ListPage.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this , e.getLocalizedMessage().toString() , Toast.LENGTH_SHORT).show();
            }
        });


    }




    public void signUpF (View view){

        String mail = mailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(mail , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {  ////EĞER ÜYE OLUŞTURMA BAŞARILI OLURSA:

                Toast.makeText(MainActivity.this , "Hesap başarıyla oluşturuldu" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this , ListPage.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {   ///// ÜYE OLUŞTURURKEN HATA OLURSA:
                Toast.makeText(MainActivity.this , e.getLocalizedMessage().toString() , Toast.LENGTH_SHORT).show();
            }
        });

    }

}