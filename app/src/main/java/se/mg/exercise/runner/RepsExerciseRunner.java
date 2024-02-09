package se.mg.exercise.runner;

import se.mg.program.RepsProgramExercise;

public class RepsExerciseRunner extends ProgramExerciseRunner<RepsProgramExercise> {
   public RepsExerciseRunner(ExerciseRunnerListener exerciseRunnerListener, RepsProgramExercise programExercise) {
      super(exerciseRunnerListener, programExercise);
   }

   @Override
   public void accept(ProgramExerciseRunnerVisitor visitor) {
      visitor.visit(this);
   }
}
