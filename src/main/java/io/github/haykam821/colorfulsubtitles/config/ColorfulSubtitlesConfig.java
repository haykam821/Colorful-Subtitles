package io.github.haykam821.colorfulsubtitles.config;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Formatting;

public class ColorfulSubtitlesConfig {
	private static final Map<SoundCategory, SubtitleColor> DEFAULT_COLORS = ImmutableMap.<SoundCategory, SubtitleColor>builder()
		.put(SoundCategory.MUSIC, SubtitleColor.ofText(Formatting.DARK_PURPLE))
		.put(SoundCategory.RECORDS, SubtitleColor.ofText(Formatting.DARK_RED))
		.put(SoundCategory.WEATHER, SubtitleColor.ofText(Formatting.AQUA))
		.put(SoundCategory.BLOCKS, SubtitleColor.ofText(Formatting.GREEN))
		.put(SoundCategory.HOSTILE, SubtitleColor.ofText(Formatting.RED))
		.put(SoundCategory.NEUTRAL, SubtitleColor.ofText(Formatting.YELLOW))
		.put(SoundCategory.PLAYERS, SubtitleColor.ofText(Formatting.GOLD))
		.put(SoundCategory.AMBIENT, SubtitleColor.ofText(Formatting.GRAY))
		.put(SoundCategory.VOICE, SubtitleColor.ofText(Formatting.LIGHT_PURPLE))
		.build();

	public static final ColorfulSubtitlesConfig DEFAULT = new ColorfulSubtitlesConfig(DEFAULT_COLORS, SubtitleColor.DEFAULT);

	public static final Codec<ColorfulSubtitlesConfig> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			ColorfulSubtitlesCodecs.SOUND_CATEGORY_TO_SUBTITLE_COLOR.optionalFieldOf("colors", DEFAULT_COLORS).forGetter(config -> config.colors),
			SubtitleColor.CODEC.optionalFieldOf("default_color", SubtitleColor.DEFAULT).forGetter(config -> config.defaultColor)
		).apply(instance, ColorfulSubtitlesConfig::new);
	});

	private final Map<SoundCategory, SubtitleColor> colors;
	private final SubtitleColor defaultColor;

	private ColorfulSubtitlesConfig(Map<SoundCategory, SubtitleColor> colors, SubtitleColor defaultColor) {
		this.colors = colors;
		this.defaultColor = defaultColor;
	}

	public SubtitleColor getColorForCategory(SoundCategory category) {
		SubtitleColor color = this.colors.get(category);
		return color == null ? this.defaultColor : color;
	}

	public SubtitleColor getDefaultColor() {
		return this.defaultColor;
	}
}
