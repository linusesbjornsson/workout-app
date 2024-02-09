package se.mg.client;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import se.mg.program.ProgramExercise;

public class ProgramExerciseAdapter implements JsonSerializer<ProgramExercise>, JsonDeserializer<ProgramExercise> {

   public ProgramExerciseAdapter() {

   }

   @Override
   public JsonElement serialize(ProgramExercise programExercise, Type typeOfSrc, JsonSerializationContext context) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", programExercise.getClass().getName());
      jsonObject.add("data", context.serialize(programExercise));
      return jsonObject;
   }

   @Override
   public ProgramExercise deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      JsonObject jsonObject = json.getAsJsonObject();
      String type = jsonObject.get("type").getAsString();
      JsonElement data = jsonObject.get("data");
      try {
         return context.deserialize(data, Class.forName(type));
      } catch (ClassNotFoundException e) {
         throw new JsonParseException("Unknown element type: " + type, e);
      }
   }
}
