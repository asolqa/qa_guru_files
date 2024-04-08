package datamodel;

import java.util.ArrayList;
import java.util.List;

public class Order {
    String id;

    String type;

    String name;

    Double ppu;

    List<Batter> batters = new ArrayList<>();

    List<Topping> toppings = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPpu() {
        return ppu;
    }

    public void setPpu(Double ppu) {
        this.ppu = ppu;
    }

    public List<Batter> getBatters() {
        return batters;
    }

    public void setBatters(List<Batter> batters) {
        this.batters = batters;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", ppu=" + ppu +
                ", batters=" + batters +
                ", toppings=" + toppings +
                '}';
    }
}
