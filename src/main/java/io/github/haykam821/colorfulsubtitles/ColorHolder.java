package io.github.haykam821.colorfulsubtitles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Formatting;

@Environment(EnvType.CLIENT)
public interface ColorHolder {
	public int getColor();
	public void setColor(int color);

	public default void setColor(SoundInstance sound) {
		this.setColor(ColorHolder.getColorForCategory(sound.getCategory()).getColorValue());
	}

	public static Formatting getColorForCategory(SoundCategory category) {
		switch (category) {
			case MUSIC: return Formatting.DARK_PURPLE;
			case RECORDS: return Formatting.DARK_RED;
			case WEATHER: return Formatting.AQUA;
			case BLOCKS: return Formatting.GREEN;
			case HOSTILE: return Formatting.RED;
			case NEUTRAL: return Formatting.YELLOW;
			case PLAYERS: return Formatting.GOLD;
			case AMBIENT: return Formatting.GRAY;
			case VOICE: return Formatting.LIGHT_PURPLE;
			default: return Formatting.WHITE;
		}
	}
}
