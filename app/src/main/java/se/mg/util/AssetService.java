package se.mg.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import se.mg.exercise.Exercise;
import se.mg.exercise.ExerciseService;

public class AssetService {
   private static final String EXERCISES_FOLDER = "exercises";
   private static Map<String, List<String>> exercises = new HashMap<>();
   private static AssetService instance = null;
   private static final String FILE_PATH = "file:///android_asset/";

   private AssetService() {

   }

   public static AssetService instance() {
      if (instance == null) {
         instance = new AssetService();
      }
      return instance;
   }

   public void init(AssetManager assetManager) {
      List<ExerciseDbReader.ExerciseDbEntry> result = ExerciseDbReader.readExerciseDb(assetManager);
      for (ExerciseDbReader.ExerciseDbEntry entry : result) {
         Exercise exercise = entry.getExercise();
         List<String> imagePaths = new ArrayList<>();
         entry.getImages().stream()
                 .forEach(i -> imagePaths.add(getImagePath(i)));
         exercises.put(exercise.getId(), imagePaths);
         ExerciseService.instance().addExercise(exercise);
      }
   }

   private String getImagePath(String image) {
      return FILE_PATH + EXERCISES_FOLDER + File.separator + image;
   }

   public void loadImage(Context context, ImageView imageView, Exercise exercise) {
      loadImage(context, imageView, exercise, 0);
   }

   public void loadImage(Context context, ImageView imageView, Exercise exercise, int index) {
      Glide.with(context)
              .load(Uri.parse(exercises.get(exercise.getId()).get(index)))
              .into(imageView);
   }

   public void loadImageIntoImageSwitcher(Context context, ImageSwitcher imageSwitcher, Exercise exercise, int index) {
      Glide.with(context)
              .asDrawable()
              .load(Uri.parse(exercises.get(exercise.getId()).get(index)))
              .into(new CustomTarget<Drawable>() {
                 @Override
                 public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    ImageView imageView = (ImageView) imageSwitcher.getNextView();
                    imageView.setImageDrawable(resource);
                    imageSwitcher.showNext();
                 }

                 @Override
                 public void onLoadCleared(@Nullable Drawable placeholder) {

                 }
              });
   }
}
