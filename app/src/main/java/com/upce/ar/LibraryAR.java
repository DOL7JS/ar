package com.upce.ar;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;


public class LibraryAR extends AppCompatActivity implements Scene.OnUpdateListener {
    public static boolean isLibraryAR = false;
    private ArFragment arCoreFragment;
    private static Model model;
    static int  MIN_OPENGL_VERSION = 3;
    HashMap<String, String> superhero_info = new HashMap<String, String>();

    @RequiresApi(api = VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!checkIsSupportedDeviceOrFinish(this)){
            startActivity(new Intent(getApplicationContext(), ModelSelection.class));
            return;
        }


        setContentView(R.layout.library_ar);
        isLibraryAR = true;
        arCoreFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arCoreFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdate);
        arCoreFragment.getPlaneDiscoveryController().hide();
        Button showInfoButton = findViewById(R.id.btn_showInfo);
        Button backToSelectModelButton = findViewById(R.id.btn_backToSelectModel);
        backToSelectModelButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ModelSelection.class)));
        TextView info = findViewById(R.id.textView_info);
        ConstraintLayout infoLayout = findViewById(R.id.constraintLayoutInfo);
        infoLayout.setVisibility(View.GONE);

        loadSuperHeroInfo();
        showInfoButton.setOnClickListener(v -> {
            infoLayout.setVisibility(View.VISIBLE);
            showInfoButton.setVisibility(View.GONE);
            info.setText(superhero_info.get(model.getModelName()));

        });
        info.setOnClickListener(v -> {
            infoLayout.setVisibility(View.GONE);
            showInfoButton.setVisibility(View.VISIBLE);
        });
    }

    private void loadSuperHeroInfo() {
        superhero_info.put("Batarang", "Název: Batarang\n" +
                "Patří Batmanovi");
        superhero_info.put("Black panther", "Skutečné jméno: T'Challa\n" +
                "Superhrdinské jméno: Black Panther\n" +
                "Vytvořil: Stan Lee a Jack Kirby \n" +
                "Schopnosti: síla, rychlost, technické \nvychytávky");
        superhero_info.put("Black panther - náhrdelník", "Název: Náhrdelník\n" +
                "Patří Black Pantherovi");
        superhero_info.put("Captain America", "Skutečné jméno: Steve Rogers\n" +
                "Superhrdinské jméno: Captain America\n" +
                "Vytvořil: Joe Simon a Jack Kirby \n" +
                "Schopnosti: síla, rychlost, vytrvalost");
        superhero_info.put("Captain America - štít", "Název: Štít\n" +
                "Patří Kapitánu Amerikovi");
        superhero_info.put("Captain Marvel", "Skutečné jméno: Carol Danvers\n" +
                "Superhrdinské jméno: Captain Marvel\n" +
                "Vytvořil: Roy Thomas a Gene Colan \n" +
                "Schopnosti: létání, nadlidská fyziologie, výboje fotonů");
        superhero_info.put("Hulk", "Skutečné jméno: Bruce Banner\n" +
                "Superhrdinské jméno: Hulk\n" +
                "Vytvořil: Stan Lee a Jack Kirby\n" +
                "Schopnosti: nadpřirozená síla");
        superhero_info.put("Iron man", "Skutečné jméno: Tony Stark\n" +
                "Superhrdinské jméno: Iron Man\n" +
                "Vytvořil: Stan Lee\n" +
                "Schopnosti: oblek, díky kterému má větší\n sílu, umí létat, zbraňový systém");
        superhero_info.put("Mjolnir", "Název: Kladivo Mjolnir\n" +
                "Patří Thorovi");
        superhero_info.put("Shazam", "Skutečné jméno: Billy Batson\n" +
                "Superhrdinské jméno: Shazam\n" +
                "Vytvořil: C. C. Beck Bill Parker\n" +
                "Schopnosti: létaní, síla, ovládání blesků");
        superhero_info.put("Spider Gwen", "Skutečné jméno: Gwen Stacy\n" +
                "Superhrdinské jméno: Spider Gwen Man\n" +
                "Vytvořil: Stan Lee a Steve Ditko\n" +
                "Schopnosti: pavoučí smysl, síla, \nrychlost, šplhání po stěnách ");
        superhero_info.put("Spiderman", "Skutečné jméno: Peter Parker\n" +
                "Superhrdinské jméno: Spider man\n" +
                "Vytvořil: Stan Lee a Steve Ditko\n" +
                "Schopnosti: pavoučí smysl, síla, \nrychlost, šplhání po stěnách");
        superhero_info.put("Starlordova helma", "Název: Vesmírná helma\n" +
                "Patří Starlordovi");
        superhero_info.put("Storm breaker", "Název: Storm breaker\n" +
                "Patří Thorovi");
        superhero_info.put("Batman", "Skutečné jméno: Bruce Wayne\n" +
                "Superhrdinské jméno: Batman\n" +
                "Vytvořil: Bob Kane a Bill Finger\n" +
                "Schopnosti: mistr bojových umění,\n mistr zbraní, detektivní schopnosti");
        superhero_info.put("Vision",
                "Superhrdinské jméno: Vision\n" +
                "Vytvořil: Roy Thomas a John Buscema\n" +
                "Schopnosti: síla, létání, fázování");

    }

    public boolean buildDatabase(Config config, Session sessionIn) {
        AugmentedImageDatabase augmentedImageDatabase = new AugmentedImageDatabase(sessionIn);
        Bitmap bitmap = loadImage(model.getQrImage());
        if (bitmap == null) {
            return false;
        }
        augmentedImageDatabase.addImage(model.getModelName(), bitmap);
        config.setAugmentedImageDatabase(augmentedImageDatabase);
        return true;
    }

    private Bitmap loadImage(String filename) {
        try {
            InputStream is = getApplicationContext().getAssets().open(filename);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = VERSION_CODES.N)
    @Override
    public void onUpdate(FrameTime frameTime) {
        Frame frame = arCoreFragment.getArSceneView().getArFrame();
        Collection<AugmentedImage> updateAugmentedImg = frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage image : updateAugmentedImg) {
            if (image.getTrackingState() == TrackingState.TRACKING) {
                if (image.getName().equals(model.getModelName()) && !model.getIsPlaced()) {
                    model.setIsPlaced(true);
                    placeObject(arCoreFragment, image.createAnchor(image.getCenterPose()), model.getModel());
                }
            }
        }
    }

    @RequiresApi(api = VERSION_CODES.N)
    private void placeObject(ArFragment arFragment, Anchor anchor, int object) {
        ModelRenderable.builder()
                .setSource(Objects.requireNonNull(arFragment.getContext()), object)
                .build()
                .thenAccept(modelRenderable -> addNodeToScene(arFragment, anchor, modelRenderable))
                .exceptionally(throwable -> {
                    Toast.makeText(arFragment.getContext(), "Error:" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    return null;
                });
    }

    private void addNodeToScene(ArFragment arFragment, Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setRenderable(modelRenderable);
        node.setParent(anchorNode);
        node.getScaleController().setMinScale(0.01f);
        node.getScaleController().setMaxScale(model.getMaxScale());
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    public static void setModel(Model models) {
        LibraryAR.model = models;
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        return true;
    }

}