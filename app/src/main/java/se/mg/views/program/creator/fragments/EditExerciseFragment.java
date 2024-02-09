package se.mg.views.program.creator.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.travijuu.numberpicker.library.NumberPicker;

import androidx.fragment.app.DialogFragment;
import se.mg.R;
import se.mg.client.ClientContext;
import se.mg.exercise.Exercise;
import se.mg.program.Program;
import se.mg.program.ProgramExercise;
import se.mg.program.RepsProgramExercise;
import se.mg.program.RepsRange;
import se.mg.program.RestProgramExercise;
import se.mg.views.utils.StateListener;

public class EditExerciseFragment extends DialogFragment {
   public static final String TAG = "EditExerciseFragment";
   private final Program program;
   private final StateListener stateListener;
   private int position;
   private final Exercise exercise;
   private RepsProgramExercise repsProgramExercise;
   private NumberPicker repsPicker;
   private NumberPicker setsPicker;
   private Button addButton;
   private ImageView closeButton;
   private MaterialCheckBox addRestCb;

   public EditExerciseFragment(Program program, StateListener stateListener, RepsProgramExercise exercise) {
      this(program, stateListener, null, -1, exercise);
   }

   public EditExerciseFragment(Program program, StateListener stateListener, int position, Exercise exercise) {
      this(program, stateListener, exercise, position, null);
   }

   public EditExerciseFragment(Program program, StateListener stateListener, Exercise exercise, int position, RepsProgramExercise repsProgramExercise) {
      super();
      this.program = program;
      this.stateListener = stateListener;
      this.exercise = exercise;
      this.position = position;
      this.repsProgramExercise = repsProgramExercise;
   }

   @Override
   public void onResume() {
      // Set the width of the dialog proportional to 90% of the screen width
      Window window = getDialog().getWindow();
      Point size = new Point();
      Display display = window.getWindowManager().getDefaultDisplay();
      display.getSize(size);
      window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
      window.setGravity(Gravity.CENTER);
      super.onResume();
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_edit_exercise_dialog, container, false);
      repsPicker = view.findViewById(R.id.exercise_reps_picker);
      setsPicker = view.findViewById(R.id.exercise_sets_picker);
      addRestCb = view.findViewById(R.id.add_rest_cb);
      if (repsProgramExercise != null) {
         repsPicker.setValue(repsProgramExercise.getReps().getMax());
         setsPicker.setVisibility(View.INVISIBLE);
         addRestCb.setVisibility(View.INVISIBLE);
      }
      addButton = view.findViewById(R.id.exercise_add_button);
      closeButton = view.findViewById(R.id.close_button);
      closeButton.setOnClickListener(l -> dismiss());
      addButton.setOnClickListener(l -> {
         int reps = repsPicker.getValue();
         if (repsProgramExercise != null) {
            repsProgramExercise.setReps(new RepsRange(reps, reps));
            stateListener.changeNotified(this);
            dismiss();
         } else {
            int sets = setsPicker.getValue();
            if (addRestCb.isChecked()) {
               handleRest(reps, sets);
            } else {
               handleReps(reps, sets);
            }
         }
      });
      return view;
   }

   private void handleReps(int reps, int sets) {
      for (int i = 0; i < sets; i++) {
         addExercise(new RepsProgramExercise(exercise, 1, new RepsRange(reps, reps)));
      }
      ClientContext.getInstance().save(getContext());
      stateListener.changeNotified(this);
      dismiss();
   }

   private void handleRest(int reps, int sets) {
      new EditRestFragment(program, obj -> {
         RestProgramExercise rest = (RestProgramExercise) obj;
         for (int i = 0; i < sets; i++) {
            if (i > 0) {
               addExercise(rest);
            }
            addExercise(new RepsProgramExercise(exercise, 1, new RepsRange(reps, reps)));
         }
         ClientContext.getInstance().save(getContext());
         stateListener.changeNotified(this);
         dismiss();
      }).show(getParentFragmentManager(), EditRestFragment.TAG);
   }

   private void addExercise(ProgramExercise exercise) {
      if (position == -1) {
         program.addExercise(exercise);
      } else {
         program.addExercise(exercise, position);
         position++;
      }
   }
}
