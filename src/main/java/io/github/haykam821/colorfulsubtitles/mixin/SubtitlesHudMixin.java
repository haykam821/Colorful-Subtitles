package io.github.haykam821.colorfulsubtitles.mixin;

import java.util.Iterator;
import java.util.List;

import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import io.github.haykam821.colorfulsubtitles.ColorHolder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.gui.hud.SubtitlesHud.SubtitleEntry;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.text.Text;

@Mixin(SubtitlesHud.class)
@Environment(EnvType.CLIENT)
public class SubtitlesHudMixin {
	@Unique
	private ColorHolder iterationEntry;

	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;", ordinal = 1))
	private Object updateIterationEntry(Iterator<Object> iterator) {
		return this.iterationEntry = (ColorHolder) iterator.next();
	}

	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I"), index = 4)
	private int modifyDrawColor(int color) {
		return ColorHelper.Argb.mixColor(color, this.iterationEntry.getColor());
	}

	@Inject(method = "onSoundPlayed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SubtitlesHud$SubtitleEntry;reset(Lnet/minecraft/util/math/Vec3d;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void resetColor(SoundInstance sound, WeightedSoundSet soundSet, CallbackInfo ci, Text text, Iterator<SubtitleEntry> iterator, SubtitleEntry entry) {
		((ColorHolder) entry).setColor(sound);
	}

	@Redirect(method = "onSoundPlayed", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private boolean setColor(List<Object> entries, Object entry, SoundInstance sound, WeightedSoundSet soundSet) {
		((ColorHolder) entry).setColor(sound);
		return entries.add(entry);
	}

}
