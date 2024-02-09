package se.mg.program.runner;

import se.mg.program.Program;
import se.mg.program.ProgramExercise;

public class ProgramRunnerState {
   private final Program program;
   private int currentStep;

   public ProgramRunnerState(Program program) {
      this.program = program;
      this.currentStep = 0;
   }

   public Program getProgram() {
      return program;
   }

   public void setCurrentStep(int step) {
      currentStep = step;
   }

   public int getCurrentStep() {
      return currentStep;
   }

   public boolean isFinished() {
      return currentStep >= program.getExercises().size() - 1;
   }

   public ProgramExercise getCurrentExercise() {
      return program.getExercises().get(currentStep);
   }

   public int getStepIndex(ProgramExercise programExercise) {
      for (int i = 0; i < program.getExercises().size(); i++) {
         ProgramExercise exercise = program.getExercises().get(i);
         if (exercise == programExercise) {
            return i;
         }
      }
      return -1;
   }
}
