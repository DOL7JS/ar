package com.upce.ar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.upce.ar.LibraryAR.checkIsSupportedDeviceOrFinish;

public class QuizAR extends AppCompatActivity implements Scene.OnUpdateListener {

    private ArFragment arCoreFragment;
    private static ArrayList<Model> models;
    private String actualModel;
    static List<String> answerList;
    ListView listViewAnswers;
    private static Questions.Question question;
    private TextView labelQuestion;
    AugmentedImageDatabase augmentedImageDatabase;
    Config conf;
    Questions questions;
    ConstraintLayout moveToNextQuestionLayout;
    ImageView imageViewFirstStar;
    ImageView imageViewSecondStar;
    ImageView imageViewThirdStar;
    Button endButton;
    Button answersButton;
    Button questionButton;
    Button confirmAnswerButton;
    Button btnNextQuestion;
    ConstraintLayout questionLayout;
    TextView textViewEvaluation;
    TextView textViewPoints;
    boolean endOfQuiz = false;
    int numberOfQuestion = 10;
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_ar);
        if(!checkIsSupportedDeviceOrFinish(this)){
            startActivity(new Intent(getApplicationContext(), ModelSelection.class));
            return;
        }
        LibraryAR.isLibraryAR = false;
        arCoreFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arCoreFragment.getArSceneView().getScene().addOnUpdateListener(this);
        arCoreFragment.getPlaneDiscoveryController().hide();
        endButton = findViewById(R.id.btnEnd);
        answersButton = findViewById(R.id.btnAnswers);
        questionButton = findViewById(R.id.btnQuestion);
        confirmAnswerButton = findViewById(R.id.btnConfirmAnswer);
        btnNextQuestion = findViewById(R.id.btnNextQuestion);
        Score.setScoreZero();
        questions = new Questions();
        questionButton.setVisibility(View.GONE);
        questionLayout = findViewById(R.id.constraintLayoutQuestion);
        moveToNextQuestionLayout = findViewById(R.id.constraintLayout_nextQuestion);
        moveToNextQuestionLayout.setVisibility(View.GONE);
        labelQuestion = findViewById(R.id.textView_info);
        textViewEvaluation = findViewById(R.id.textView_evaluation);
        textViewPoints = findViewById(R.id.textView_points);
        btnNextQuestion.setOnClickListener(v -> {
            if (endOfQuiz) {
                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                return;
            }
            if (questions.getCountQuestions() == numberOfQuestion) {
                textViewEvaluation.setText("");
                imageViewFirstStar.setImageResource(0);
                imageViewSecondStar.setImageResource(0);
                imageViewThirdStar.setImageResource(0);
                textViewPoints.setText("Získal jste " + Score.getScore() + " z " + 3 * questions.getCountQuestions() + " možných bodů ! \nGratuluji !");
                moveToNextQuestionLayout.getLayoutParams().height = 1000;
                moveToNextQuestionLayout.setVisibility(View.VISIBLE);
                endOfQuiz = true;
                return;
            }
            endButton.setEnabled(true);
            answersButton.setEnabled(true);
            confirmAnswerButton.setEnabled(true);
            questionButton.setEnabled(true);
            questionLayout.setEnabled(true);
            listViewAnswers.setEnabled(true);
            moveToNextQuestionLayout.setVisibility(View.GONE);
        });
        listViewAnswers = findViewById(R.id.listViewAnswers);
        listViewAnswers.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewAnswers.setVisibility(View.GONE);
        imageViewFirstStar = findViewById(R.id.imageView_firstStar);
        imageViewSecondStar = findViewById(R.id.imageView_secondStar);
        imageViewThirdStar = findViewById(R.id.imageView_thirdStar);

        endButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QuizWelcome.class)));
        confirmAnswerButton.setOnClickListener(v -> {
            if (listViewAnswers.getCheckedItemPosition() == -1) {
                Toast.makeText(this, "Nevybral si odpověď, zkus to znovu", Toast.LENGTH_SHORT).show();
                return;
            }
            question.setAlreadyAnswered(true);
            question.addAttempts();
            if (answerIsCorrect()) {
                listViewAnswers.setVisibility(View.GONE);
                Score.addScore(4 - question.getAttempts());
                NextQuestionLayoutVisible();
                generateNewRandomQuestion();
                List<Node> nodes = arCoreFragment.getArSceneView().getScene().getChildren();
                while (nodes.size() > 2) {
                    nodes.get(2).setEnabled(false);
                    for (int i = 0; i < models.size(); i++) {
                        models.get(i).setIsPlaced(false);
                    }
                    arCoreFragment.getArSceneView().getScene().removeChild(nodes.get(2));
                }
            } else {
                Toast.makeText(this, "Zkus to znovu", Toast.LENGTH_SHORT).show();
            }

        });
        answersButton.setOnClickListener(v -> {
            if (listViewAnswers.getVisibility() == View.VISIBLE) {
                listViewAnswers.setVisibility(View.GONE);
            } else {
                listViewAnswers.setVisibility(View.VISIBLE);
            }
        });
        questionButton.setOnClickListener(v -> {
            questionLayout.setVisibility(View.VISIBLE);
            questionButton.setVisibility(View.GONE);
        });
        questionLayout.setOnClickListener(v -> {
            questionLayout.setVisibility(View.GONE);
            questionButton.setVisibility(View.VISIBLE);
        });
        generateNewRandomQuestion();
        setModels();
    }

    @SuppressLint("SetTextI18n")
    private void NextQuestionLayoutVisible() {
        switch (question.getAttempts()) {
            case 3:
                imageViewFirstStar.setImageResource(R.drawable.star_yellow);
                imageViewSecondStar.setImageResource(R.drawable.star_grey);
                imageViewThirdStar.setImageResource(R.drawable.star_grey);
                textViewEvaluation.setText("Dobrý !");
                textViewPoints.setText("Získal jsi 1 bod");
                break;
            case 2:
                imageViewFirstStar.setImageResource(R.drawable.star_yellow);
                imageViewSecondStar.setImageResource(R.drawable.star_yellow);
                imageViewThirdStar.setImageResource(R.drawable.star_grey);
                textViewEvaluation.setText("Super !");
                textViewPoints.setText("Získal jsi 2 body");
                break;
            case 1:
                imageViewFirstStar.setImageResource(R.drawable.star_yellow);
                imageViewSecondStar.setImageResource(R.drawable.star_yellow);
                imageViewThirdStar.setImageResource(R.drawable.star_yellow);
                textViewEvaluation.setText("Výborně !");
                textViewPoints.setText("Získal jsi 3 body");
                break;
            default:
                imageViewFirstStar.setImageResource(R.drawable.star_grey);
                imageViewSecondStar.setImageResource(R.drawable.star_grey);
                imageViewThirdStar.setImageResource(R.drawable.star_grey);
                textViewEvaluation.setText("Škoda !");
                textViewPoints.setText("Získal jsi 0 bodů");
                break;
        }
        endButton.setEnabled(false);
        answersButton.setEnabled(false);
        confirmAnswerButton.setEnabled(false);
        questionButton.setEnabled(false);
        questionLayout.setEnabled(false);
        listViewAnswers.setEnabled(false);

        moveToNextQuestionLayout.setVisibility(View.VISIBLE);
    }

    private void generateNewRandomQuestion() {

        Questions.Question newQuestion = questions.getRandomQuestion();
        if (newQuestion != null) {
            setQuestion(newQuestion);
        } else {
            Toast.makeText(this, "Vyčerpal jste otázky", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), HomeScreen.class));
        }
        actualModel = newQuestion.getModel();
        labelQuestion.setText(newQuestion.getQuestion());
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, answerList);
        listViewAnswers.setAdapter(arrayAdapter);
    }

    private float getMaxScaleActualModel(String modelName) {
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).getModelName().equals(modelName)) {
                return models.get(i).getMaxScale();
            }
        }
        return 0.5f;
    }

    private boolean answerIsCorrect() {
        return answerList.get(listViewAnswers.getCheckedItemPosition()).equals(question.getAnswer());
    }


    public boolean buildDatabase(Config config, Session sessionIn) {
        conf = config;
        augmentedImageDatabase = new AugmentedImageDatabase((sessionIn));
        for (int i = 0; i < models.size(); i++) {
            Bitmap bitmap = loadImage(models.get(i).getQrImage());
            if (bitmap == null) {
                return false;
            }
            augmentedImageDatabase.addImage(models.get(i).getModelName(), bitmap);
        }
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
                for (int i = 0; i < models.size(); i++) {
                    if (actualModel.equals(models.get(i).getModelName()) && !models.get(i).getIsPlaced()) {
                        models.get(i).setIsPlaced(true);
                        placeObject(arCoreFragment, image.createAnchor(image.getCenterPose()), models.get(i).getModel());
                    }
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
        node.getScaleController().setMaxScale(getMaxScaleActualModel(actualModel));
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    public void setQuestion(Questions.Question question) {
        QuizAR.question = question;
        answerList = question.getPossibleAnswers();

    }

    public void setModels() {
        models = new ArrayList<>();
        models.add(new Model("iron_man", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("iron_man", getApplicationContext()), 0.035f));
        models.add(new Model("hulk", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("hulk", getApplicationContext()), 0.1f));
        models.add(new Model("spiderman", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("spiderman", getApplicationContext()), 0.04f));
        models.add(new Model("batman", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("batman", getApplicationContext()), 0.1f));
        models.add(new Model("batarang", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("batarang", getApplicationContext()), 0.2f));
        models.add(new Model("black_panther", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("black_panther", getApplicationContext()), 0.08f));
        models.add(new Model("captain_america", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("captain_america", getApplicationContext()), 0.03f));
        models.add(new Model("black_panther_necklace", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("black_panther_necklace", getApplicationContext()), 0.35f));
        models.add(new Model("captain_america_shield", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("captain_america_shield", getApplicationContext()), 0.1f));
        models.add(new Model("captain_marvel", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("captain_marvel", getApplicationContext()), 0.1f));
        models.add(new Model("mjolnir", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("mjolnir", getApplicationContext()), 0.04f));
        models.add(new Model("star_lord_helmet", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("star_lord_helmet", getApplicationContext()), 0.15f));
        models.add(new Model("shazam", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("shazam", getApplicationContext()), 0.1f));
        models.add(new Model("spider_gwen", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("spider_gwen", getApplicationContext()), 0.03f));
        models.add(new Model("storm_breaker", false, "qrcodeuniverzal.png", Model.getIdOf3DModel("storm_breaker", getApplicationContext()), 0.02f));
    }

}
