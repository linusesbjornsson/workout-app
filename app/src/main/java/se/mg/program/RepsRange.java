package se.mg.program;

import java.io.Serializable;

public class RepsRange implements Serializable {
   private static final long serialVersionUID = 1L;

   private int min;
   private int max;

   public RepsRange(int minMax) {
      this.min = minMax;
      this.max = minMax;
   }

   public RepsRange(int min, int max) {
      this.min = min;
      this.max = max;
   }

   public int getMin() {
      return min;
   }

   public int getMax() {
      return max;
   }

   @Override
   public String toString() {
      if (min < max) {
         return min + " - " + max;
      } else {
         return String.valueOf(max);
      }
   }
}
