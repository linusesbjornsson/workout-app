package se.mg.views.program.creator.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;

import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import se.mg.R;
import se.mg.client.ClientContext;
import se.mg.exercise.Exercise;
import se.mg.program.Program;
import se.mg.program.ProgramExercise;
import se.mg.program.ProgramVisitor;
import se.mg.program.RepsProgramExercise;
import se.mg.program.RestProgramExercise;
import se.mg.program.TimedProgramExercise;
import se.mg.util.AssetService;
import se.mg.util.Formatter;
import se.mg.views.program.creator.ProgramCardRowTouchHelperContract;
import se.mg.views.program.creator.fragments.EditExerciseFragment;
import se.mg.views.program.creator.fragments.EditRestFragment;
import se.mg.views.program.creator.fragments.SelectExerciseFragment;
import se.mg.views.utils.ChipUtils;
import se.mg.views.utils.FragmentNavigationHelper;
import se.mg.views.utils.StateListener;

public class ProgramCardRecyclerViewAdapter extends RecyclerView.Adapter<ProgramCardRecyclerViewAdapter.CardViewHolder> implements ProgramCardRowTouchHelperContract {
   public static final int EXERCISE = 1;
   public static final int REST = 2;
   private final StateListener stateListener;
   private final FragmentManager fragmentManager;
   private Context context;
   private Program program;

   public ProgramCardRecyclerViewAdapter(Context context, StateListener stateListener, FragmentManager fragmentManager, Program program) {
      this.context = context;
      this.stateListener = stateListener;
      this.fragmentManager = fragmentManager;
      this.program = program;
   }

