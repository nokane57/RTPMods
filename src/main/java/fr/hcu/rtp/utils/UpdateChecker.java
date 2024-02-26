package fr.hcu.rtp.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {

    private static final String UPDATE_URL = "https://api.github.com/repos/nokane57/RTPMods/releases/latest";

    public static void checkForUpdates() {
        try (Scanner scanner = new Scanner(new URL(UPDATE_URL).openStream())) {
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            String latestVersion = response.toString().split("\"tag_name\":\"")[1].split("\"")[0];
            String currentVersion = Constants.VERSION; // Replace Mod.VERSION with your actual version
            if (!latestVersion.equals(currentVersion)) {
                System.out.println("A new version (" + latestVersion + ") of the mod is available!");
                // Notify the user and prompt them to download the update
            } else {
                System.out.println("Your mod is up to date!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        checkForUpdates();
    }
}
