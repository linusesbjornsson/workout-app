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

import com.travijuu.numberpicker.library.NumberPicker;

import androidx.fragment.app.DialogFragment;
import se.mg.R;
import se.mg.program.Program;
import se.mg.program.RestProgramExercise;
import se.mg.views.utils.StateListener;

public class EditRestFragment extends DialogFragment {
   public static final String TAG = "EditRestFragment";
   private final Program program;
   private final StateListener stateListener;
   private RestProgramExercise restProgramExercise;
   private NumberPicker minutePicker;
   private NumberPicker secondPicker;
   private Button addButton;
   private ImageView closeButton;

   public EditRestFragment(Program program, StateListener stateListener) {
      this(program, stateListener, null);
   }

   public EditRestFragment(Program program, StateListener stateListener, RestProgramExercise restProgramExercise) {
      super();
      this.program = program;
      this.stateListener = stateListener;
      this.restProgramExercise = restProgramExercise;
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
      View view = inflater.inflate(R.layout.fragment_edit_rest, container, false);
      minutePicker = view.findViewById(R.id.rest_minute_picker);
      secondPicker = view.findViewById(R.id.rest_second_picker);
      addButton = view.findViewById(R.id.exercise_add_button);
      closeButton = view.findViewById(R.id.close_button);
      if (restProgramExercise != null) {
         int minutes = restProgramExercise.getSeconds() / 60;
         int seconds = restProgramExercise.getSeconds() % 60;
         minutePicker.setValue(minutes);
         secondPicker.setValue(seconds);
      }
      closeButton.setOnClickListener(l -> dismiss());
      addButton.setOnClickListener(l -> {
         secondPicker.clearFocus();
         minutePicker.clearFocus();
         int minutes = minutePicker.getValue();
         int seconds = (minutes * 60) + secondPicker.getValue();
         if (restProgramExercise != null) {
            restProgramExercise.setSeconds(seconds);
            stateListener.changeNotified(null);
         } else {
            stateListener.changeNotified(new RestProgramExercise(1, seconds));
         }
         dismiss();
      });
      return view;
   }
}
