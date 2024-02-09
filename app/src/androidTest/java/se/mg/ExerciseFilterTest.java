package se.mg;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import se.mg.exercise.Exercise;
import se.mg.exercise.Muscle;
import se.mg.util.AssetService;
import se.mg.views.program.creator.ExerciseFilter;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ExerciseFilterTest {
   private Context context;
   private ExerciseFilter filter;

   @Before
   public void setupAssetService() {
      context = InstrumentationRegistry.getInstrumentation().getTargetContext();
      filter = new ExerciseFilter();
      AssetService.instance().init(context.getAssets());
   }

   @Test
   public void filter_chest_exercises_should_return_size() {
      filter.getFilterState().addPrimaryMuscle(Muscle.CHEST);
      List<Exercise> result = filter.getFilteredExercises();
      assertEquals(84, result.size());
   }

   @Test
   public void filter_middle_back_exercises_should_return_size() {
      filter.getFilterState().addPrimaryMuscle(Muscle.MIDDLE_BACK);
      List<Exercise> result = filter.getFilteredExercises();
      assertEquals(34, result.size());
   }
}
