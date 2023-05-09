package org.crayne.dogwatermark.util;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DogwaterSettings {

    @NotNull
    private WatermarkAlignment watermarkAlignment;

    @NotNull
    private String watermarkText;

    @NotNull
    private Color watermarkColor;

    private boolean watermarkShadow;

    public DogwaterSettings() {
        watermarkAlignment = WatermarkAlignment.TOP_LEFT;
        watermarkText = "use '/dwm text <text>' to change this";
        watermarkColor = Color.WHITE;
        watermarkShadow = true;
    }

    public boolean watermarkShadow() {
        return watermarkShadow;
    }

    @NotNull
    public Color watermarkColor() {
        return watermarkColor;
    }

    @NotNull
    public String watermarkText() {
        return watermarkText;
    }

    public void watermarkAlignment(@NotNull final WatermarkAlignment watermarkAlignment) {
        this.watermarkAlignment = watermarkAlignment;
    }

    public void watermarkColor(@NotNull final Color watermarkColor) {
        this.watermarkColor = watermarkColor;
    }

    public void watermarkShadow(final boolean watermarkShadow) {
        this.watermarkShadow = watermarkShadow;
    }

    public void watermarkText(@NotNull final String watermarkText) {
        this.watermarkText = watermarkText;
    }

    @NotNull
    public WatermarkAlignment watermarkAlignment() {
        return watermarkAlignment;
    }

}
