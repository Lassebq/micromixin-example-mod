package io.github.lassebq.examplemod.mixin;

import java.io.File;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class ExampleMixin {
    @Shadow
    public static File getRunDirectory() { return null; }

    @Inject(
        target = @Desc(
            owner = Minecraft.class,
            value = "init"
        ),
        at = @At("HEAD")
    )
    public void init(CallbackInfo ci) {
        System.out.println("Hello World!");
        System.out.println("We are running in " + getRunDirectory().getAbsolutePath());
    }
}
