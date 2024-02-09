package se.mg.views.program.creator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import se.mg.R;
import se.mg.client.ClientContext;
import se.mg.program.Program;
import se.mg.views.program.creator.fragments.ProgramViewFragment;
import se.mg.views.utils.FragmentNavigationHelper;

public class CreateProgramView extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_program);
      setupViews();
   }

   private void setupViews() {
      Bundle bundle = getIntent().getExtras();
      int programIndex = bundle.getInt("programIndex");
      Program program = ClientContext.getInstance().getClient().getProgramList().get(programIndex);
      FragmentNavigationHelper.replaceTo(new ProgramViewFragment(program), R.id.create_program_container, getSupportFragmentManager());
   }
}