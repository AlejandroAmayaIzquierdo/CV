package dad.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dad.contacto.Email;

public class EmailAdapter implements JsonSerializer<Email>, JsonDeserializer<Email> {

	@Override
	public Email deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jo = json.getAsJsonObject();
		return new Email(
				jo.get("direccion").getAsString());
	}

	@Override
	public JsonElement serialize(Email src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jo = new JsonObject();
		jo.addProperty("direccion", src.getDireccion());
		return jo;
	}

}
