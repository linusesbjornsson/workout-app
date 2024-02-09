package se.mg.program.runner;

import androidx.fragment.app.FragmentManager;
import se.mg.R;
import se.mg.exercise.runner.ExerciseRunnerListener;
import se.mg.exercise.runner.ProgramExerciseRunner;
import se.mg.exercise.runner.ProgramExerciseRunnerBuilder;
import se.mg.exercise.runner.ProgramExerciseRunnerVisitor;
import se.mg.exercise.runner.RepsExerciseRunner;
import se.mg.exercise.runner.RestExerciseRunner;
import se.mg.program.Program;
import se.mg.program.ProgramExercise;
import se.mg.views.runner.fragments.RunnerRepsFragment;
import se.mg.views.runner.fragments.RunnerRestFragment;
import se.mg.views.utils.FragmentNavigationHelper;

public class ProgramRunner implements ProgramExerciseRunnerVisitor, ExerciseRunnerListener {
   private final ProgramRunnerState programRunnerState;
   private final ProgramRunnerListener programRunnerListener;
   private final FragmentManager fragmentManager;

   public ProgramRunner(ProgramRunnerListener programRunnerListener, Program program, FragmentManager fragmentManager) {
      programRunnerState = new ProgramRunnerState(program);
      this.programRunnerListener = programRunnerListener;
      this.fragmentManager = fragmentManager;
   }

   public void setCurrentStep(int step) {
      getState().setCurrentStep(step);
      programRunnerListener.stepChanged();
   }

   public ProgramRunnerState getState() {
      return programRunnerState;
   }

   public void initialize() {
      getRunner().accept(this);
   }

   private ProgramExerciseRunner getRunner() {
      ProgramExerciseRunnerBuilder builder = new ProgramExerciseRunnerBuilder(this);
      ProgramExercise exercise = programRunnerState.getCurrentExercise();
      exercise.accept(builder);
      return builder.build();
   }

   @Override
   public void visit(RepsExerciseRunner repsExerciseRunner) {
      FragmentNavigationHelper.replaceTo(new RunnerRepsFragment(this, repsExerciseRunner), R.id.program_runner_content, fragmentManager);
   }

   @Override
   public void visit(RestExerciseRunner restExerciseRunner) {
      FragmentNavigationHelper.replaceTo(new RunnerRestFragment(this, restExerciseRunner), R.id.program_runner_content, fragmentManager);
   }

   @Override
   public void runnerFinished() {
      if (getState().isFinished()) {
         programRunnerListener.programFinished();
      } else {
         setCurrentStep(getState().getCurrentStep() + 1);
         initialize();
      }
   }
}
