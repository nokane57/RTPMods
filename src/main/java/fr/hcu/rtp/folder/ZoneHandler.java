package fr.hcu.rtp.folder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.hcu.rtp.Main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ZoneHandler {

    private final File zoneFile;
    private final JsonObject zonesJson;

    public ZoneHandler() {
        zoneFile = new File("config/HCU/Zones/Zones.json");
        if (!zoneFile.exists()) {
            try {
                zoneFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        zonesJson = loadZonesFromFile();
    }

    private JsonObject loadZonesFromFile() {
        JsonObject jsonObject = new JsonObject();
        try (FileReader reader = new FileReader(zoneFile)) {
            JsonParser parser = new JsonParser();
            jsonObject = parser.parse(reader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void saveZone(String zoneName, float x, float y, float z) {
        JsonObject zoneJson = new JsonObject();
        zoneJson.addProperty("x", x);
        zoneJson.addProperty("y", y);
        zoneJson.addProperty("z", z);
        zonesJson.add(zoneName, zoneJson);

        try (FileWriter writer = new FileWriter(zoneFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(zonesJson, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject getZonesJson() {
        return zonesJson;
    }
}
