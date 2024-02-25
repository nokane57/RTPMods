package fr.hcu.rtp;

import fr.hcu.rtp.commands.SetZoneCommand;
import fr.hcu.rtp.commands.SetZoneRayonCommand;
import fr.hcu.rtp.utils.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;


@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION)
public class Main {

    @Mod.Instance
    public static Main instance;
    public static Logger logger;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
    }

    @Mod.EventHandler
    public static void Init(FMLInitializationEvent e) {

    }

    @Mod.EventHandler
    public static void PostInit(FMLPostInitializationEvent e) {

    }

    @SideOnly(Side.SERVER)
    @Mod.EventHandler
    public static void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new SetZoneRayonCommand());
        event.registerServerCommand(new SetZoneCommand());
    }

    // GETTER
    public static Main getInstance() {return instance;}
    public static Logger getLogger() {return logger;}
}
