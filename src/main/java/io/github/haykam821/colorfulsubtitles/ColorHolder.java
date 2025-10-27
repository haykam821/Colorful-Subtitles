package io.github.haykam821.colorfulsubtitles;

import io.github.haykam821.colorfulsubtitles.config.ColorfulSubtitlesConfig;
import io.github.haykam821.colorfulsubtitles.config.SubtitleColor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.ColorHelper;

@Environment(EnvType.CLIENT)
public interface ColorHolder {
	int getTextColor();
	void setTextColor(int color);

	int getBackgroundColor();
	void setBackgroundColor(int color);

	default void setColor(SoundInstance sound) {
		ColorfulSubtitlesConfig config = ColorfulSubtitles.getConfig();
		SubtitleColor color = config.getColorForCategory(sound.getCategory());

		this.setTextColor(ColorHelper.fullAlpha(color.getText().getRgb()));

		if (color.getBackground().isPresent()) {
			this.setBackgroundColor(color.getBackground().get().getRgb());
		} else {
			SubtitleColor defaultColor = config.getDefaultColor();
			this.setBackgroundColor(defaultColor.getBackground().map(TextColor::getRgb).orElse(-1));
		}
	}
}
