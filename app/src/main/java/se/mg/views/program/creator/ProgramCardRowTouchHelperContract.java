package se.mg.views.program.creator;

import se.mg.views.program.creator.adapters.ProgramCardRecyclerViewAdapter;

public interface ProgramCardRowTouchHelperContract {
   void onRowMoved(int from, int to);

   void onRowSelected(ProgramCardRecyclerViewAdapter.CardViewHolder viewHolder);

   void onRowClear(ProgramCardRecyclerViewAdapter.CardViewHolder viewHolder);
}
