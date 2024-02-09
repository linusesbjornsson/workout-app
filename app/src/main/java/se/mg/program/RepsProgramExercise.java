package se.mg.program;

import se.mg.exercise.Exercise;

public class RepsProgramExercise extends ProgramExercise {
   private static final long serialVersionUID = 1L;

   private RepsRange reps;

   public RepsProgramExercise(Exercise exercise, long version, RepsRange reps) {
      super(exercise, version);
      this.reps = reps;
   }

   public RepsRange getReps() {
      return reps;
   }

   public void setReps(RepsRange reps) {
      this.reps = reps;
   }

   @Override
   public void accept(ProgramVisitor visitor) {
      visitor.visit(this);
   }
}
