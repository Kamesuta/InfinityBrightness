package com.kamesuta.infinitybrightness;

import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.DoubleOptionSliderWidget;

public record ButtonAndSlider(ButtonListWidget.ButtonEntry button, DoubleOptionSliderWidget widget) {
}
