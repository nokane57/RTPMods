package fr.hcu.rtp.commands;

import fr.hcu.rtp.folder.ZoneRayonHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.server.permission.PermissionAPI;

import javax.annotation.Nullable;
import java.util.List;

public class SetZoneRayonCommand extends CommandBase {

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (!(sender instanceof EntityPlayer)) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Seul un joueur peut executer cette command !"));
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;
        if (!PermissionAPI.hasPermission(player, "hcu.rtp.admin")) {
            player.sendMessage(new TextComponentString(TextFormatting.RED + "Vous n'avez pas la permission d'utiliser cette command !"));
            return;
        }

        if (args.length != 2) {
            throw new WrongUsageException(getUsage(player));
        }

        String zonRayonName = args[0];
        float rayon = Float.parseFloat(args[1]);
        BlockPos playerPos = player.getPosition();
        ZoneRayonHandler zoneRayonHandler = new ZoneRayonHandler();
        zoneRayonHandler.saveZone(zonRayonName, playerPos.getX(), playerPos.getY(), playerPos.getZ(), rayon);
        player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Zone " + TextFormatting.AQUA + zonRayonName + TextFormatting.GREEN + " créée avec succès!"));
    }

    @Override
    public String getName() {
        return "setzonerayon";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/setzonerayon <nom de la zone> <rayon>";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
