package se.mg.exercise.runner;

import se.mg.program.ProgramVisitor;
import se.mg.program.RepsProgramExercise;
import se.mg.program.RestProgramExercise;
import se.mg.program.TimedProgramExercise;

public class ProgramExerciseRunnerBuilder implements ProgramVisitor {

   private ProgramExerciseRunner runner = null;
   private final ExerciseRunnerListener exerciseRunnerListener;

   public ProgramExerciseRunnerBuilder(ExerciseRunnerListener exerciseRunnerListener) {
      this.exerciseRunnerListener = exerciseRunnerListener;
   }

   @Override
   public void visit(RepsProgramExercise repsProgramExercise) {
      runner = new RepsExerciseRunner(exerciseRunnerListener, repsProgramExercise);
   }

   @Override
   public void visit(RestProgramExercise restProgramExercise) {
      runner = new RestExerciseRunner(exerciseRunnerListener, restProgramExercise);
   }

   @Override
   public void visit(TimedProgramExercise timedProgramExercise) {

   }

   public ProgramExerciseRunner build() {
      return runner;
   }
}
