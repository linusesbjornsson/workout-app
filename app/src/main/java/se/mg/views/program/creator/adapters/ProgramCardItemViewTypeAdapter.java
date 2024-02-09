package se.mg.views.program.creator.adapters;

import se.mg.program.ProgramVisitor;
import se.mg.program.RepsProgramExercise;
import se.mg.program.RestProgramExercise;
import se.mg.program.TimedProgramExercise;

public class ProgramCardItemViewTypeAdapter implements ProgramVisitor {

   private int itemViewType;

   @Override
   public void visit(RepsProgramExercise repsProgramExercise) {
      itemViewType = ProgramCardRecyclerViewAdapter.EXERCISE;
   }

   @Override
   public void visit(RestProgramExercise rest) {
      itemViewType = ProgramCardRecyclerViewAdapter.REST;
   }

   @Override
   public void visit(TimedProgramExercise timedProgramExercise) {
      itemViewType = ProgramCardRecyclerViewAdapter.EXERCISE;
   }

   public int getItemViewType() {
      return itemViewType;
   }
}
