package org.crayne.dogwatermark.util;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

public enum WatermarkAlignment {

    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT;

    @NotNull
    public static Optional<WatermarkAlignment> ofName(@NotNull final String name) {
        return Arrays.stream(values()).filter(w -> w.name().equalsIgnoreCase(name)).findAny();
    }

}
