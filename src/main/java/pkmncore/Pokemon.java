package pkmncore;

public interface Pokemon {

    String getName();

    int getHeight();

    String[] getAbilities();

    public static final Pokemon NULL = new Pokemon() {

        public String getName() {
            return "This pokemon does not exist";
        }

        public int getHeight() {
            return 0;
        }

        public String[] getAbilities() {
            return new String[0];
        }
    };
}
