package se.mg.program;

public class RestProgramExercise extends TimedProgramExercise {
   public RestProgramExercise(long version, int seconds) {
      super(null, version, seconds);
   }

   @Override
   public void accept(ProgramVisitor visitor) {
      visitor.visit(this);
   }
}