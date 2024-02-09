package se.mg.views.program.creator;

import java.util.ArrayList;
import java.util.List;

import se.mg.exercise.Category;
import se.mg.exercise.Equipment;
import se.mg.exercise.Force;
import se.mg.exercise.Level;
import se.mg.exercise.Mechanic;
import se.mg.exercise.Muscle;
import se.mg.views.utils.StateListener;

public class SelectExerciseFilterState {
   private String name = "";
   private List<StateListener> listeners = new ArrayList<>();
   private Mechanic mechanic = null;
   private Level level = null;
   private Equipment equipment = null;
   private Category category = null;
   private Force force = null;
   private List<Muscle> primaryMuscles = new ArrayList<>();
   private List<Muscle> secondaryMuscles = new ArrayList<>();

   public SelectExerciseFilterState() {
   }

   public void reset() {
      name = "";
      mechanic = null;
      level = null;
      equipment = null;
      category = null;
      force = null;
      primaryMuscles.clear();
      secondaryMuscles.clear();
      notifyChanges();
   }

   public void addListener(StateListener listener) {
      listeners.add(listener);
   }

   private void notifyChanges() {
      listeners.stream().forEach(l -> l.changeNotified(this));
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name.toLowerCase();
      notifyChanges();
   }

   public void setLevel(Level level) {
      this.level = level;
      notifyChanges();
   }

   public void setForce(Force force) {
      this.force = force;
      notifyChanges();
   }

   public void setMechanic(Mechanic mechanic) {
      this.mechanic = mechanic;
      notifyChanges();
   }

   public void setEquipment(Equipment equipment) {
      this.equipment = equipment;
      notifyChanges();
   }

   public void setCategory(Category category) {
      this.category = category;
      notifyChanges();
   }

   public void addPrimaryMuscle(Muscle muscle) {
      primaryMuscles.add(muscle);
      notifyChanges();
   }

   public void removePrimaryMuscle(Muscle muscle) {
      primaryMuscles.remove(muscle);
      notifyChanges();
   }

   public void addSecondaryMuscle(Muscle muscle) {
      secondaryMuscles.add(muscle);
      notifyChanges();
   }

   public void removeSecondaryMuscle(Muscle muscle) {
      secondaryMuscles.remove(muscle);
      notifyChanges();
   }

   public Mechanic getMechanic() {
      return mechanic;
   }

   public Level getLevel() {
      return level;
   }

   public Equipment getEquipment() {
      return equipment;
   }

   public Category getCategory() {
      return category;
   }

   public Force getForce() {
      return force;
   }

   public List<Muscle> getPrimaryMuscles() {
      return primaryMuscles;
   }

   public List<Muscle> getSecondaryMuscles() {
      return secondaryMuscles;
   }
}
