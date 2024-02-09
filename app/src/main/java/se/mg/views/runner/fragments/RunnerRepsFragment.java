package se.mg.views.runner.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import se.mg.R;
import se.mg.exercise.runner.ExerciseRunnerListener;
import se.mg.exercise.runner.RepsExerciseRunner;
import se.mg.util.AssetService;
import se.mg.util.Formatter;

public class RunnerRepsFragment extends ExerciseRunnerFragment {

   private final RepsExerciseRunner repsExerciseRunner;
   private ImageSwitcher exerciseImageSwitcher;
   private TextView exerciseNameView;
   private TextView exerciseRepsView;
   private TextView exerciseNoteView;
   private Handler handler;
   private int index = 0;

   public RunnerRepsFragment(ExerciseRunnerListener exerciseRunnerListener, RepsExerciseRunner repsExerciseRunner) {
      super(exerciseRunnerListener);
      this.repsExerciseRunner = repsExerciseRunner;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_runner_reps, container, false);
      view.setOnClickListener(l -> repsExerciseRunner.runnerFinished());
      exerciseNameView = view.findViewById(R.id.exercise_name);
      exerciseNoteView = view.findViewById(R.id.exercise_note);
      exerciseRepsView = view.findViewById(R.id.exercise_reps_range);
      exerciseImageSwitcher = view.findViewById(R.id.exercise_image_switcher);
      exerciseImageSwitcher.setFactory(() -> {
         ImageView imageView = new ImageView(getContext());
         imageView.setScaleType(ImageView.ScaleType.FIT_XY);
         imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
         return imageView;
      });
      Animation in  = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
      Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
      exerciseImageSwitcher.setInAnimation(in);
      exerciseImageSwitcher.setOutAnimation(out);
      setupExercise();
      return view;
   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
      handler.removeCallbacksAndMessages(null);
   }

   private void setupExercise() {
      exerciseNameView.setText(repsExerciseRunner.getProgramExercise().getExercise().getName());
      exerciseNoteView.setText(repsExerciseRunner.getProgramExercise().getExercise().getNote());
      exerciseRepsView.setText(Formatter.formatReps(repsExerciseRunner.getProgramExercise().getReps()));
      handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
         @Override
         public void run() {
            AssetService.instance().loadImageIntoImageSwitcher(getContext(), exerciseImageSwitcher, repsExerciseRunner.getProgramExercise().getExercise(), index);
            index = (index == 0 ? 1 : 0);
            handler.postDelayed(this, 3000);
         }
      });
   }
}
