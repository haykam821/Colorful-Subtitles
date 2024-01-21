package io.github.haykam821.colorfulsubtitles.config;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;

import net.minecraft.sound.SoundCategory;

public final class ColorfulSubtitlesCodecs {
	private static final SoundCategory[] SOUND_CATEGORIES = SoundCategory.values();

	private static final Codec<SoundCategory> SOUND_CATEGORY = Codec.STRING.comapFlatMap(ColorfulSubtitlesCodecs::getSoundCategory, SoundCategory::getName);

	private static final Keyable SOUND_CATEGORY_KEYS = new Keyable() {
		@Override
		public <T> Stream<T> keys(DynamicOps<T> ops) {
			return Arrays.stream(SOUND_CATEGORIES)
				.map(SoundCategory::getName)
				.map(ops::createString);
		}
	};

	protected static final Codec<Map<SoundCategory, SubtitleColor>> SOUND_CATEGORY_TO_SUBTITLE_COLOR = Codec.simpleMap(SOUND_CATEGORY, SubtitleColor.CODEC, SOUND_CATEGORY_KEYS).codec();

	private ColorfulSubtitlesCodecs() {
		return;
	}

	private static DataResult<SoundCategory> getSoundCategory(String name) {
		for (SoundCategory category : SOUND_CATEGORIES) {
			if (category.getName().equals(name)) {
				return DataResult.success(category);
			}
		}

		return DataResult.error(() -> "Unknown sound category '" + name + "'");
	}
}
