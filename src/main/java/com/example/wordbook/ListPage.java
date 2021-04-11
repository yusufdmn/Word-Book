package com.example.wordbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ListPage extends AppCompatActivity {

    ImageView panel;
    ImageButton imageButton,exitPanel;
    EditText listNameEditText;
    Button addListButton;

    RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

        panel = findViewById(R.id.panelId);
        exitPanel = findViewById(R.id.exitPanelId);
        listNameEditText = findViewById(R.id.listNameEditTextId);
        addListButton = findViewById(R.id.AddListButtonId);

        imageButton = findViewById(R.id.imageButton);
        recyclerView = findViewById(R.id.recyclerView);

        ////////////
        panel.setVisibility(View.INVISIBLE);
        exitPanel.setVisibility(View.INVISIBLE);
        listNameEditText.setVisibility(View.INVISIBLE);
        addListButton.setVisibility(View.INVISIBLE);
        ///////////

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void exitF(View view){
        recyclerView.setEnabled(true);
        panel.setVisibility(View.INVISIBLE);
        exitPanel.setVisibility(View.INVISIBLE);
        listNameEditText.setVisibility(View.INVISIBLE);
        addListButton.setVisibility(View.INVISIBLE);
        listNameEditText.setText("");

    }

    public void panelF(View view){
        recyclerView.setEnabled(false);
        panel.setVisibility(View.VISIBLE);
        exitPanel.setVisibility(View.VISIBLE);
        listNameEditText.setVisibility(View.VISIBLE);
        addListButton.setVisibility(View.VISIBLE);
    }


    public void addListF(View view){


        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String email = firebaseUser.getEmail();
        String listName = listNameEditText.getText().toString();

        HashMap<String , Object> listData = new HashMap<>();
        listData.put("email" , email);
        listData.put("name" , listName);
        listData.put("time" , FieldValue.serverTimestamp());  //// O anki zamanı söylüyor.

        firebaseFirestore.collection("lists").add(listData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                recyclerView.setEnabled(true);
                panel.setVisibility(View.INVISIBLE);
                exitPanel.setVisibility(View.INVISIBLE);
                listNameEditText.setVisibility(View.INVISIBLE);
                addListButton.setVisibility(View.INVISIBLE);
                listNameEditText.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ListPage.this , e.getLocalizedMessage().toString() , Toast.LENGTH_SHORT).show();
            }
        });
    }







    /////MENÜ

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu , menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

         if (item.getItemId() == R.id.signOutId){


            firebaseAuth.signOut();
            Intent intentToEnter = new Intent(ListPage.this , MainActivity.class);
            startActivity(intentToEnter);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}