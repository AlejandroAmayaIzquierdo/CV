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

import dad.Experiencia.Experiencia;

public class ExperenciaAdapter implements JsonSerializer<Experiencia>, JsonDeserializer<Experiencia> {

	@Override
	public Experiencia deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jo = json.getAsJsonObject();
		return new Experiencia(
				LocalDateTime.parse(jo.get("desde").getAsString()),
				LocalDateTime.parse(jo.get("hasta").getAsString()),
				jo.get("denominacion").getAsString(),
				jo.get("empleador").getAsString());
	}

	@Override
	public JsonElement serialize(Experiencia src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jo = new JsonObject();
		jo.addProperty("desde", src.getDesde().toString());
		jo.addProperty("hasta", src.getHasta().toString());
		jo.addProperty("denominacion", src.getDenominacion());
		jo.addProperty("empleador", src.getEmpleador());
		return jo;
	}

}
