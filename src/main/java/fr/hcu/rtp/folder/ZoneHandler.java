package fr.hcu.rtp.folder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ZoneHandler {

    private final File zoneFolder;
    private final File zoneFile;
    private JsonObject zonesJson;

    public ZoneHandler() {
        zoneFolder = new File("config/HCU/Zones/");
        zoneFile = new File(zoneFolder, "Zones.json");
        if (!zoneFolder.exists()) {
            zoneFolder.mkdirs();
        }
    }

    private void createFileIfNeeded() {
        if (!zoneFile.exists()) {
            try {
                zoneFile.createNewFile();
                zonesJson = new JsonObject();
                saveZonesToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            zonesJson = loadZonesFromFile();
        }
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
        createFileIfNeeded();
        JsonObject zoneJson = new JsonObject();
        zoneJson.addProperty("x", x);
        zoneJson.addProperty("y", y);
        zoneJson.addProperty("z", z);
        zonesJson.add(zoneName, zoneJson);
        saveZonesToFile();
    }

    private void saveZonesToFile() {
        try (FileWriter writer = new FileWriter(zoneFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(zonesJson, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject getZonesJson() {
        createFileIfNeeded();
        return zonesJson;
    }
}
