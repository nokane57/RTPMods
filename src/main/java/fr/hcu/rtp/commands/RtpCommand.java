package fr.hcu.rtp.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.hcu.rtp.folder.ZoneHandler;
import fr.hcu.rtp.folder.ZoneRayonHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.server.permission.PermissionAPI;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RtpCommand extends CommandBase {

    private final ZoneHandler zoneHandler = new ZoneHandler();
    private final ZoneRayonHandler zoneRayonHandler = new ZoneRayonHandler();

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Seul un joueur peut exécuter cette commande !"));
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;

        if (!PermissionAPI.hasPermission(player, "hcu.rtp.use")) {
            player.sendMessage(new TextComponentString(TextFormatting.RED + "Vous n'avez pas la permission d'utiliser cette command !"));
            return;
        }

        List<String> zoneNames = getZoneNames();
        if (zoneNames.isEmpty()) {
            player.sendMessage(new TextComponentString(TextFormatting.RED + "Aucune zone disponible pour le téléport !"));
            return;
        }

        String randomZoneName = zoneNames.get(new Random().nextInt(zoneNames.size()));
        JsonObject zoneData = zoneHandler.getZonesJson().getAsJsonObject(randomZoneName);
        float zoneX = zoneData.get("x").getAsFloat();
        float zoneY = zoneData.get("y").getAsFloat();
        float zoneZ = zoneData.get("z").getAsFloat();

        if (!isPlayerInAllowedZone(player)) {
            player.sendMessage(new TextComponentString(TextFormatting.RED + "Vous n'êtes pas dans une zone où vous pouvez être téléporté !"));
            return;
        }
        player.setPositionAndUpdate(zoneX, zoneY, zoneZ);
        player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Vous avez été téléporté dans la zone : " + TextFormatting.AQUA + randomZoneName));
    }

    private boolean isPlayerInAllowedZone(EntityPlayer player) {
        BlockPos playerPos = player.getPosition();
        JsonObject zonesRayonJson = zoneRayonHandler.getZonesJson();
        for (Map.Entry<String, JsonElement> entry : zonesRayonJson.entrySet()) {
            JsonObject zoneData = entry.getValue().getAsJsonObject();
            float zoneX = zoneData.get("x").getAsFloat();
            float zoneY = zoneData.get("y").getAsFloat();
            float zoneZ = zoneData.get("z").getAsFloat();
            float rayon = zoneData.get("rayon").getAsFloat();
            double distanceSquared = playerPos.distanceSq(zoneX + 0.5, zoneY + 0.5, zoneZ + 0.5);
            if (distanceSquared <= rayon * rayon) {
                return true;
            }
        }
        return false;
    }

    private List<String> getZoneNames() {
        List<String> zoneNames = new ArrayList<>();
        JsonObject zonesJson = zoneHandler.getZonesJson();
        for (Map.Entry<String, JsonElement> entry : zonesJson.entrySet()) {
            zoneNames.add(entry.getKey());
        }
        return zoneNames;
    }

    @Override
    public String getName() {
        return "rtp";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/rtp";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return new ArrayList<>();
    }
}
