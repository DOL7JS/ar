package com.upce.ar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ModelSelection extends AppCompatActivity {
    ArrayList<Model> modelList;
    ListView listViewModels;
    ImageView modelImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model_selection);
        Button btnToLibraryAR = findViewById(R.id.btnToLibraryAR);
        ProgressBar loadingBar = findViewById(R.id.progressBarLoading);
        loadingBar.setVisibility(View.GONE);
        listViewModels = findViewById(R.id.listViewModels);
        Button backButton = findViewById(R.id.btnBack);
        modelImage = findViewById(R.id.imageViewModel);
        loadModelImage("noModels");
        loadModels();
        backButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HomeScreen.class)));
        btnToLibraryAR.setOnClickListener(v -> {
            if (listViewModels.getCheckedItemPosition() != -1) {
                loadingBar.setVisibility(View.VISIBLE);
                LibraryAR.setModel(modelList.get(listViewModels.getCheckedItemPosition()));
                startActivity(new Intent(getApplicationContext(), LibraryAR.class));
            } else {
                Toast.makeText(getApplicationContext(), "Nevybral jste zadny model", Toast.LENGTH_LONG).show();
            }
        });


        listViewModels.setOnItemClickListener((parent, view, position, id) -> loadModelImage(modelList.get(position).getImageName()));
    }

    private void loadModelImage(String image) {
        InputStream is = null;
        try {
            is = getApplicationContext().getAssets().open(image + ".PNG");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        modelImage.setImageBitmap(bitmap);
    }

    private void loadModels() {
        listViewModels.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        modelList = new ArrayList<>();
        modelList.add(new Model("Iron man", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("iron_man", getApplicationContext()), 0.08f, "iron_man"));
        modelList.add(new Model("Hulk", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("hulk", getApplicationContext()), 0.1f, "hulk"));
        modelList.add(new Model("Spiderman", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("spiderman", getApplicationContext()), 0.04f, "spiderman"));
        modelList.add(new Model("Batman", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("batman", getApplicationContext()), 0.09f, "batman"));
        modelList.add(new Model("Batarang", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("batarang", getApplicationContext()), 0.2f, "batarang"));
        modelList.add(new Model("Black panther", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("black_panther", getApplicationContext()), 0.08f, "black_panther"));
        modelList.add(new Model("Captain America", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("captain_america", getApplicationContext()), 0.03f, "captain_america"));
        modelList.add(new Model("Black panther - náhrdelník", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("black_panther_necklace", getApplicationContext()), 0.35f, "black_panther_necklace"));
        modelList.add(new Model("Captain America - štít", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("captain_america_shield", getApplicationContext()), 0.1f, "captain_america_shield"));
     //   modelList.add(new Model("Captain Marvel", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("captain_marvel", getApplicationContext()), 0.1f, "captain_marvel"));
        modelList.add(new Model("Mjolnir", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("mjolnir", getApplicationContext()), 0.04f, "mjolnir"));
        modelList.add(new Model("Starlordova helma", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("star_lord_helmet", getApplicationContext()), 0.15f, "star_lord_helmet"));
        modelList.add(new Model("Shazam", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("shazam", getApplicationContext()), 0.1f, "shazam"));
        modelList.add(new Model("Spider Gwen", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("spider_gwen", getApplicationContext()), 0.03f, "spider_gwen"));
        modelList.add(new Model("Storm breaker", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("storm_breaker", getApplicationContext()), 0.02f, "storm_breaker"));
        modelList.add(new Model("Vision", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("vision", getApplicationContext()), 0.09f, "vision"));

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, modelList);
        listViewModels.setAdapter(arrayAdapter);
    }

}