package se.mg.exercise.runner;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import se.mg.program.TimedProgramExercise;

public abstract class TimedExerciseRunner extends ProgramExerciseRunner<TimedProgramExercise> implements Runnable {

   protected List<TimedRunnerListener> timedRunnerListeners = new ArrayList<>();
   protected Handler handler;
   protected int elapsedTime;

   public TimedExerciseRunner(ExerciseRunnerListener exerciseRunnerListener, TimedProgramExercise programExercise) {
      super(exerciseRunnerListener, programExercise);
   }


   public void setHandler(Handler handler) {
      this.handler = handler;
   }

   public void addTimedRunnerListener(TimedRunnerListener listener) {
      timedRunnerListeners.add(listener);
   }

   public int getTimeLeft() {
      return getProgramExercise().getSeconds() - elapsedTime;
   }

   public int getPercentageComplete() {
      double percentComplete = 100.0 * elapsedTime / getProgramExercise().getSeconds();
      return (int) percentComplete;
   }

   @Override
   public void run() {
      if (elapsedTime < getProgramExercise().getSeconds()) {
         elapsedTime++;
         handler.postDelayed(this, 1000);
         timedRunnerListeners.stream().forEach(t -> t.onTick());
      } else {
         timedRunnerListeners.stream().forEach(t -> t.onTimedRunnerFinished());
      }
   }

   public void stopTimer() {
      handler.removeCallbacks(this);
   }
}
