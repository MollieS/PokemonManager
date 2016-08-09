package pokemonmanager;

import java.util.ArrayList;
import java.util.List;

public interface Pokemon {

    String getName();

    String getHeight();

    List<String> getAbilities();

    void addAbility(String ability);

    public static final Pokemon NULL = new Pokemon() {

        public String getName() {
            return "This pokemon does not exist";
        }

        public String getHeight() {
            return new String();
        }

        public List<String> getAbilities() {
            return new ArrayList<>();
        }

        public void addAbility(String ability) {
        }
    };
}
