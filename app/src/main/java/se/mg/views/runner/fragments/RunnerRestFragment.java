package se.mg.views.runner.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import se.mg.R;
import se.mg.exercise.runner.ExerciseRunnerListener;
import se.mg.exercise.runner.RestExerciseRunner;
import se.mg.exercise.runner.TimedRunnerListener;
import se.mg.util.Formatter;

public class RunnerRestFragment extends BaseTimedFragment<RestExerciseRunner> implements TimedRunnerListener {
   private TextView restTimeView;
   private CircularProgressIndicator progressIndicator;
   public static final String REST_FRAGMENT_ID = "REST_FRAGMENT_ID";
   public static final String NOTIFICATION_TITLE = "Rest done!";
   public static final String NOTIFICATION_TEXT = "Navigate back to the application to continue your program";

   public RunnerRestFragment(ExerciseRunnerListener exerciseRunnerListener, RestExerciseRunner restExerciseRunner) {
      super(exerciseRunnerListener, restExerciseRunner);
   }

   @Override
   protected NotificationMetadata getNotificationMetadata() {
      NotificationMetadata metadata = new NotificationMetadata();
      metadata.title = NOTIFICATION_TITLE;
      metadata.fragmentId = REST_FRAGMENT_ID;
      metadata.text = NOTIFICATION_TEXT;
      metadata.icon = R.drawable.rest;
      return metadata;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_runner_rest, container, false);
      createNotificationChannel();
      setupView(view);
      setupTimer();
      restTimeView = view.findViewById(R.id.rest_time);
      progressIndicator = view.findViewById(R.id.rest_progress);
      updateView();
      return view;
   }

   @Override
   protected void updateView() {
      restTimeView.setText(Formatter.formatTime(exerciseRunner.getTimeLeft()));
      ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressIndicator, "progress", progressIndicator.getProgress(), exerciseRunner.getPercentageComplete());
      progressAnimator.setDuration(1000);
      progressAnimator.setInterpolator(new DecelerateInterpolator());
      progressAnimator.start();
   }
}
