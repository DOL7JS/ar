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
        superhero_info.put("Batarang", "N??zev: Batarang\n" +
                "Pat???? Batmanovi");
        superhero_info.put("Black panther", "Skute??n?? jm??no: T'Challa\n" +
                "Superhrdinsk?? jm??no: Black Panther\n" +
                "Vytvo??il: Stan Lee a Jack Kirby \n" +
                "Schopnosti: s??la, rychlost, technick?? vychyt??vky");
        superhero_info.put("Black panther - n??hrdeln??k", "N??zev: N??hrdeln??k\n" +
                "Pat???? Black Pantherovi");
        superhero_info.put("Captain America", "Skute??n?? jm??no: Steve Rogers\n" +
                "Superhrdinsk?? jm??no: Captain America\n" +
                "Vytvo??il: Joe Simon a Jack Kirb \n" +
                "Schopnosti: s??la, rychlost, vytrvalost");
        superhero_info.put("Captain America - ??t??t", "N??zev: ??t??t\n" +
                "Pat???? Kapit??nu Amerikovi");
        superhero_info.put("Captain Marvel", "Skute??n?? jm??no: Carol Danvers\n" +
                "Superhrdinsk?? jm??no: Captain Marvel\n" +
                "Vytvo??il: Roy Thomas a Gene Colan \n" +
                "Schopnosti: l??t??n??, nadlidsk?? fyziologie, v??boje foton??");
        superhero_info.put("Hulk", "Skute??n?? jm??no: Bruce Banner\n" +
                "Superhrdinsk?? jm??no: Hulk\n" +
                "Vytvo??il: Stan Lee a Jack Kirby\n" +
                "Schopnosti: nadp??irozen?? s??la");
        superhero_info.put("Iron man", "Skute??n?? jm??no: Tony Stark\n" +
                "Superhrdinsk?? jm??no: Iron Man\n" +
                "Vytvo??il: Stan Lee\n" +
                "Schopnosti: oblek, d??ky kter??mu m?? v??t????\n s??lu, um?? l??tat, zbra??ov?? syst??m");
        superhero_info.put("Mjolnir", "N??zev: Kladivo Mjolnir\n" +
                "Pat???? Thorovi");
        superhero_info.put("Shazam", "Skute??n?? jm??no: Billy Batson\n" +
                "Superhrdinsk?? jm??no: Shazam\n" +
                "Vytvo??il: C. C. Beck Bill Parker\n" +
                "Schopnosti: l??tan??, s??la, ovl??d??n?? blesk??");
        superhero_info.put("Spider Gwen", "Skute??n?? jm??no: Gwen Stacy\n" +
                "Superhrdinsk?? jm??no: Spider Gwen Man\n" +
                "Vytvo??il: Stan Lee a Steve Ditko\n" +
                "Schopnosti: pavou???? smysl, s??la, \nrychlost, ??plh??n?? po st??n??ch ");
        superhero_info.put("Spiderman", "Skute??n?? jm??no: Peter Parker\n" +
                "Superhrdinsk?? jm??no: Spider man\n" +
                "Vytvo??il: Stan Lee a Steve Ditko\n" +
                "Schopnosti: pavou???? smysl, s??la, \nrychlost, ??plh??n?? po st??n??ch");
        superhero_info.put("Starlordova helma", "N??zev: Vesm??rn?? helma\n" +
                "Pat???? Starlordovi");
        superhero_info.put("Storm breaker", "N??zev: Storm breaker\n" +
                "Pat???? Thorovi");
        superhero_info.put("Batman", "Skute??n?? jm??no: Bruce Wayne\n" +
                "Superhrdinsk?? jm??no: Batman\n" +
                "Vytvo??il: Bob Kane a Bill Finger\n" +
                "Schopnosti: mistr bojov??ch um??n??,\n mistr zbran??, detektivn?? schopnosti");
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
            //activity.finish();
            return false;
        }
        return true;
    }

}