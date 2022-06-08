package de.oliver.bierdb.entities;

public enum DrinkType {

    BEER (1, "Bier"),
    RADLER (2, "Radler");

    private final int id;
    private final String name;

    DrinkType(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static DrinkType getById(int id){
        for (DrinkType drinkType : values()) {
            if(drinkType.id == id){
                return drinkType;
            }
        }

        return null;
    }
}
