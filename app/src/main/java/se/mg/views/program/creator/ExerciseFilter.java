package se.mg.views.program.creator;

import java.util.List;
import java.util.stream.Collectors;

import se.mg.exercise.Exercise;
import se.mg.exercise.ExerciseService;

public class ExerciseFilter {
   private SelectExerciseFilterState filterState = new SelectExerciseFilterState();

   public ExerciseFilter() {

   }

   public SelectExerciseFilterState getFilterState() {
      return filterState;
   }

   public void reset() {
      filterState.reset();
   }

   public List<Exercise> getFilteredExercises() {
      return ExerciseService.instance().getExercises().stream()
              .filter(e -> {
                 if (filterState.getLevel() != null
                         && (e.getLevel() == null || filterState.getLevel() != e.getLevel())) {
                    return false;
                 }
                 if (filterState.getCategory() != null
                         && (e.getCategory() == null || filterState.getCategory() != e.getCategory())) {
                    return false;
                 }
                 if (filterState.getEquipment() != null
                         && (e.getEquipment() == null || filterState.getEquipment() != e.getEquipment())) {
                    return false;
                 }
                 if (filterState.getForce() != null
                         && (e.getForce() == null || filterState.getForce() != e.getForce())) {
                    return false;
                 }
                 if (filterState.getMechanic() != null
                         && (e.getMechanic() == null || filterState.getMechanic() != e.getMechanic())) {
                    return false;
                 }
                 if (!filterState.getPrimaryMuscles().isEmpty()
                         && (!filterState.getPrimaryMuscles().containsAll(e.getPrimaryMuscles())
                         || !e.getPrimaryMuscles().containsAll(filterState.getPrimaryMuscles()))) {
                    return false;
                 }
                 if (!filterState.getSecondaryMuscles().isEmpty()
                         && (!filterState.getSecondaryMuscles().containsAll(e.getSecondaryMuscles())
                         || !e.getSecondaryMuscles().containsAll(filterState.getSecondaryMuscles()))) {
                    return false;
                 }
                 if (!filterState.getName().isEmpty() && !e.getName().toLowerCase().contains(filterState.getName())) {
                    return false;
                 }
                 return true;
              })
              .collect(Collectors.toList());
   }
}
