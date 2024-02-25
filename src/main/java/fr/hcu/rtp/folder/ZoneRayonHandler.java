package fr.hcu.rtp.folder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ZoneRayonHandler {

    private final File zoneRayonFile;
    private final JsonObject zonesRayonJson;

    public ZoneRayonHandler() {
        zoneRayonFile = new File("config/HCU/Zones/ZonesRayon.json");
        if (!zoneRayonFile.exists()) {
            try {
                zoneRayonFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        zonesRayonJson = loadZonesFromFile();
    }

    private JsonObject loadZonesFromFile() {
        JsonObject jsonObject = new JsonObject();
        try (FileReader reader = new FileReader(zoneRayonFile)) {
            JsonParser parser = new JsonParser();
            jsonObject = parser.parse(reader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void saveZone(String zoneName, float x, float y, float z, float rayon) {
        JsonObject zoneJson = new JsonObject();
        zoneJson.addProperty("x", x);
        zoneJson.addProperty("y", y);
        zoneJson.addProperty("z", z);
        zoneJson.addProperty("rayon", rayon);
        zonesRayonJson.add(zoneName, zoneJson);

        try (FileWriter writer = new FileWriter(zoneRayonFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(zonesRayonJson, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject getZonesJson() {
        return zonesRayonJson;
    }
}
