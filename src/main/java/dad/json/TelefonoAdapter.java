package dad.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dad.contacto.Telefono;

public class TelefonoAdapter implements JsonSerializer<Telefono>, JsonDeserializer<Telefono> {

	@Override
	public Telefono deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jo = json.getAsJsonObject();
		return new Telefono(
				jo.get("Numero").getAsString(),
				jo.get("Tipo").getAsString()
				);
	}

	@Override
	public JsonElement serialize(Telefono src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jo = new JsonObject();
		jo.addProperty("Numero", src.getNumero());
		jo.addProperty("Tipo", src.getTipo() + "");
		return jo;
	}

}
