package se.mg.client;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import se.mg.program.ProgramExercise;

public class ClientPersistence {

   private static final String CLIENT_DATA = "client_data.json";

   private final Context context;

   public ClientPersistence(Context context) {
      this.context = context;
   }

   public void save(Client client) {
      FileOutputStream outputStream = null;
      try {
         Gson gson = new GsonBuilder()
                 .setPrettyPrinting()
                 .registerTypeAdapter(ProgramExercise.class, new ProgramExerciseAdapter())
                 .create();
         String json = gson.toJson(client);
         outputStream = context.openFileOutput(CLIENT_DATA, Context.MODE_PRIVATE);
         outputStream.write(json.getBytes());
         outputStream.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      } finally {
         if (outputStream != null) {
            try {
               outputStream.close();
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
         }
      }

   }

   public Client load() {
      Client client = null;
      FileInputStream inputStream = null;
      InputStreamReader inputStreamReader = null;
      BufferedReader bufferedReader = null;
      try {
         Gson gson = new GsonBuilder()
                 .registerTypeAdapter(ProgramExercise.class, new ProgramExerciseAdapter())
                 .create();
         inputStream = context.openFileInput(CLIENT_DATA);
         inputStreamReader = new InputStreamReader(inputStream);
         bufferedReader = new BufferedReader(inputStreamReader);

         StringBuilder stringBuilder = new StringBuilder();
         String line;
         while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
         }

         // Convert the JSON string to a JSONObject
         String jsonString = stringBuilder.toString();
         client = gson.fromJson(jsonString, Client.class);
      } catch (FileNotFoundException fe) {
         // Do nothing
      } catch (IOException e) {
         throw new RuntimeException(e);
      } finally {
         try {
            if (inputStream != null) {
               inputStream.close();
            }
            if (bufferedReader != null) {
               bufferedReader.close();
            }
            if (inputStreamReader != null) {
               inputStreamReader.close();
            }
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
      return client;
   }
}
