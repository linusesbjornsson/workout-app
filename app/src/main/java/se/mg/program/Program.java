package se.mg.program;

import java.util.ArrayList;
import java.util.List;

import se.mg.entity.BaseEntity;

public class Program extends BaseEntity {
   private static final long serialVersionUID = 2L;
   private String name;
   private String note;
   private List<ProgramExercise> exercises;

   public Program(String name, String note, long version) {
      super(version);
      this.name = name;
      this.note = note;
      this.exercises = new ArrayList<>();
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public List<ProgramExercise> getExercises() {
      return exercises;
   }

   public void removeExercise(int position) {
      exercises.remove(position);
   }

   public void addExercise(ProgramExercise exercise) {
      exercises.add(exercise);
   }

   public void addExercise(ProgramExercise exercise, int position) {
      exercises.add(position, exercise);
   }

   public void setExercises(List<ProgramExercise> exercises) {
      this.exercises = exercises;
   }
}
