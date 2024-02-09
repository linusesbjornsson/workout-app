package se.mg.program;

import se.mg.exercise.Exercise;

public class TimedProgramExercise extends ProgramExercise {
   private static final long serialVersionUID = 1L;

   private int seconds;

   public TimedProgramExercise(Exercise exercise, long version, int seconds) {
      super(exercise, version);
      this.seconds = seconds;
   }

   public int getSeconds() {
      return seconds;
   }

   public void setSeconds(int seconds) {
      this.seconds = seconds;
   }

   @Override
   public void accept(ProgramVisitor visitor) {
      visitor.visit(this);
   }
}
