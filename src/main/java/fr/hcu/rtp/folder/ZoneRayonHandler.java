package fr.hcu.rtp.folder;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ZoneRayonHandler {

    private final File zoneRayonFolder;
    private final File zoneRayonFile;
    private JsonObject zonesRayonJson;

    public ZoneRayonHandler() {
        zoneRayonFolder = new File("config/HCU/Zones/");
        zoneRayonFile = new File(zoneRayonFolder, "ZonesRayon.json");
        if (!zoneRayonFolder.exists()) {
            zoneRayonFolder.mkdirs();
        }
    }

    private void createFileIfNeeded() {
        if (!zoneRayonFile.exists()) {
            try {
                zoneRayonFile.createNewFile();
                zonesRayonJson = new JsonObject();
                saveZonesToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            zonesRayonJson = loadZonesFromFile();
        }
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
        createFileIfNeeded();
        JsonObject zoneJson = new JsonObject();
        zoneJson.addProperty("x", x);
        zoneJson.addProperty("y", y);
        zoneJson.addProperty("z", z);
        zoneJson.addProperty("rayon", rayon);
        zonesRayonJson.add(zoneName, zoneJson);
        saveZonesToFile();
    }

    private void saveZonesToFile() {
        try (FileWriter writer = new FileWriter(zoneRayonFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(zonesRayonJson, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject getZonesJson() {
        createFileIfNeeded();
        return zonesRayonJson;
    }
}