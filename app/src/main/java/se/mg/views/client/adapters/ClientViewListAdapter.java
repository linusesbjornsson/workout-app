package se.mg.views.client.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import se.mg.R;
import se.mg.client.Client;
import se.mg.client.ClientContext;
import se.mg.program.Program;
import se.mg.views.program.creator.CreateProgramView;
import se.mg.views.runner.ProgramRunnerView;
import se.mg.views.utils.ChipUtils;
import se.mg.views.utils.StateListener;

public class ClientViewListAdapter extends RecyclerView.Adapter<ClientViewListAdapter.ItemViewHolder> implements StateListener {

   private Context context;
   private Client client;

   public ClientViewListAdapter(Context context, Client client) {
      this.context = context;
      this.client = client;
   }

   @NonNull
   @Override
   public ClientViewListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.program_card, parent, false));
   }

   @Override
   public void onBindViewHolder(@NonNull ClientViewListAdapter.ItemViewHolder holder, int position) {
      Program program = client.getProgramList().get(position);
      holder.itemView.setOnClickListener(l -> {
         Context context = l.getContext();
         Intent intent = new Intent(context, ProgramRunnerView.class);
         Bundle bundle = new Bundle();
         bundle.putSerializable("program", program);
         intent.putExtras(bundle);
         context.startActivity(intent);
      });
      holder.itemView.setOnLongClickListener(v -> {
         PopupMenu menu = new PopupMenu(context, holder.itemView);
         MenuInflater inflater = menu.getMenuInflater();
         inflater.inflate(R.menu.edit_menu, menu.getMenu());
         menu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
               case R.id.edit_item:
                  Context context = v.getContext();
                  Intent intent = new Intent(context, CreateProgramView.class);
                  Bundle bundle = new Bundle();
                  bundle.putInt("programIndex", position);
                  intent.putExtras(bundle);
                  context.startActivity(intent);
                  return true;
               case R.id.delete_item:
                  client.removeProgram(position);
                  ClientContext.getInstance().save(v.getContext());
                  notifyItemRemoved(position);
                  notifyItemRangeChanged(position, program.getExercises().size());
                  return true;
               default:
                  return false;
            }
         });
         menu.show();
         return true;
      });
      holder.programName.setText(program.getName());
      holder.programNote.setText(program.getNote());
      holder.chips.removeAllViews();
      ChipUtils.createOtherChips(context, program).stream().forEach(c -> holder.chips.addView(c));
      ChipUtils.createPrimaryMusclesChips(context, program.getPrimaryMuscles()).stream().forEach(c -> holder.chips.addView(c));
   }

   @Override
   public int getItemCount() {
      return client.getProgramList() == null ? 0 : client.getProgramList().size();
   }

   @Override
   public int getItemViewType(int position) {
      return 0;
   }

   @Override
   public void changeNotified(Object object) {
      if (object instanceof Program) {
         notifyDataSetChanged();
      }
   }

   public class ItemViewHolder extends RecyclerView.ViewHolder {
      private final TextView programName;
      private final TextView programNote;
      private final ChipGroup chips;

      public ItemViewHolder(@NonNull View itemView) {
         super(itemView);
         programName = itemView.findViewById(R.id.program_name);
         programNote = itemView.findViewById(R.id.program_note);
         chips = itemView.findViewById(R.id.chips_program_card);
      }
   }
}