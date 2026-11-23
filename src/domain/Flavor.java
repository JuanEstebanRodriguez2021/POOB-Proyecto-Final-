package domain;

/**
 * Sabores de los helados
 * 
 * Sara Gonzalez
 * 
 * @version (a version number or a date)
 */
public class Flavor {
    private String name;

    public Flavor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Sabores
    public static final Flavor STRAWBERRY = new Flavor("Fresa");
    public static final Flavor VANILLA = new Flavor("Vainilla");
    public static final Flavor CHOCOLATE = new Flavor("Chocolate");
}


 

