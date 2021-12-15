package com.kamesuta.infinitybrightness.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.Option;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class InfinityBrightnessClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Option.GAMMA.min = -10;
        Option.GAMMA.setMax(10);
        Option.GAMMA.getter = (gameOptions) -> {
            double sign = gameOptions.gamma > 0 ? 1 : -1;
            double gamma = Math.abs(gameOptions.gamma);
            return sign * Math.pow(gamma * 10, 1.0 / 3.0);
        };
        Option.GAMMA.setter = (gameOptions, gamma) -> {
            gameOptions.gamma = Math.pow(gamma, 3) / 10;
        };
        Option.GAMMA.displayStringGetter = (gameOptions, option) -> {
            double d = gameOptions.gamma;
            Text text = option.getPercentAdditionLabel((int) (d * 100.0D));
            return d > 0 ? text
                    : new LiteralText(text.getString().replace("+", ""));
        };
    }
}
