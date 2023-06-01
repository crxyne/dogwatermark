package org.crayne.dogwatermark.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import net.minecraft.client.Minecraft;
import org.crayne.dogwatermark.DogwaterMarkMain;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings("ResultOfMethodCallIgnored")
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

    private static class DogwaterJson {

        private final String alignment;
        private final String text;
        private final String colorHex;
        private final boolean shadow;

        public DogwaterJson(@NotNull final String alignment, @NotNull final String text, @NotNull final String colorHex, final boolean shadow) {
            this.alignment = alignment;
            this.text = text;
            this.colorHex = colorHex;
            this.shadow = shadow;
        }

        public DogwaterJson(@NotNull final DogwaterSettings settings) {
            this(settings.watermarkAlignment.name(), settings.watermarkText, "#"
                    + Integer.toHexString(settings.watermarkColor.getRGB()).substring(2), settings.watermarkShadow);
        }

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

    private static final File dogwatermarkFile = new File(Minecraft.getMinecraft().mcDataDir, "dogwatermark.json");

    private void load(@NotNull final DogwaterJson json) {
        watermarkAlignment = Arrays.stream(WatermarkAlignment.values()).filter(s -> s.name().equals(json.alignment)).findAny().orElse(WatermarkAlignment.TOP_LEFT);
        watermarkText = json.text;
        watermarkColor = Color.decode(json.colorHex);
        watermarkShadow = json.shadow;
    }

    public void loadDogwaterMark() throws IOException {
        if (!dogwatermarkFile.isFile()) return;
        final Gson gson = new Gson();
        final JsonReader reader = new JsonReader(new FileReader(dogwatermarkFile));
        final DogwaterJson json = gson.fromJson(reader, DogwaterJson.class);
        load(json);
    }

    public void saveDogwaterMark() throws IOException {
        if (!dogwatermarkFile.isFile() && dogwatermarkFile.exists()) return;
        final Gson gson = new Gson();
        final DogwaterJson json = new DogwaterJson(this);
        final FileWriter fw = new FileWriter(dogwatermarkFile);
        gson.toJson(json, fw);
        fw.flush();
        fw.close();
    }

    public void attemptSave() {
        try {
            saveDogwaterMark();
        } catch (IOException e) {
            DogwaterMarkMain.logger().error("Could not save dogwatermark to json file");
            e.printStackTrace();
        }
    }

    public void attemptLoad() {
        try {
            loadDogwaterMark();
        } catch (final IOException e) {
            DogwaterMarkMain.logger().error("Could not load dogwatermark json file");
            e.printStackTrace();
        }
    }

}
