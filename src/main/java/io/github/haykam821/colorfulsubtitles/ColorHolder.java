package io.github.haykam821.colorfulsubtitles;

import io.github.haykam821.colorfulsubtitles.config.ColorfulSubtitlesConfig;
import io.github.haykam821.colorfulsubtitles.config.SubtitleColor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.text.TextColor;

@Environment(EnvType.CLIENT)
public interface ColorHolder {
	public int getTextColor();
	public void setTextColor(int color);

	public int getBackgroundColor();
	public void setBackgroundColor(int color);

	public default void setColor(SoundInstance sound) {
		ColorfulSubtitlesConfig config = ColorfulSubtitles.getConfig();
		SubtitleColor color = config.getColorForCategory(sound.getCategory());

		this.setTextColor(color.getText().getRgb());

		if (color.getBackground().isPresent()) {
			this.setBackgroundColor(color.getBackground().get().getRgb());
		} else {
			SubtitleColor defaultColor = config.getDefaultColor();
			this.setBackgroundColor(defaultColor.getBackground().map(TextColor::getRgb).orElse(-1));
		}
	}
}
