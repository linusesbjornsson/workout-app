package se.mg.views.runner.fragments;

import androidx.fragment.app.Fragment;
import se.mg.exercise.runner.ExerciseRunnerListener;

public abstract class ExerciseRunnerFragment extends Fragment {
   protected final ExerciseRunnerListener exerciseRunnerListener;

   public ExerciseRunnerFragment(ExerciseRunnerListener exerciseRunnerListener) {
      super();
      this.exerciseRunnerListener = exerciseRunnerListener;
   }
}
