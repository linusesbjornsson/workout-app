package se.mg.util;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import se.mg.exercise.Category;
import se.mg.exercise.Equipment;
import se.mg.exercise.Exercise;
import se.mg.exercise.ExerciseBuilder;
import se.mg.exercise.Force;
import se.mg.exercise.Level;
import se.mg.exercise.Mechanic;
import se.mg.exercise.Muscle;

public class ExerciseDbReader {
   private static final String EXERCISES_JSON = "exercises.json";

   public static List<ExerciseDbEntry> readExerciseDb(AssetManager assetManager) {
      try {
         InputStream is = assetManager.open(EXERCISES_JSON);
         JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
         return readExercises(reader);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private static List<ExerciseDbEntry> readExercises(JsonReader reader) throws IOException {
      List<ExerciseDbEntry> exerciseDbEntries = new ArrayList<>();

      reader.beginArray();
      while (reader.hasNext()) {
         exerciseDbEntries.add(readExercise(reader));
      }
      reader.endArray();
      return exerciseDbEntries;
   }

   private static ExerciseDbEntry readExercise(JsonReader reader) throws IOException {
      ExerciseBuilder exerciseBuilder = new ExerciseBuilder(1);
      List<String> images = new ArrayList<>();
      reader.beginObject();
      while (reader.hasNext()) {
         String name = reader.nextName();
         if (name.equals("name")) {
            exerciseBuilder.name(reader.nextString());
         } else if (name.equals("force") && reader.peek() != JsonToken.NULL) {
            exerciseBuilder.force(Force.valueOf(reader.nextString().toUpperCase().replace(" ", "_")));
         } else if (name.equals("level") && reader.peek() != JsonToken.NULL) {
            exerciseBuilder.level(Level.valueOf(reader.nextString().toUpperCase().replace(" ", "_")));
         } else if (name.equals("mechanic") && reader.peek() != JsonToken.NULL) {
            exerciseBuilder.mechanic(Mechanic.valueOf(reader.nextString().toUpperCase().replace(" ", "_")));
         } else if (name.equals("equipment") && reader.peek() != JsonToken.NULL) {
            exerciseBuilder.equipment(Equipment.valueOf(reader.nextString().toUpperCase().replace(" ", "_")));
         } else if (name.equals("primaryMuscles")) {
            reader.beginArray();
            while (reader.hasNext()) {
               exerciseBuilder.addPrimaryMuscle(Muscle.valueOf(reader.nextString().toUpperCase().replace(" ", "_")));
            }
            reader.endArray();
         } else if (name.equals("secondaryMuscles")) {
            reader.beginArray();
            while (reader.hasNext()) {
               exerciseBuilder.addSecondaryMuscle(Muscle.valueOf(reader.nextString().toUpperCase().replace(" ", "_")));
            }
            reader.endArray();
         } else if (name.equals("instructions")) {
            StringBuilder note = new StringBuilder();
            reader.beginArray();
            while (reader.hasNext()) {
               if (note.length() > 0) {
                  note.append("\n");
               }
               note.append(reader.nextString());
            }
            reader.endArray();
            if (note.length() > 0) {
               exerciseBuilder.note(note.toString());
            }
         } else if (name.equals("category")) {
            exerciseBuilder.category(Category.valueOf(reader.nextString().toUpperCase().replace(" ", "_")));
         } else if (name.equals("id")) {
            exerciseBuilder.id(reader.nextString());
         } else if (name.equals("images")) {
            reader.beginArray();
            while (reader.hasNext()) {
               images.add(reader.nextString());
            }
            reader.endArray();
         } else {
            reader.skipValue();
         }
      }
      reader.endObject();
      return new ExerciseDbEntry(exerciseBuilder.build(), images);
   }

   public static class ExerciseDbEntry {
      private Exercise exercise;
      private List<String> images;

      public ExerciseDbEntry(Exercise exercise, List<String> images) {
         this.exercise = exercise;
         this.images = images;
      }

      public Exercise getExercise() {
         return exercise;
      }

      public List<String> getImages() {
         return images;
      }
   }
}
