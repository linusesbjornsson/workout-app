package se.mg.views.client;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import se.mg.R;
import se.mg.client.Client;
import se.mg.client.ClientContext;
import se.mg.program.Program;
import se.mg.util.AssetService;
import se.mg.views.client.adapters.ClientViewListAdapter;
import se.mg.views.program.creator.fragments.EditExerciseFragment;
import se.mg.views.program.creator.fragments.EditProgramFragment;
import se.mg.views.utils.StateListener;

public class ClientView extends AppCompatActivity implements StateListener {

   private Client client;
   private RecyclerView programListRv;
   private ClientViewListAdapter adapter;
   private ImageView addProgramButton;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_client_view);
      AssetService.instance().init(getAssets());
      ClientContext.load(this);
      client = ClientContext.getInstance().getClient();
      programListRv = findViewById(R.id.rv_client_programs);
      LinearLayoutManager layoutManager = new LinearLayoutManager(this);
      programListRv.setLayoutManager(layoutManager);
      addProgramButton = findViewById(R.id.create_program_button);
      addProgramButton.setOnClickListener(l ->
              new EditProgramFragment(null, this)
                      .show(getSupportFragmentManager(), EditExerciseFragment.TAG));
      adapter = new ClientViewListAdapter(this, client);
      programListRv.setAdapter(adapter);
   }

   @Override
   public void changeNotified(Object object) {
      if (object instanceof Program) {
         Program program = (Program) object;
         client.addProgram(program);
         ClientContext.getInstance().save(this);
         adapter.notifyDataSetChanged();
      }
   }

   @Override
   protected void onRestart() {
      super.onRestart();
      adapter.notifyDataSetChanged();
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();
   }
}