package com.upce.ar;

import android.util.Log;
import android.widget.Toast;

import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

public class CustomArFragment extends ArFragment {
    @Override
    protected Config getSessionConfiguration(Session session) {
        getPlaneDiscoveryController().setInstructionView(null);
        Config config = new Config(session);
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        session.configure(config);
        getArSceneView().setupSession(session);

        if (LibraryAR.isLibraryAR) {
            if ((((LibraryAR) getActivity()).buildDatabase(config, session))) {
                Log.d("SetupAugImgDb", "Success");
            } else {
                Toast.makeText(getContext(), " SetupAugImgDb -- Faliure setting up db", Toast.LENGTH_LONG).show();
            }
        } else {
            if ((((QuizAR) getActivity()).buildDatabase(config, session))) {
                Log.d("SetupAugImgDb", "Success");
            } else {
                Toast.makeText(getContext(), " SetupAugImgDb -- Faliure setting up db", Toast.LENGTH_LONG).show();
            }
        }

        return config;
    }
}
