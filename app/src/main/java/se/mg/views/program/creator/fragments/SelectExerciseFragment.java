package se.mg.views.program.creator.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import se.mg.R;
import se.mg.exercise.Exercise;
import se.mg.program.Program;
import se.mg.views.program.creator.ExerciseFilter;
import se.mg.views.program.creator.adapters.SelectExerciseRecyclerViewAdapter;
import se.mg.views.utils.StateListener;

public class SelectExerciseFragment extends Fragment implements StateListener {
   private static final long LOADED_EXERCISES_LIMIT = 5;
   private final Program program;
   private final StateListener stateListener;
   private final int position;
   private RecyclerView exerciseRv;
   private SelectExerciseRecyclerViewAdapter selectExerciseRecyclerViewAdapter;
   private SearchView searchView;
   private TextView resetButton;
   private ExerciseFilter exerciseFilter;
   private boolean isLoading = false;

   public SelectExerciseFragment(StateListener stateListener, int position, Program program) {
      super();
      this.program = program;
      this.position = position;
      this.stateListener = stateListener;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_select_exercise, container, false);
      view.setOnTouchListener((v, event) -> true);
      exerciseFilter = new ExerciseFilter();
      exerciseFilter.getFilterState().addListener(this);
      exerciseRv = view.findViewById(R.id.select_exercise_rv);
      resetButton = view.findViewById(R.id.selection_exercise_filter_reset);
      resetButton.setOnClickListener(b -> reset());
      searchView = view.findViewById(R.id.select_exercise_search);
      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextSubmit(String s) {
            exerciseFilter.getFilterState().setName(s);
            return true;
         }

         @Override
         public boolean onQueryTextChange(String s) {
            exerciseFilter.getFilterState().setName(s);
            return true;
         }
      });
      view.findViewById(R.id.select_exercise_filter).setOnClickListener(
              l -> new SelectExerciseFilterFragment(exerciseFilter.getFilterState())
                      .show(getParentFragmentManager(), SelectExerciseFilterFragment.TAG));
      selectExerciseRecyclerViewAdapter = new SelectExerciseRecyclerViewAdapter(getContext(), getParentFragmentManager(), program, stateListener, position, getLimitedFilteredExercises());
      exerciseRv.setAdapter(selectExerciseRecyclerViewAdapter);
      LinearLayoutManager manager = new LinearLayoutManager(getContext());
      exerciseRv.setLayoutManager(manager);
      exerciseRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
         @Override
         public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
         }

         @Override
         public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            if (!isLoading) {
               if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == selectExerciseRecyclerViewAdapter.getItemCount() - 1) {
                  loadMore();
                  isLoading = true;
               }
            }
         }
      });
      return view;
   }

   private List<Exercise> getLimitedFilteredExercises() {
      return exerciseFilter.getFilteredExercises().stream()
              .limit(LOADED_EXERCISES_LIMIT)
              .collect(Collectors.toList());
   }

   private void loadMore() {
      if (selectExerciseRecyclerViewAdapter.getItemCount() >= LOADED_EXERCISES_LIMIT) {
         selectExerciseRecyclerViewAdapter.addExercise(null);
         exerciseRv.post(() -> selectExerciseRecyclerViewAdapter.notifyItemInserted(selectExerciseRecyclerViewAdapter.getItemCount() - 1));

         List<Exercise> filteredExercises = exerciseFilter.getFilteredExercises();
         if (selectExerciseRecyclerViewAdapter.getItemCount() < filteredExercises.size()) {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
               selectExerciseRecyclerViewAdapter.removeLastExercise();
               int scrollPosition = selectExerciseRecyclerViewAdapter.getItemCount();
               selectExerciseRecyclerViewAdapter.notifyItemRemoved(scrollPosition);
               int currentSize = scrollPosition;
               int nextLimit = currentSize + (int) (LOADED_EXERCISES_LIMIT);
               if (filteredExercises.size() >= nextLimit) {
                  filteredExercises.subList(currentSize, nextLimit).stream()
                          .forEach(e -> selectExerciseRecyclerViewAdapter.addExercise(e));
               } else {
                  filteredExercises.subList(currentSize, filteredExercises.size()).stream()
                          .forEach(e -> selectExerciseRecyclerViewAdapter.addExercise(e));
               }
               selectExerciseRecyclerViewAdapter.notifyDataSetChanged();
               isLoading = false;
            }, 500);
         }
      }
   }

   private void reset() {
      exerciseFilter.reset();
   }

   @Override
   public void changeNotified(Object object) {
      selectExerciseRecyclerViewAdapter.setExercises(getLimitedFilteredExercises());
      selectExerciseRecyclerViewAdapter.notifyDataSetChanged();
   }
}