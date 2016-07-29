package pkmncore;

public interface Pokemon {

    String getName();

    String getHeight();

    String[] getAbilities();

    public static final Pokemon NULL = new Pokemon() {

        public String getName() {
            return "This pokemon does not exist";
        }

        public String getHeight() {
            return new String();
        }

        public String[] getAbilities() {
            return new String[0];
        }
    };
}
