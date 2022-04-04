package io.github.moehreag.axolotlclient.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class DoubleOption extends OptionBase implements Option{

    private double option;
    private final double Default;
    private final double min;
    private final double max;

    public DoubleOption(String name, double Default, double min, double max) {
        super(name);
        this.Default=Default;
        this.min=min;
        this.max=max;
    }

    public double get(){
        return option;
    }

    public void set(double set){
        option=set;
    }

    public double getMin(){return min;}
    public double getMax(){return max;}

    @Override
    public OptionType getType() {
        return OptionType.DOUBLE;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setDefaults(){
        option = Default;
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        option = element.getAsDouble();
    }

    @Override
    public JsonElement getJson() {
        return new JsonPrimitive(option);
    }
}