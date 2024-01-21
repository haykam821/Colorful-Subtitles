package io.github.haykam821.colorfulsubtitles.config;

import java.util.Optional;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

public class SubtitleColor {
	protected static final SubtitleColor DEFAULT = SubtitleColor.ofText(TextColor.fromRgb(0xFFFFFF));

	private static final Codec<SubtitleColor> RECORD_CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			TextColor.CODEC.fieldOf("text").forGetter(color -> color.text),
			TextColor.CODEC.optionalFieldOf("background").forGetter(color -> color.background)
		).apply(instance, SubtitleColor::new);
	});

	private static final Codec<SubtitleColor> SIMPLE_CODEC = TextColor.CODEC.xmap(SubtitleColor::ofText, color -> color.text);

	public static final Codec<SubtitleColor> CODEC = Codec.either(RECORD_CODEC, SIMPLE_CODEC).xmap(either -> {
		return either.map(left -> left, right -> right);
	}, color -> {
		return color.background.isEmpty() ? Either.right(color) : Either.left(color);
	});

	private final TextColor text;
	private final Optional<TextColor> background;

	private SubtitleColor(TextColor text, Optional<TextColor> background) {
		this.text = text;
		this.background = background;
	}

	public TextColor getText() {
		return this.text;
	}

	public Optional<TextColor> getBackground() {
		return this.background;
	}

	@Override
	public String toString() {
		return "SubtitleColor{text=" + this.text + ", background=" + this.background + "}";
	}

	public static SubtitleColor ofText(TextColor text) {
		return new SubtitleColor(text, Optional.empty());
	}

	public static SubtitleColor ofText(Formatting formatting) {
		return SubtitleColor.ofText(TextColor.fromFormatting(formatting));
	}
}
