package dad.json;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate>{

	@Override
	public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jo = new JsonObject();
		jo.addProperty("year", date.getYear());
		jo.addProperty("month", date.getMonthValue());
		jo.addProperty("day", date.getDayOfMonth());
		return jo;
	}

	@Override
	public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jo = json.getAsJsonObject();
		return LocalDate.of(
			jo.get("year").getAsInt(),
			jo.get("month").getAsInt(),
			jo.get("day").getAsInt()
		);
	}

}
