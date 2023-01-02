package io.github.haykam821.colorfulsubtitles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import io.github.haykam821.colorfulsubtitles.config.ColorfulSubtitlesConfig;
import net.fabricmc.loader.api.FabricLoader;

public final class ColorfulSubtitles {
	private static final String MOD_ID = "colorfulsubtitles";
	public static final Logger LOGGER = LoggerFactory.getLogger("Colorful Subtitles");

	private static ColorfulSubtitlesConfig config;

	private ColorfulSubtitles() {
		return;
	}

	public static ColorfulSubtitlesConfig getConfig() {
		if (config == null) {
			config = ColorfulSubtitles.loadConfig();
		}

		return config;
	}

	private static ColorfulSubtitlesConfig loadConfig() {
		Path configDir = FabricLoader.getInstance().getConfigDir();
		File file = new File(configDir.toFile(), MOD_ID + ".json");

		try (Reader reader = new BufferedReader(new FileReader(file))) {
			JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
			DataResult<Pair<ColorfulSubtitlesConfig, JsonElement>> result = ColorfulSubtitlesConfig.CODEC.decode(JsonOps.INSTANCE, json);

			return result.get().left().get().getFirst();
		} catch (FileNotFoundException exception) {
			try (Writer writer = new BufferedWriter(new FileWriter(file))) {
				DataResult<JsonElement> result = ColorfulSubtitlesConfig.CODEC.encodeStart(JsonOps.INSTANCE, ColorfulSubtitlesConfig.DEFAULT);
				new Gson().toJson(result.get().left().get(), writer);

				LOGGER.warn("Could not find Colorful Subtitles config; wrote default to file");
			} catch (Exception writeException) {
				LOGGER.warn("Could not find Colorful Subtitles config; failed to write default to file", writeException);
			}
		} catch (Exception exception) {
			LOGGER.warn("Failed to read Colorful Subtitles config; falling back to default", exception);
		}

		return ColorfulSubtitlesConfig.DEFAULT;
	}
}