package se.mg.views.runner.fragments;

import se.mg.exercise.runner.ExerciseRunnerListener;
import se.mg.exercise.runner.TimedRunnerListener;

public class RunnerTimedFragment extends ExerciseRunnerFragment implements TimedRunnerListener {

   public RunnerTimedFragment(ExerciseRunnerListener exerciseRunnerListener) {
      super(exerciseRunnerListener);
   }

   @Override
   public void onTick() {

   }

   @Override
   public void onTimedRunnerFinished() {

   }
}
