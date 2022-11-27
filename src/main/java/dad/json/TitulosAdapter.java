package dad.json;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dad.Formacion.Titulo;

public class TitulosAdapter implements JsonSerializer<Titulo>, JsonDeserializer<Titulo> {

	@Override
	public Titulo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jo = json.getAsJsonObject();
		return new Titulo(
				LocalDateTime.parse(jo.get("desde").getAsString()),
				LocalDateTime.parse(jo.get("hasta").getAsString()),
				jo.get("denominacion").getAsString(),
				jo.get("organizador").getAsString());
	}

	@Override
	public JsonElement serialize(Titulo src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jo = new JsonObject();
		jo.addProperty("desde", src.getDesde().toString());
		jo.addProperty("hasta", src.getHasta().toString());
		jo.addProperty("denominacion", src.getDenominacion());
		jo.addProperty("organizador", src.getOrganizador());
		return jo;
	}

}
