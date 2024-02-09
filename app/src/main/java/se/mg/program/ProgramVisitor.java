package se.mg.program;

public interface ProgramVisitor {
   void visit(RepsProgramExercise repsProgramExercise);

   void visit(RestProgramExercise rest);

   void visit(TimedProgramExercise timedProgramExercise);
}
