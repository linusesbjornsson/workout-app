package se.mg.exercise.runner;

public interface ProgramExerciseRunnerVisitor {
   void visit(RepsExerciseRunner repsExerciseRunner);
   void visit(RestExerciseRunner restExerciseRunner);
}
