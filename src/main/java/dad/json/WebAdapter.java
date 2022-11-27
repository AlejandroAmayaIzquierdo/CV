package dad.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


import dad.contacto.Web;

public class WebAdapter implements JsonSerializer<Web>, JsonDeserializer<Web> {

	@Override
	public Web deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jo = json.getAsJsonObject();
		return new Web(
				jo.get("url").getAsString()
				);
	}

	@Override
	public JsonElement serialize(Web src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jo = new JsonObject();
		jo.addProperty("url", src.getUrl());
		return jo;
	}

}
