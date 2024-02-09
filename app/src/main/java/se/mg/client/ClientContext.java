package se.mg.client;

import android.content.Context;

public class ClientContext {
   private static ClientContext instance = null;
   private final Client client;

   public ClientContext(Client client) {
      this.client = client;
   }

   public static ClientContext getInstance() {
      if (instance == null) {
         throw new RuntimeException("ClientContext is not initialized");
      }
      return instance;
   }

   public static void load(Context context) {
      ClientPersistence persistence = new ClientPersistence(context);
      Client client = persistence.load();
      if (client == null) {
         client = new Client();
      }
      instance = new ClientContext(client);
   }

   public void save(Context context) {
      ClientPersistence persistence = new ClientPersistence(context);
      persistence.save(client);
   }

   public Client getClient() {
      return client;
   }
}
