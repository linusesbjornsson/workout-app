package se.mg.views.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentNavigationHelper {

   public static void navigateToNoBackStack(Fragment fragment, int id, FragmentManager fragmentManager) {
      navigateTo(fragment, id, fragmentManager, false);
   }

   public static void navigateTo(Fragment fragment, int id, FragmentManager fragmentManager) {
      navigateTo(fragment, id, fragmentManager, true);
   }

   public static void replaceTo(Fragment fragment, int id, FragmentManager fragmentManager) {
      final FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.replace(id, fragment);
      transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
      transaction.commit();
   }

   public static void navigateTo(Fragment fragment, int id, FragmentManager fragmentManager, boolean backStack) {
      final FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.add(id, fragment);
      transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
      if (backStack) {
         transaction.addToBackStack(null);
      }
      transaction.commit();
   }

   public static void remove(Fragment fragment, FragmentManager fragmentManager) {
      final FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.remove(fragment);
      fragmentManager.popBackStack();
      transaction.commit();
   }
}
