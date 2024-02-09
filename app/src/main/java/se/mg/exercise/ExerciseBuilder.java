package se.mg.exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseBuilder {
   protected final long version;
   protected final List<Muscle> primaryMuscles = new ArrayList<>();
   protected final List<Muscle> secondaryMuscles = new ArrayList<>();
   protected Category category;
   protected String id;
   protected String name;
   protected String note;
   protected Force force;
   protected Level level;
   protected Mechanic mechanic;
   protected Equipment equipment;

   public ExerciseBuilder(long version) {
      this.version = version;
   }

   public ExerciseBuilder id(String id) {
      this.id = id;
      return this;
   }

   public ExerciseBuilder name(String name) {
      this.name = name;
      return this;
   }

   public ExerciseBuilder note(String note) {
      this.note = note;
      return this;
   }

   public ExerciseBuilder force(Force force) {
      this.force = force;
      return this;
   }

   public ExerciseBuilder level(Level level) {
      this.level = level;
      return this;
   }

   public ExerciseBuilder category(Category category) {
      this.category = category;
      return this;
   }

   public ExerciseBuilder mechanic(Mechanic mechanic) {
      this.mechanic = mechanic;
      return this;
   }

   public ExerciseBuilder equipment(Equipment equipment) {
      this.equipment = equipment;
      return this;
   }

   public ExerciseBuilder addPrimaryMuscle(Muscle muscle) {
      this.primaryMuscles.add(muscle);
      return this;
   }

   public ExerciseBuilder addSecondaryMuscle(Muscle muscle) {
      this.secondaryMuscles.add(muscle);
      return this;
   }

   public Exercise build() {
      Exercise exercise = new Exercise(id, name, category, level, note, version);
      exercise.setForce(force);
      exercise.setMechanic(mechanic);
      exercise.setEquipment(equipment);
      exercise.setPrimaryMuscles(primaryMuscles);
      exercise.setSecondaryMuscles(secondaryMuscles);
      return exercise;
   }
}
