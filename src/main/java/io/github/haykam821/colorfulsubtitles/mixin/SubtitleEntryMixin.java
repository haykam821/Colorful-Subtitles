package io.github.haykam821.colorfulsubtitles.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import io.github.haykam821.colorfulsubtitles.ColorHolder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.SubtitlesHud;

@Mixin(SubtitlesHud.SubtitleEntry.class)
@Environment(EnvType.CLIENT)
public class SubtitleEntryMixin implements ColorHolder {
	@Unique
	private int textColor = 0;

	@Unique
	private int backgroundColor = -1;

	@Override
	public int getTextColor() {
		return this.textColor;
	}

	@Override
	public void setTextColor(int color) {
		this.textColor = color;
	}

	@Override
	public int getBackgroundColor() {
		return this.backgroundColor;
	}

	@Override
	public void setBackgroundColor(int color) {
		this.backgroundColor = color;
	}
}
