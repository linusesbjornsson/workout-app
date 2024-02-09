package se.mg.views.program.creator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import se.mg.R;
import se.mg.exercise.Exercise;
import se.mg.program.Program;
import se.mg.util.AssetService;
import se.mg.views.program.creator.fragments.ExerciseViewFragment;
import se.mg.views.utils.ChipUtils;
import se.mg.views.utils.FragmentNavigationHelper;
import se.mg.views.utils.StateListener;

public class SelectExerciseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private Context context;
   private FragmentManager fragmentManager;
   private Program program;
   private StateListener stateListener;
   private int programPosition;
   private List<Exercise> exerciseList;
   private final int VIEW_TYPE_ITEM = 0;
   private final int VIEW_TYPE_LOADING = 1;

   public SelectExerciseRecyclerViewAdapter(Context context, FragmentManager fragmentManager, Program program, StateListener stateListener, int programPosition, List<Exercise> exerciseList) {
      this.context = context;
      this.fragmentManager = fragmentManager;
      this.program = program;
      this.stateListener = stateListener;
      this.programPosition = programPosition;
      this.exerciseList = exerciseList;
   }

   @NonNull
   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      if (viewType == VIEW_TYPE_ITEM) {
         return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.selection_exercise_card, parent, false));
      } else {
         return new LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false));
      }
   }

   @Override
   public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      if (holder instanceof ItemViewHolder) {
         ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
         Exercise exercise = exerciseList.get(position);
         itemViewHolder.itemView.setOnClickListener(v -> FragmentNavigationHelper.navigateTo(new ExerciseViewFragment(exercise, program, stateListener, programPosition), R.id.create_program_container, fragmentManager));
         itemViewHolder.exerciseName.setText(exercise.getName());
         AssetService.instance().loadImage(context, itemViewHolder.exerciseImage, exercise);
         itemViewHolder.chips.removeAllViews();
         ChipUtils.createOtherChips(context, exercise).stream().forEach(c -> itemViewHolder.chips.addView(c));
         ChipUtils.createPrimaryMusclesChips(context, exercise.getPrimaryMuscles()).stream().forEach(c -> itemViewHolder.chips.addView(c));
         ChipUtils.createSecondaryMusclesChips(context, exercise.getSecondaryMuscles()).stream().forEach(c -> itemViewHolder.chips.addView(c));
      }
   }

   @Override
   public int getItemCount() {
      return exerciseList == null ? 0 : exerciseList.size();
   }

   @Override
   public int getItemViewType(int position) {
      return exerciseList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
   }

   public void addExercise(Exercise exercise) {
      exerciseList.add(exercise);
   }

   public void setExercises(List<Exercise> exercises) {
      exerciseList = exercises;
   }

   public void removeLastExercise() {
      exerciseList.remove(exerciseList.size() - 1);
   }

   public class ItemViewHolder extends RecyclerView.ViewHolder {
      private final TextView exerciseName;
      private final ImageView exerciseImage;
      private final ChipGroup chips;

      public ItemViewHolder(@NonNull View itemView) {
         super(itemView);
         exerciseName = itemView.findViewById(R.id.card_exercise_name);
         exerciseImage = itemView.findViewById(R.id.exercise_img);
         chips = itemView.findViewById(R.id.chips_exercise_card);
      }
   }

   public class LoadingViewHolder extends RecyclerView.ViewHolder {
      private final ProgressBar progressBar;

      public LoadingViewHolder(@NonNull View itemView) {
         super(itemView);
         progressBar = itemView.findViewById(R.id.progressBar);
      }
   }
}