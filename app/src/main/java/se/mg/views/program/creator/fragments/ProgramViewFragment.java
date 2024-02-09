package se.mg.views.program.creator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.core.view.MenuCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import se.mg.R;
import se.mg.client.ClientContext;
import se.mg.program.Program;
import se.mg.program.RestProgramExercise;
import se.mg.views.program.creator.ProgramCardRecyclerRowCallback;
import se.mg.views.program.creator.adapters.ProgramCardRecyclerViewAdapter;
import se.mg.views.utils.FragmentNavigationHelper;
import se.mg.views.utils.StateListener;

public class ProgramViewFragment extends Fragment implements StateListener {
   private ProgramCardRecyclerViewAdapter adapter;
   private Program program;
   private RecyclerView recyclerView;
   private TextView programTextView;
   private TextView programEditView;
   private View backButton;
   private View addExerciseMenu;

   public ProgramViewFragment(Program program) {
      super();
      this.program = program;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_program_view, container, false);
      recyclerView = view.findViewById(R.id.rv_program_view);
      LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
      recyclerView.setLayoutManager(layoutManager);
      adapter = new ProgramCardRecyclerViewAdapter(getContext(), this, getParentFragmentManager(), program);
      ItemTouchHelper.Callback callback = new ProgramCardRecyclerRowCallback(adapter);
      ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
      touchHelper.attachToRecyclerView(recyclerView);
      recyclerView.setAdapter(adapter);
      programTextView = view.findViewById(R.id.create_program_name);
      programEditView = view.findViewById(R.id.create_program_edit_menu);
      addExerciseMenu = view.findViewById(R.id.create_exercise_add_menu);
      backButton = view.findViewById(R.id.program_back_button);
      setupViews();
      return view;
   }

   private void setupViews() {
      programTextView.setText(program.getName());
      addExerciseMenu.setOnClickListener(view -> {
         PopupMenu popupMenu = new PopupMenu(view.getContext(), addExerciseMenu);
         popupMenu.getMenuInflater().inflate(R.menu.create_program_menu, popupMenu.getMenu());
         MenuCompat.setGroupDividerEnabled(popupMenu.getMenu(), true);
         popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.menu_add_rest) {
               new EditRestFragment(program, this)
                       .show(getParentFragmentManager(), EditRestFragment.TAG);
            } else if (menuItem.getItemId() == R.id.menu_add_exercise) {
               FragmentNavigationHelper.navigateTo(new SelectExerciseFragment(this, -1, program), R.id.create_program_container, getParentFragmentManager());
            }
            return true;
         });
         popupMenu.show();
      });
      programEditView.setOnClickListener(l -> new EditProgramFragment(program, this)
              .show(getParentFragmentManager(), EditProgramFragment.TAG));
      backButton.setOnClickListener(l -> getActivity().finish());
   }

   @Override
   public void changeNotified(Object object) {
      if (object instanceof Program) {
         programTextView.setText(((Program) object).getName());
         ClientContext.getInstance().save(getContext());
      } else if (object instanceof RestProgramExercise) {
         program.addExercise((RestProgramExercise) object);
         ClientContext.getInstance().save(getContext());
         adapter.notifyItemInserted(adapter.getItemCount());
      } else {
         adapter.notifyDataSetChanged();
      }
   }
}
