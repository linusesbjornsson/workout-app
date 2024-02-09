package se.mg.views.runner.fragments;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import se.mg.exercise.runner.ExerciseRunnerListener;
import se.mg.exercise.runner.TimedExerciseRunner;
import se.mg.exercise.runner.TimedRunnerListener;

public abstract class BaseTimedFragment<T extends TimedExerciseRunner> extends ExerciseRunnerFragment implements TimedRunnerListener {

   protected final T exerciseRunner;
   protected Handler handler = null;
   protected boolean isFinished = false;

   private static final String CHANNEL_ID = "Timed_Fragment_Channel";
   private static final String CHANNEL_NAME = "Timer done";
   private static final int NOTIFICATION_ID = 1;

   public BaseTimedFragment(ExerciseRunnerListener exerciseRunnerListener, T exerciseRunner) {
      super(exerciseRunnerListener);
      this.exerciseRunner = exerciseRunner;
   }

   @Override
   public void onDestroy() {
      if (handler != null && exerciseRunner != null) {
         exerciseRunner.stopTimer();
         handler.removeCallbacks(exerciseRunner);
      }
      NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
      notificationManager.cancel(NOTIFICATION_ID);
      super.onDestroy();
   }

   protected void setupTimer() {
      handler = new Handler();
      exerciseRunner.setHandler(handler);
      exerciseRunner.addTimedRunnerListener(this);
   }

   protected void setupView(View view) {
      view.setOnClickListener(l -> {
         if (isFinished) {
            exerciseRunner.runnerFinished();
         } else if (!handler.hasCallbacks(exerciseRunner)) {
            handler.post(exerciseRunner);
         }
      });
   }

   @Override
   public void onTick() {
      updateView();
   }

   @Override
   public void onTimedRunnerFinished() {
      isFinished = true;
      showNotification();
   }

   protected void showNotification() {
      long[] vibratePattern = {0, 500, 200, 500};
      NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
              .setSmallIcon(getNotificationMetadata().icon)
              .setContentTitle(getNotificationMetadata().title)
              .setContentText(getNotificationMetadata().text)
              .setVibrate(vibratePattern)
              .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
              .setPriority(NotificationCompat.PRIORITY_HIGH)
              .setAutoCancel(true);

      NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
      if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
         return;
      }
      notificationManager.notify(NOTIFICATION_ID, builder.build());
   }

   protected void createNotificationChannel() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         long[] vibratePattern = {0, 500, 200, 500};
         int importance = NotificationManager.IMPORTANCE_DEFAULT;
         NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
         notificationChannel.enableVibration(true);
         notificationChannel.setVibrationPattern(vibratePattern);
         NotificationManager notificationManager = requireActivity().getSystemService(NotificationManager.class);
         notificationManager.createNotificationChannel(notificationChannel);
      }
   }

   protected abstract NotificationMetadata getNotificationMetadata();

   protected abstract void updateView();

   protected static class NotificationMetadata {
      protected String title;
      protected String text;
      protected int icon;
      protected String fragmentId;
   }
}
