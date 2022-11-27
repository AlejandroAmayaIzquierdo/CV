package dad.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dad.Conocimiento.Conocimiento;

public class ConocimientoAdapter implements JsonSerializer<Conocimiento>, JsonDeserializer<Conocimiento> {

	@Override
	public Conocimiento deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jo = json.getAsJsonObject();
		return new Conocimiento(
				jo.get("denominacion").getAsString(),
				jo.get("nivel").getAsString()
				);
	}

	@Override
	public JsonElement serialize(Conocimiento src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jo = new JsonObject();
		jo.addProperty("denominacion", src.getDenominacion());
		jo.addProperty("nivel", src.getNivel() + "");
		return jo;
	}

}
