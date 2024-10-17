package com.examplemod.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
	@Redirect(target = @Desc(
		owner = TitleScreen.class,
		value = "init"
	),
	slice = @Slice(
		from = @At(value = "CONSTANT", args = "stringValue=Options...")
	),
	require = 0,
	at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private boolean addQuitButton(List<ButtonWidget> list, Object obj) {
		if(obj instanceof ButtonWidget && ((ButtonWidget)obj).message.equals("Options...")) {
			ButtonWidget button = (ButtonWidget)obj;
			list.add(new ButtonWidget(button.id, button.x, button.y, 98, 20, button.message));
			list.add(new ButtonWidget(4, button.x + 102, button.y, 98, 20, "Quit game"));
			return true;
		}
		list.add((ButtonWidget)obj);
		return true;
	}

	@Inject(
	target = @Desc(
		owner = TitleScreen.class,
		value = "buttonClicked",
		args = {ButtonWidget.class}
	),
	// method = "buttonClicked(Lnet/minecraft/client/gui/widget/ButtonWidget;)V",
	require = 0, at = @At(value = "TAIL"))
	private void handleQuitButton(ButtonWidget button, CallbackInfo ci) {
		if(button.id == 4) {
			this.minecraft.stop();
		}
	}

}
