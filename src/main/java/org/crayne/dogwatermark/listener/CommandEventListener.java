package org.crayne.dogwatermark.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.crayne.dogwatermark.DogwaterMarkMain;
import org.crayne.dogwatermark.util.DogwaterSettings;
import org.crayne.dogwatermark.util.WatermarkAlignment;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class CommandEventListener {

    private final DogwaterSettings settings;

    public CommandEventListener(@NotNull final DogwaterSettings settings) {
        this.settings = settings;
    }

    @SubscribeEvent
    public void chatEvent(@NotNull final ClientChatEvent ev) {
        final String message = ev.getMessage();
        if (!message.startsWith(")")) return;

        final String command = message.substring(1).trim();
        final String[] args = Arrays.stream(command.split(" ")).map(String::trim).toArray(String[]::new);
        ev.setCanceled(true);
        execute(args);
    }

    private static void message(@NotNull final String msg) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(msg));
    }

    private static void unrecognizedUsage(@NotNull final String @NotNull [] args, @NotNull final String param) {
        message("§cExpected a parameter after the selected mode. Usage: )" + args[0] + " <" + param + ">.");
    }

    private static void success(@NotNull final String mode) {
        message("§bSuccessfully changed watermark " + mode + ".");
    }

    private boolean commandChangeText(@NotNull final String @NotNull [] args) {
        final String text = String.join(" ", Arrays.asList(args).subList(1, args.length));
        settings.watermarkText(text);
        settings.attemptSave();
        success("text");
        return true;
    }

    private boolean commandChangeColor(@NotNull final String @NotNull [] args) {
        if (args.length != 2) {
            unrecognizedUsage(args, "color-hex-code");
            return false;
        }
        final Color color;
        try {
            color = Color.decode(args[1]);
        } catch (final NumberFormatException e) {
            message("§cCould not parse color hex code: " + args[1] + ". Example of a correct hex color code: #FFFFFF (in this case, white)");
            return false;
        }
        settings.watermarkColor(color);
        settings.attemptSave();
        success("color");
        return true;
    }

    private boolean commandChangeAlignment(@NotNull final String @NotNull [] args) {
        if (args.length != 2) {
            unrecognizedUsage(args, "alignment (possible are: top_right, bottom_right, top_left, bottom_left)");
            return false;
        }
        final Optional<WatermarkAlignment> alignment = WatermarkAlignment.ofName(args[1]);
        if (!alignment.isPresent()) {
            message("§cUnknown alignment: " + args[1] + ". Possible are: top_right, bottom_right, top_left, bottom_left");
            return false;
        }
        settings.watermarkAlignment(alignment.get());
        settings.attemptSave();
        success("alignment");
        return true;
    }

    private boolean commandChangeShadowMode(@NotNull final String @NotNull [] args) {
        if (args.length != 2) {
            unrecognizedUsage(args, "enabled (true / false)");
            return false;
        }
        final boolean enable = Boolean.parseBoolean(args[1]);
        settings.watermarkShadow(enable);
        settings.attemptSave();
        success("shadow mode");
        return true;
    }

    private static void commandUnknownMode(@NotNull final String @NotNull [] args) {
        message("§cUnknown command mode: " + args[0] + ". Use the prefix ) along with any of the following arguments: text, color, align, shadow.");
    }

    public void execute(@NotNull final String @NotNull [] args) {
        if (args.length == 0) {
            message("§cNo arguments provided. Use the prefix ) along with any of the following arguments: text, color, align, shadow.");
            return;
        }
        final boolean success;
        switch (args[0].toLowerCase()) {
            case "text":
                success = commandChangeText(args);
                break;
            case "color":
                success = commandChangeColor(args);
                break;
            case "align":
                success = commandChangeAlignment(args);
                break;
            case "shadow":
                success = commandChangeShadowMode(args);
                break;
            default:
                commandUnknownMode(args);
                return;
        }
        if (!success) return;

    }

}
