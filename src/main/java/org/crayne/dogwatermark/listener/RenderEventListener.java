package org.crayne.dogwatermark.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.crayne.dogwatermark.util.DogwaterSettings;
import org.jetbrains.annotations.NotNull;

public class RenderEventListener {

    private final DogwaterSettings settings;

    public RenderEventListener(@NotNull final DogwaterSettings settings) {
        this.settings = settings;
    }

    @NotNull
    private Pair<Float, Float> coordsUsingAlignment(@NotNull final ScaledResolution resolution) {
        final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        final String text = settings.watermarkText();
        final float x, y;

        switch (settings.watermarkAlignment()) {
            default:
                x = 0.0f;
                y = 0.0f;
                break;
            case TOP_RIGHT:
                x = resolution.getScaledWidth() - fontRenderer.getStringWidth(text);
                y = 0.0f;
                break;
            case BOTTOM_LEFT:
                x = 0.0f;
                y = resolution.getScaledHeight() - fontRenderer.FONT_HEIGHT;
                break;
            case BOTTOM_RIGHT:
                x = resolution.getScaledWidth() - fontRenderer.getStringWidth(text);
                y = resolution.getScaledHeight() - fontRenderer.FONT_HEIGHT;
                break;
        }
        //noinspection RedundantSuppression
        //noinspection SuspiciousNameCombination what the fuck intellij?
        return Pair.of(x, y);
    }

    @SubscribeEvent
    public void renderEvent(@NotNull final RenderGameOverlayEvent ev) {
        final Pair<Float, Float> xy = coordsUsingAlignment(ev.getResolution());

        Minecraft.getMinecraft().fontRenderer.drawString(settings.watermarkText(), xy.getLeft(), xy.getRight(), settings.watermarkColor().getRGB(), settings.watermarkShadow());
    }

}
