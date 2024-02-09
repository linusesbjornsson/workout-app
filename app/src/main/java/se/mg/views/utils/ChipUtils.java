package se.mg.views.utils;

import android.content.Context;

import com.google.android.material.chip.Chip;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

import se.mg.R;
import se.mg.entity.BaseEntity;
import se.mg.exercise.Category;
import se.mg.exercise.Equipment;
import se.mg.exercise.Force;
import se.mg.exercise.Level;
import se.mg.exercise.Mechanic;
import se.mg.exercise.Muscle;

public class ChipUtils {

   public static List<Chip> createPrimaryMusclesChips(Context context, List<Muscle> primaryMuscles) {
      List<Chip> chips = new ArrayList<>();
      for (Muscle muscle : primaryMuscles) {
         chips.add(createPrimaryMuscleChip(context, muscle));
      }
      return chips;
   }

   public static List<Chip> createSecondaryMusclesChips(Context context, List<Muscle> secondaryMuscles) {
      List<Chip> chips = new ArrayList<>();
      for (Muscle muscle : secondaryMuscles) {
         chips.add(createSecondaryMuscleChip(context, muscle));
      }
      return chips;
   }

   public static List<Chip> createOtherChips(Context context, BaseEntity entity) {
      List<Chip> chips = new ArrayList<>();
      if (entity.getLevel() != null) {
         chips.add(createLevelChip(context, entity.getLevel()));
      }
      if (entity.getCategory() != null) {
         chips.add(createCategoryChip(context, entity.getCategory()));
      }
      if (entity.getForce() != null) {
         chips.add(createForceChip(context, entity.getForce()));
      }
      if (entity.getMechanic() != null) {
         chips.add(createMechanicChip(context, entity.getMechanic()));
      }
      if (entity.getEquipment() != null) {
         chips.add(createEquipmentChip(context, entity.getEquipment()));
      }
      return chips;
   }

   public static Chip createChip(Context context, String text, int color) {
      Chip chip = new Chip(context);
      chip.setText(WordUtils.capitalizeFully(text.replace("_", " ")));
      chip.setClickable(false);
      chip.setChipBackgroundColorResource(color);
      return chip;
   }

   public static Chip createPrimaryMuscleChip(Context context, Muscle muscle) {
      return createChip(context, muscle.name(), R.color.chip_primary_muscle);
   }

   public static Chip createSecondaryMuscleChip(Context context, Muscle muscle) {
      return createChip(context, muscle.name(), R.color.chip_secondary_muscle);
   }

   public static Chip createLevelChip(Context context, Level level) {
      return createChip(context, level.name(), R.color.chip_exercise_level);
   }

   public static Chip createCategoryChip(Context context, Category category) {
      return createChip(context, category.name(), R.color.chip_exercise_category);
   }

   public static Chip createMechanicChip(Context context, Mechanic mechanic) {
      return createChip(context, mechanic.name(), R.color.chip_exercise_mechanic);
   }

   public static Chip createForceChip(Context context, Force force) {
      return createChip(context, force.name(), R.color.chip_exercise_force);
   }

   public static Chip createEquipmentChip(Context context, Equipment equipment) {
      return createChip(context, equipment.name(), R.color.chip_exercise_equipment);
   }
}
