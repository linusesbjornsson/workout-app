package se.mg.views.program.creator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import se.mg.views.program.creator.adapters.ProgramCardRecyclerViewAdapter;

public class ProgramCardRecyclerRowCallback extends ItemTouchHelper.Callback {
   private ProgramCardRowTouchHelperContract touchHelperContract;

   public ProgramCardRecyclerRowCallback(ProgramCardRowTouchHelperContract touchHelperContract) {
      this.touchHelperContract = touchHelperContract;
   }

   @Override
   public boolean isLongPressDragEnabled() {
      return true;
   }

   @Override
   public boolean isItemViewSwipeEnabled() {
      return false;
   }

   @Override
   public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
      int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
      return makeMovementFlags(dragFlag, 0);
   }

   @Override
   public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
      this.touchHelperContract.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
      return false;
   }

   @Override
   public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
      if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
         touchHelperContract.onRowSelected((ProgramCardRecyclerViewAdapter.CardViewHolder) viewHolder);
      }
      super.onSelectedChanged(viewHolder, actionState);
   }

   @Override
   public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
      super.clearView(recyclerView, viewHolder);
      touchHelperContract.onRowClear((ProgramCardRecyclerViewAdapter.CardViewHolder) viewHolder);
   }

   @Override
   public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

   }
}
