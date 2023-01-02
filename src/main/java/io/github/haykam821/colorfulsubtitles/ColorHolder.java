package io.github.haykam821.colorfulsubtitles;

import io.github.haykam821.colorfulsubtitles.config.ColorfulSubtitlesConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundInstance;

@Environment(EnvType.CLIENT)
public interface ColorHolder {
	public int getColor();
	public void setColor(int color);

	public default void setColor(SoundInstance sound) {
		ColorfulSubtitlesConfig config = ColorfulSubtitles.getConfig();
		this.setColor(config.getColorForCategory(sound.getCategory()).getRgb());
	}
}
