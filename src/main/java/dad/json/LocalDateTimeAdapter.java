package dad.json;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

	@Override
	public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jo = json.getAsJsonObject();
		return LocalDateTime.of(
				jo.get("year").getAsInt(),
				jo.get("month").getAsInt(),
				jo.get("day").getAsInt(),
				0,
				0
			);
	}

	@Override
	public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jo = new JsonObject();
		jo.addProperty("year", src.getYear());
		jo.addProperty("month", src.getMonthValue());
		jo.addProperty("day", src.getDayOfMonth());
		return jo;
	}

}
