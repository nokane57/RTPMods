package fr.hcu.rtp.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {


    public static void checkForUpdates() {
        try (Scanner scanner = new Scanner(new URL(Constants.UPDATER).openStream())) {
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            String latestVersion = response.toString().split("\"tag_name\":\"")[1].split("\"")[0];
            String currentVersion = Constants.VERSION;
            if (!latestVersion.equals(currentVersion)) {
                System.out.println("A new version (" + latestVersion + ") of the mod is available!");
            } else {
                System.out.println("Your mod is up to date!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
