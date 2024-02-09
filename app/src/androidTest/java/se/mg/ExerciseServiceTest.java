package se.mg;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import se.mg.exercise.ExerciseService;
import se.mg.util.AssetService;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExerciseServiceTest {

   private Context context;

   @Before
   public void setupAssetService() {
      context = InstrumentationRegistry.getInstrumentation().getTargetContext();
      AssetService.instance().init(context.getAssets());
   }

   @Test
   public void test_exercise_service_init() {
      assertTrue(!ExerciseService.instance().getExercises().isEmpty());
   }
}
