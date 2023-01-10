package rpg;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
    public ArrayList<String> characterList;

    public Generator() {
        this.characterList = new ArrayList<>();
    }

    public void addToList(@org.jetbrains.annotations.NotNull String charactersToAdd) {
        for (int i = 0; i < charactersToAdd.length(); i++) {
            this.characterList.add(String.valueOf(charactersToAdd.charAt(i)));
        }
    }

    public void clearList() {
        this.characterList.clear();
    }

    public String generatePassword(int length) {
        if (this.characterList.size() > 0) {

            StringBuilder password = new StringBuilder();
            int i = 0;
            while (i < length) {
                Random rand = new Random();
                int size = this.characterList.size();
                int characterToAdd = rand.nextInt(size);
                password.append(characterList.get(characterToAdd));
                i++;
            }
            return password.toString();
        }
        return "";
    }
}
