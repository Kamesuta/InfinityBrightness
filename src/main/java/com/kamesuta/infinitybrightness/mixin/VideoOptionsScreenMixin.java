package com.kamesuta.infinitybrightness.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.DoubleOptionSliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(VideoOptionsScreen.class)
public class VideoOptionsScreenMixin extends Screen {
    private Optional<DoubleOptionSliderWidget> gammaOption;
    private int gammaOptionWidth;

    protected VideoOptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At(value = "TAIL"))
    public void init(CallbackInfo ci) {
        gammaOption = children().stream()
                .filter(e -> e instanceof ButtonListWidget)
                .map(ButtonListWidget.class::cast)
                .flatMap(e -> e.children().stream())
                .flatMap(f -> f.children().stream())
                .filter(e -> e instanceof DoubleOptionSliderWidget)
                .map(DoubleOptionSliderWidget.class::cast)
                .filter(e -> "options.gamma".equals(((TranslatableText) e.option.key).getKey()))
                .findFirst();
        gammaOptionWidth = gammaOption.map(ClickableWidget::getWidth).orElse(0);
    }

    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        gammaOption.ifPresent(widget -> {
            if (widget.isMouseOver(mouseX, mouseY)) {
                ci.cancel();
                widget.setWidth(gammaOptionWidth * 2 + 10);
                widget.render(matrices, mouseX, mouseY, delta);
            } else {
                widget.setWidth(gammaOptionWidth);
            }
        });
    }

//    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/option/OptionsScreen;renderBackground(Lnet/minecraft/client/util/math/MatrixStack;)V"))
//    public void render(OptionsScreen instance, MatrixStack matrixStack) {
//        instance.renderb
//    }
}
