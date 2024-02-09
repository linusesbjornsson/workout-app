package se.mg.program;

import se.mg.entity.Entity;
import se.mg.exercise.Exercise;

public abstract class ProgramExercise extends Entity {
   private static final long serialVersionUID = 1L;
   private Exercise exercise;
   private String note;
   private float weight;

   public ProgramExercise(Exercise exercise, long version) {
      super(version);
      this.exercise = exercise;
   }

   public Exercise getExercise() {
      return exercise;
   }

   public void setExercise(Exercise exercise) {
      this.exercise = exercise;
   }

   public float getWeight() {
      return weight;
   }

   public void setWeight(float weight) {
      this.weight = weight;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public abstract void accept(ProgramVisitor visitor);
}
