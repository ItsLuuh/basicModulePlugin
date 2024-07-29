package net.luuh.test.constants;

public class Palette {

    private final String name;
    private final String color;

    protected Palette(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
