package se.mg.util;

import se.mg.program.RepsRange;

public final class Formatter {
   public static String formatTime(int totalSecs) {
      long minutes = totalSecs / 60;
      long seconds = totalSecs % 60;
      StringBuilder sb = new StringBuilder();
      if (minutes < 10) {
         sb.append("0");
      }
      sb.append(minutes).append(":");
      if (seconds < 10) {
         sb.append("0");
      }
      sb.append(seconds);
      return sb.toString();
   }

   public static String formatReps(RepsRange repsRange) {
      StringBuilder sb = new StringBuilder();
      if (repsRange.getMin() < repsRange.getMax()) {
         sb.append(repsRange.getMin());
         sb.append(" - ");
         sb.append(repsRange.getMax());
      } else {
         sb.append(repsRange.getMax());
      }
      sb.append(" reps");
      return sb.toString();
   }
}
