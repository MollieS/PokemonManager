package pkmncore;

public class Pokemon {

    private String name;
    private int height;
    private String ability;

    public Pokemon(String name, int height, String ability) {
        this.name = name;
        this.height = height;
        this.ability = ability;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public String getAbility() {
        return ability;
    }
}
