package se.mg.exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseService {

   public static ExerciseService instance = null;
   private List<Exercise> exercises = new ArrayList<>();

   private ExerciseService() {
   }

   public static ExerciseService instance() {
      if (instance == null) {
         instance = new ExerciseService();
      }
      return instance;
   }

   public List<Exercise> getExercises() {
      return exercises;
   }

   public void init() {
   }

   public void addExercise(Exercise exercise) {
      exercises.add(exercise);
   }
}