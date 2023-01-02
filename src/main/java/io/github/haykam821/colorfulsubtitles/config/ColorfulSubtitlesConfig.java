package io.github.haykam821.colorfulsubtitles.config;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.sound.SoundCategory;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

public class ColorfulSubtitlesConfig {
	private static final Map<SoundCategory, TextColor> DEFAULT_COLORS = ImmutableMap.<SoundCategory, TextColor>builder()
		.put(SoundCategory.MUSIC, TextColor.fromFormatting(Formatting.DARK_PURPLE))
		.put(SoundCategory.RECORDS, TextColor.fromFormatting(Formatting.DARK_RED))
		.put(SoundCategory.WEATHER, TextColor.fromFormatting(Formatting.AQUA))
		.put(SoundCategory.BLOCKS, TextColor.fromFormatting(Formatting.GREEN))
		.put(SoundCategory.HOSTILE, TextColor.fromFormatting(Formatting.RED))
		.put(SoundCategory.NEUTRAL, TextColor.fromFormatting(Formatting.YELLOW))
		.put(SoundCategory.PLAYERS, TextColor.fromFormatting(Formatting.GOLD))
		.put(SoundCategory.AMBIENT, TextColor.fromFormatting(Formatting.GRAY))
		.put(SoundCategory.VOICE, TextColor.fromFormatting(Formatting.LIGHT_PURPLE))
		.build();

	private static final TextColor DEFAULT_DEFAULT_COLOR = TextColor.fromFormatting(Formatting.WHITE);

	public static final ColorfulSubtitlesConfig DEFAULT = new ColorfulSubtitlesConfig(DEFAULT_COLORS, DEFAULT_DEFAULT_COLOR);

	public static final Codec<ColorfulSubtitlesConfig> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			ColorfulSubtitlesCodecs.SOUND_CATEGORY_TO_TEXT_COLOR.optionalFieldOf("colors", DEFAULT_COLORS).forGetter(config -> config.colors),
			TextColor.CODEC.optionalFieldOf("default_color", DEFAULT_DEFAULT_COLOR).forGetter(config -> config.defaultColor)
		).apply(instance, ColorfulSubtitlesConfig::new);
	});

	private final Map<SoundCategory, TextColor> colors;
	private final TextColor defaultColor;

	private ColorfulSubtitlesConfig(Map<SoundCategory, TextColor> colors, TextColor defaultColor) {
		this.colors = colors;
		this.defaultColor = defaultColor;
	}

	public TextColor getColorForCategory(SoundCategory category) {
		TextColor color = this.colors.get(category);
		return color == null ? this.defaultColor : color;
	}
}
