package com.upce.ar;


import android.content.Context;
import android.content.res.Resources;

public class Model {
    private String name;
    private boolean isPlaced;
    private String qrImage;
    private int model;
    private String imageName;
    private float maxScale;

    Model(String name, boolean isPlaced, String qrImage, int model, float maxScale) {
        this.name = name;
        this.isPlaced = isPlaced;
        this.qrImage = qrImage;
        this.model = model;
        this.maxScale = maxScale;
    }

    Model(String name, boolean isPlaced, String qrImage, int model, float maxScale, String imageName) {
        this.name = name;
        this.isPlaced = isPlaced;
        this.qrImage = qrImage;
        this.model = model;
        this.maxScale = maxScale;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public String getModelName() {
        return name;
    }

    public String getQrImage() {
        return qrImage;
    }

    public void setIsPlaced(boolean bool) {
        isPlaced = bool;
    }

    public boolean getIsPlaced() {
        return isPlaced;
    }

    public int getModel() {
        return model;
    }


    public float getMaxScale() {
        return maxScale;
    }

    public String toString() {
        return name;
    }

    public static int getIdOf3DModel(String modelName, Context context) {
        Resources res = context.getResources();
        return res.getIdentifier(modelName, "raw", context.getPackageName());
    }
}
