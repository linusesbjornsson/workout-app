package se.mg.views.program.creator.fragments;

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
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;

import androidx.core.view.MenuCompat;
import androidx.fragment.app.Fragment;
import se.mg.R;
import se.mg.exercise.Exercise;
import se.mg.program.Program;
import se.mg.util.AssetService;
import se.mg.views.utils.ChipUtils;
import se.mg.views.utils.FragmentNavigationHelper;
import se.mg.views.utils.StateListener;

public class ExerciseViewFragment extends Fragment {

   private final Exercise exercise;
   private final Program program;
   private final StateListener stateListener;
   private final int position;
   private ImageSwitcher exerciseImageSwitcher;
   private ImageView backButtonView;
   private ImageView addButtonView;
   private TextView exerciseNameView;
   private TextView exerciseNoteView;
   private ChipGroup primaryMusclesChipGroup;
   private ChipGroup secondaryMusclesChipGroup;
   private ChipGroup otherChipGroup;
   private Handler handler;
   private int index = 0;

   public ExerciseViewFragment(Exercise exercise, Program program, StateListener stateListener, int position) {
      super();
      this.exercise = exercise;
      this.program = program;
      this.stateListener = stateListener;
      this.position = position;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_exercise_view, container, false);
      exerciseNameView = view.findViewById(R.id.exercise_name);
      exerciseNoteView = view.findViewById(R.id.exercise_note);
      backButtonView = view.findViewById(R.id.exercise_back_button);
      backButtonView.setOnClickListener(l -> FragmentNavigationHelper.remove(this, getParentFragmentManager()));
      addButtonView = view.findViewById(R.id.create_exercise_add_menu);
      addButtonView.setOnClickListener(
              l -> {
                 PopupMenu popupMenu = new PopupMenu(view.getContext(), addButtonView);
                 popupMenu.getMenuInflater().inflate(R.menu.create_exercise_menu, popupMenu.getMenu());
                 MenuCompat.setGroupDividerEnabled(popupMenu.getMenu(), true);
                 popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.menu_add_reps_exercise) {
                       new EditExerciseFragment(program, stateListener, position, exercise)
                               .show(getParentFragmentManager(), EditExerciseFragment.TAG);
                    } else if (menuItem.getItemId() == R.id.menu_add_timed_exercise) {

                    }
                    return true;
                 });
                 popupMenu.show();
              });
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
      primaryMusclesChipGroup = view.findViewById(R.id.chips_primary_muscles);
      secondaryMusclesChipGroup = view.findViewById(R.id.chips_secondary_muscles);
      otherChipGroup = view.findViewById(R.id.chips_other);
      setupExercise();
      return view;
   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
      handler.removeCallbacksAndMessages(null);
   }

   private void setupExercise() {
      exerciseNameView.setText(exercise.getName());
      exerciseNoteView.setText(exercise.getNote());
      ChipUtils.createPrimaryMusclesChips(getContext(), exercise.getPrimaryMuscles()).stream().forEach(c -> primaryMusclesChipGroup.addView(c));
      ChipUtils.createSecondaryMusclesChips(getContext(), exercise.getSecondaryMuscles()).stream().forEach(c -> secondaryMusclesChipGroup.addView(c));
      ChipUtils.createOtherChips(getContext(), exercise).stream().forEach(c -> otherChipGroup.addView(c));
      handler = new Handler(Looper.getMainLooper());
      handler.post(new Runnable() {
         @Override
         public void run() {
            AssetService.instance().loadImageIntoImageSwitcher(getContext(), exerciseImageSwitcher, exercise, index);
            index = (index == 0 ? 1 : 0);
            handler.postDelayed(this, 3000);
         }
      });
   }
}
