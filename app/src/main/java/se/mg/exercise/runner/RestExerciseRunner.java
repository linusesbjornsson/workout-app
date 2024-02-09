package se.mg.exercise.runner;

import se.mg.program.RestProgramExercise;

public class RestExerciseRunner extends TimedExerciseRunner {

   public RestExerciseRunner(ExerciseRunnerListener exerciseRunnerListener, RestProgramExercise programExercise) {
      super(exerciseRunnerListener, programExercise);
   }

   @Override
   public void accept(ProgramExerciseRunnerVisitor visitor) {
      visitor.visit(this);
   }
}
