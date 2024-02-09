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

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Arrays;

import androidx.fragment.app.DialogFragment;
import se.mg.R;
import se.mg.exercise.Category;
import se.mg.exercise.Equipment;
import se.mg.exercise.Force;
import se.mg.exercise.Level;
import se.mg.exercise.Mechanic;
import se.mg.exercise.Muscle;
import se.mg.views.program.creator.SelectExerciseFilterState;
import se.mg.views.utils.ChipUtils;

public class SelectExerciseFilterFragment extends DialogFragment {
   public static final String TAG = "SelectExerciseFilterFragment";
   private final SelectExerciseFilterState filterState;
   private ChipGroup levelFilter;
   private ChipGroup categoryFilter;
   private ChipGroup mechanicFilter;
   private ChipGroup forceFilter;
   private ChipGroup equipmentFilter;
   private ChipGroup primaryMusclesFilter;
   private ChipGroup secondaryMusclesFilter;

   public SelectExerciseFilterFragment(SelectExerciseFilterState filterState) {
      super();
      this.filterState = filterState;
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
      View view = inflater.inflate(R.layout.fragment_exercise_filter, container, false);
      levelFilter = view.findViewById(R.id.selection_level_filter);
      categoryFilter = view.findViewById(R.id.selection_category_filter);
      mechanicFilter = view.findViewById(R.id.selection_mechanic_filter);
      forceFilter = view.findViewById(R.id.selection_force_filter);
      equipmentFilter = view.findViewById(R.id.selection_equipment_filter);
      primaryMusclesFilter = view.findViewById(R.id.selection_primary_muscles_filter);
      secondaryMusclesFilter = view.findViewById(R.id.selection_secondary_muscles_filter);
      setupFilter();
      return view;
   }

   private void setupFilter() {
      // Levels
      Arrays.stream(Level.values()).forEach(e -> {
         Chip chip = ChipUtils.createLevelChip(getContext(), e);
         chip.setClickable(true);
         chip.setCheckable(true);
         if (filterState.getLevel() == e) {
            chip.setChecked(true);
         }
         levelFilter.addView(chip);
         chip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
               filterState.setLevel(e);
            } else {
               filterState.setLevel(null);
            }
         });
      });
      // Category
      Arrays.stream(Category.values()).forEach(e -> {
         Chip chip = ChipUtils.createCategoryChip(getContext(), e);
         chip.setClickable(true);
         chip.setCheckable(true);
         if (filterState.getCategory() == e) {
            chip.setChecked(true);
         }
         categoryFilter.addView(chip);
         chip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
               filterState.setCategory(e);
            } else {
               filterState.setCategory(null);
            }
         });
      });
      // Mechanic
      Arrays.stream(Mechanic.values()).forEach(e -> {
         Chip chip = ChipUtils.createMechanicChip(getContext(), e);
         chip.setClickable(true);
         chip.setCheckable(true);
         if (filterState.getMechanic() == e) {
            chip.setChecked(true);
         }
         mechanicFilter.addView(chip);
         chip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
               filterState.setMechanic(e);
            } else {
               filterState.setMechanic(null);
            }
         });
      });
      // Force
      Arrays.stream(Force.values()).forEach(e -> {
         Chip chip = ChipUtils.createForceChip(getContext(), e);
         chip.setClickable(true);
         chip.setCheckable(true);
         if (filterState.getForce() == e) {
            chip.setChecked(true);
         }
         forceFilter.addView(chip);
         chip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
               filterState.setForce(e);
            } else {
               filterState.setForce(null);
            }
         });
      });
      // Equipment
      Arrays.stream(Equipment.values()).forEach(e -> {
         Chip chip = ChipUtils.createEquipmentChip(getContext(), e);
         chip.setClickable(true);
         chip.setCheckable(true);
         if (filterState.getEquipment() == e) {
            chip.setChecked(true);
         }
         equipmentFilter.addView(chip);
         chip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
               filterState.setEquipment(e);
            } else {
               filterState.setEquipment(null);
            }
         });
      });
      // Muscles
      Arrays.stream(Muscle.values()).forEach(e -> {
         Chip primaryMuscleChip = ChipUtils.createPrimaryMuscleChip(getContext(), e);
         Chip secondaryMuscleChip = ChipUtils.createSecondaryMuscleChip(getContext(), e);
         primaryMuscleChip.setClickable(true);
         primaryMuscleChip.setCheckable(true);
         if (filterState.getPrimaryMuscles().contains(e)) {
            primaryMuscleChip.setChecked(true);
         }
         primaryMusclesFilter.addView(primaryMuscleChip);
         primaryMuscleChip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
               filterState.addPrimaryMuscle(e);
            } else {
               filterState.removePrimaryMuscle(e);
            }
         });
         secondaryMuscleChip.setClickable(true);
         secondaryMuscleChip.setCheckable(true);
         if (filterState.getSecondaryMuscles().contains(e)) {
            secondaryMuscleChip.setChecked(true);
         }
         secondaryMusclesFilter.addView(secondaryMuscleChip);
         secondaryMuscleChip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
               filterState.addSecondaryMuscle(e);
            } else {
               filterState.removeSecondaryMuscle(e);
            }
         });
      });
   }
}
