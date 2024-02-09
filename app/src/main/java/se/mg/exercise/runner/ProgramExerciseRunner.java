package se.mg.exercise.runner;

import se.mg.program.ProgramExercise;

public abstract class ProgramExerciseRunner<T extends ProgramExercise> {
   private final T programExercise;
   private final ExerciseRunnerListener exerciseRunnerListener;

   public ProgramExerciseRunner(ExerciseRunnerListener exerciseRunnerListener, T programExercise) {
      this.exerciseRunnerListener = exerciseRunnerListener;
      this.programExercise = programExercise;
   }

   public T getProgramExercise() {
      return programExercise;
   }

   public void runnerFinished() {
      exerciseRunnerListener.runnerFinished();
   }
   public abstract void accept(ProgramExerciseRunnerVisitor visitor);
}
