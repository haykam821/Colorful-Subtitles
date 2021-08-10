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
	private int color = 0;

	@Override
	public int getColor() {
		return this.color;
	}

	@Override
	public void setColor(int color) {
		this.color = color;
	}
}
