package org.crayne.dogwatermark;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.crayne.dogwatermark.listener.CommandEventListener;
import org.crayne.dogwatermark.listener.RenderEventListener;
import org.crayne.dogwatermark.util.DogwaterSettings;
import org.jetbrains.annotations.NotNull;

@Mod(modid = DogwaterMarkMain.modId)
public class DogwaterMarkMain {

    @NotNull
    public static final String modId = "dogwatermark";

    @NotNull
    private static final Logger logger = LogManager.getLogger();

    @NotNull
    private static final DogwaterSettings settings = new DogwaterSettings();

    @Mod.EventHandler
    public void init(@NotNull final FMLInitializationEvent event) {
        logger.info("Initializing DogwaterMark...");

        MinecraftForge.EVENT_BUS.register(new RenderEventListener(settings));
        ClientCommandHandler.instance.registerCommand(new CommandEventListener(settings));
    }

}
