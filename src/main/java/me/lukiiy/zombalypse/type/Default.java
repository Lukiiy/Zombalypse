package me.lukiiy.zombalypse.type;

import me.lukiiy.zombalypse.CustomType;

public class Default implements CustomType {
    @Override
    public String getId() {
        return "default";
    }

    @Override
    public String getName() {
        return "";
    }
}
