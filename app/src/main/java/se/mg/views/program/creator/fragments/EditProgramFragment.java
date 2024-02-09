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
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import se.mg.R;
import se.mg.program.Program;
import se.mg.views.program.creator.SelectExerciseFilterState;
import se.mg.views.utils.StateListener;

public class EditProgramFragment extends DialogFragment {
   public static final String TAG = "EditProgramFragment";
   private Program program;
   private final StateListener refresh;
   private EditText programNameView;
   private EditText programNoteView;
   private ImageView closeButton;
   private Button saveButton;
   private Button setCategoriesButton;
   private SelectExerciseFilterState filterState;

   public EditProgramFragment(Program program, StateListener refresh) {
      super();
      this.program = program;
      this.refresh = refresh;
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
      View view = inflater.inflate(R.layout.fragment_edit_program_dialog, container, false);
      filterState = new SelectExerciseFilterState();
      programNameView = view.findViewById(R.id.edit_program_name);
      programNoteView = view.findViewById(R.id.edit_program_note);
      closeButton = view.findViewById(R.id.close_button);
      closeButton.setOnClickListener(l -> dismiss());
      if (program == null) {
         program = new Program("", "", 1);
      }
      programNameView.setText(program.getName());
      programNoteView.setText(program.getNote());
      saveButton = view.findViewById(R.id.program_save_button);
      saveButton.setOnClickListener(l -> {
         program.setName(programNameView.getText().toString());
         program.setNote(programNoteView.getText().toString());
         program.setCategory(filterState.getCategory());
         program.setMechanic(filterState.getMechanic());
         program.setForce(filterState.getForce());
         program.setEquipment(filterState.getEquipment());
         program.setLevel(filterState.getLevel());
         program.setPrimaryMuscles(filterState.getPrimaryMuscles());
         if (refresh != null) {
            refresh.changeNotified(program);
         }
         dismiss();
      });
      setCategoriesButton = view.findViewById(R.id.program_categories_button);
      setCategoriesButton.setOnClickListener(l -> {
         new SelectExerciseFilterFragment(filterState)
                 .show(getParentFragmentManager(), SelectExerciseFilterFragment.TAG);
      });
      filterState.setCategory(program.getCategory());
      filterState.setMechanic(program.getMechanic());
      filterState.setForce(program.getForce());
      filterState.setEquipment(program.getEquipment());
      filterState.setLevel(program.getLevel());
      program.getPrimaryMuscles().stream().forEach(m -> filterState.addPrimaryMuscle(m));
      return view;
   }
}