   @NonNull
   @Override
   public ProgramCardRecyclerViewAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      ProgramCardRecyclerViewAdapter.CardViewHolder viewHolder;
      switch (viewType) {
         default:
         case EXERCISE:
            View exerciseView = inflater.inflate(R.layout.program_exercise_card, parent, false);
            viewHolder = new ExerciseViewHolder(exerciseView);
            break;
         case REST:
            View restView = inflater.inflate(R.layout.rest_card, parent, false);
            viewHolder = new RestViewHolder(restView);
            break;
      }
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(@NonNull ProgramCardRecyclerViewAdapter.CardViewHolder holder, int position) {
      ProgramExercise exercise = program.getExercises().get(position);
      exercise.accept(new ProgramVisitor() {
         @Override
         public void visit(RepsProgramExercise repsProgramExercise) {
            ExerciseViewHolder viewHolder = (ExerciseViewHolder) holder;
            Exercise exercise = repsProgramExercise.getExercise();
            setupExercise(viewHolder, exercise);
            setupEvent(viewHolder, repsProgramExercise);
            viewHolder.exerciseReps.setText(Formatter.formatReps(repsProgramExercise.getReps()));
         }

         @Override
         public void visit(RestProgramExercise rest) {
            RestViewHolder viewHolder = (RestViewHolder) holder;
            viewHolder.restTime.setText(Formatter.formatTime(rest.getSeconds()));
            setupEvent(viewHolder, rest);
         }

         @Override
         public void visit(TimedProgramExercise timedProgramExercise) {
            ExerciseViewHolder viewHolder = (ExerciseViewHolder) holder;
            Exercise exercise = timedProgramExercise.getExercise();
            setupExercise(viewHolder, exercise);
            setupEvent(viewHolder, timedProgramExercise);
            viewHolder.exerciseReps.setText(Formatter.formatTime(timedProgramExercise.getSeconds()));
         }

         private void setupEvent(CardViewHolder viewHolder, ProgramExercise programExercise) {
            viewHolder.cardView.setOnClickListener(l -> {
               PopupMenu menu = new PopupMenu(context, viewHolder.itemView);
               MenuInflater inflater = menu.getMenuInflater();
               inflater.inflate(R.menu.edit_menu_exercise, menu.getMenu());
               menu.setOnMenuItemClickListener(item -> {
                  switch (item.getItemId()) {
                     case R.id.edit_item:
                        programExercise.accept(new ProgramVisitor() {
                           @Override
                           public void visit(RepsProgramExercise repsProgramExercise) {
                              new EditExerciseFragment(program, obj -> {
                                 notifyItemChanged(position);
                                 ClientContext.getInstance().save(context);
                              }, repsProgramExercise).show(fragmentManager, EditExerciseFragment.TAG);
                           }

                           @Override
                           public void visit(RestProgramExercise rest) {
                              new EditRestFragment(program, obj -> {
                                 notifyItemChanged(position);
                                 ClientContext.getInstance().save(context);
                              }, rest).show(fragmentManager, EditExerciseFragment.TAG);
                           }

                           @Override
                           public void visit(TimedProgramExercise timedProgramExercise) {

                           }
                        });
                        return true;
                     case R.id.add_exercise_item:
                        FragmentNavigationHelper.navigateTo(new SelectExerciseFragment(stateListener, position, program), R.id.create_program_container, fragmentManager);
                        return true;
                     case R.id.add_rest_item:
                        new EditRestFragment(program, obj -> {
                           RestProgramExercise rest = (RestProgramExercise) obj;
                           program.addExercise(rest, position);
                           ClientContext.getInstance().save(context);
                           notifyItemChanged(position);
                        }).show(fragmentManager, EditRestFragment.TAG);
                        return true;
                     case R.id.delete_item:
                        program.removeExercise(position);
                        ClientContext.getInstance().save(context);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, program.getExercises().size());
                        return true;
                     default:
                        return false;
                  }
               });
               menu.show();
            });
         }

         private void setupExercise(ExerciseViewHolder viewHolder, Exercise exercise) {
            viewHolder.exerciseName.setText(exercise.getName());
            AssetService.instance().loadImage(context, viewHolder.exerciseImage, exercise);
            viewHolder.chips.removeAllViews();
            ChipUtils.createOtherChips(context, exercise).stream().forEach(c -> viewHolder.chips.addView(c));
            ChipUtils.createPrimaryMusclesChips(context, exercise.getPrimaryMuscles()).stream().forEach(c -> viewHolder.chips.addView(c));
            ChipUtils.createSecondaryMusclesChips(context, exercise.getSecondaryMuscles()).stream().forEach(c -> viewHolder.chips.addView(c));
         }
      });
   }

   @Override
   public int getItemCount() {
      return program.getExercises().size();
   }

   @Override
   public int getItemViewType(int position) {
      ProgramExercise exercise = program.getExercises().get(position);
      ProgramCardItemViewTypeAdapter mediator = new ProgramCardItemViewTypeAdapter();
      exercise.accept(mediator);
      return mediator.getItemViewType();
   }

   @Override
   public void onRowMoved(int from, int to) {
      if (from < to) {
         for (int i = from; i < to; i++) {
            Collections.swap(program.getExercises(), i, i + 1);
         }
      } else {
         for (int i = from; i > to; i--) {
            Collections.swap(program.getExercises(), i, i - 1);
         }
      }
      notifyItemMoved(from, to);
   }

   @Override
   public void onRowSelected(ProgramCardRecyclerViewAdapter.CardViewHolder viewHolder) {
      viewHolder.cardView.setCardBackgroundColor(Color.GRAY);
   }

   @Override
   public void onRowClear(ProgramCardRecyclerViewAdapter.CardViewHolder viewHolder) {
      viewHolder.cardView.setCardBackgroundColor(Color.WHITE);
   }

   public class CardViewHolder extends RecyclerView.ViewHolder {
      protected final CardView cardView;

      public CardViewHolder(View view) {
         super(view);
         cardView = (CardView) view;
      }
   }

   public class ExerciseViewHolder extends CardViewHolder {
      private final ImageView exerciseImage;
      private final TextView exerciseName;
      private final TextView exerciseReps;
      private final ChipGroup chips;

      public ExerciseViewHolder(View view) {
         super(view);
         exerciseImage = view.findViewById(R.id.exercise_img);
         exerciseName = view.findViewById(R.id.card_exercise_name);
         exerciseReps = view.findViewById(R.id.card_exercise_reps);
         chips = itemView.findViewById(R.id.chips_exercise_card);
      }
   }

   public class RestViewHolder extends CardViewHolder {
      public TextView restTime;

      public RestViewHolder(View view) {
         super(view);
         restTime = view.findViewById(R.id.card_rest_time);
      }
   }
}
