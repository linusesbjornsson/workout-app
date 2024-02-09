package se.mg.entity;

import java.util.ArrayList;
import java.util.List;

import se.mg.exercise.Category;
import se.mg.exercise.Equipment;
import se.mg.exercise.Force;
import se.mg.exercise.Level;
import se.mg.exercise.Mechanic;
import se.mg.exercise.Muscle;

public abstract class BaseEntity extends Entity {

   protected Force force;
   protected Level level;
   protected Category category;
   protected Equipment equipment;
   protected Mechanic mechanic;
   protected List<Muscle> primaryMuscles = new ArrayList<>();
   protected List<Muscle> secondaryMuscles = new ArrayList<>();

   public BaseEntity(long version) {
      super(version);
   }

   public Force getForce() {
      return force;
   }

   public void setForce(Force force) {
      this.force = force;
   }

   public Level getLevel() {
      return level;
   }

   public void setLevel(Level level) {
      this.level = level;
   }

   public Category getCategory() {
      return category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }

   public Equipment getEquipment() {
      return equipment;
   }

   public void setEquipment(Equipment equipment) {
      this.equipment = equipment;
   }

   public Mechanic getMechanic() {
      return mechanic;
   }

   public void setMechanic(Mechanic mechanic) {
      this.mechanic = mechanic;
   }

   public List<Muscle> getPrimaryMuscles() {
      return primaryMuscles;
   }

   public void setPrimaryMuscles(List<Muscle> primaryMuscles) {
      this.primaryMuscles = primaryMuscles;
   }

   public List<Muscle> getSecondaryMuscles() {
      return secondaryMuscles;
   }

   public void setSecondaryMuscles(List<Muscle> secondaryMuscles) {
      this.secondaryMuscles = secondaryMuscles;
   }
}
