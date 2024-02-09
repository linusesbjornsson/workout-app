package se.mg.views.runner;

import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import se.mg.R;
import se.mg.program.Program;
import se.mg.program.ProgramVisitor;
import se.mg.program.RepsProgramExercise;
import se.mg.program.RestProgramExercise;
import se.mg.program.TimedProgramExercise;
import se.mg.program.runner.ProgramRunner;
import se.mg.program.runner.ProgramRunnerListener;

public class ProgramRunnerView extends AppCompatActivity implements ProgramVisitor, ProgramRunnerListener {

   private ProgramRunner programRunner;
   private HorizontalScrollView stepsScroll;
   private LinearLayout runnerSteps;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_program_runner);
      stepsScroll = findViewById(R.id.program_runner_steps_scroll);
      runnerSteps = findViewById(R.id.program_runner_steps);
      Bundle bundle = getIntent().getExtras();
      Program program = (Program) bundle.getSerializable("program");
      programRunner = new ProgramRunner(this, program, getSupportFragmentManager());
      setupViews();
   }

   private void setupViews() {
      programRunner.getState().getProgram().getExercises().stream()
              .forEach(p -> p.accept(this));
      programRunner.initialize();
   }

   private void initCard(CardView cardView, int step) {
      if (step == 0) {
         cardView.setCardBackgroundColor(getResources().getColor(R.color.exercise_selected_step, null));
      }
      cardView.setOnClickListener(l -> {
         programRunner.setCurrentStep(step);
         programRunner.initialize();
      });
      runnerSteps.addView(cardView);
   }

   @Override
   public void visit(RepsProgramExercise repsProgramExercise) {
      CardView cardView = (CardView) getLayoutInflater().inflate(R.layout.runner_exercise_card, runnerSteps, false);
      initCard(cardView, programRunner.getState().getStepIndex(repsProgramExercise));
   }

   @Override
   public void visit(RestProgramExercise restProgramExercise) {
      CardView cardView = (CardView) getLayoutInflater().inflate(R.layout.runner_rest_card, runnerSteps, false);
      initCard(cardView, programRunner.getState().getStepIndex(restProgramExercise));
   }

   @Override
   public void visit(TimedProgramExercise timedProgramExercise) {
      CardView cardView = (CardView) getLayoutInflater().inflate(R.layout.runner_timed_exercise_card, runnerSteps, false);
      initCard(cardView, programRunner.getState().getStepIndex(timedProgramExercise));
   }

   @Override
   public void stepChanged() {
      CardView cardView = (CardView) runnerSteps.getChildAt(programRunner.getState().getCurrentStep());
      stepsScroll.smoothScrollTo(cardView.getLeft(), cardView.getTop());
      cardView.setCardBackgroundColor(getResources().getColor(R.color.exercise_selected_step, null));
      for (int i = 0; i < runnerSteps.getChildCount(); i++) {
         CardView view = (CardView) runnerSteps.getChildAt(i);
         if (i != programRunner.getState().getCurrentStep()) {
            view.setCardBackgroundColor(getResources().getColor(android.R.color.white, null));
         }
      }
   }

   @Override
   public void programFinished() {
      finish();
   }
}