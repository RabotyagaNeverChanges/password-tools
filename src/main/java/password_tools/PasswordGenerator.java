package password_tools;

import password_tools.exceptions.IllegalCharacterTypeException;
import password_tools.exceptions.IllegalPasswordSizeException;

import java.util.ArrayList;
import java.util.List;

public class PasswordGenerator {
    private  final int amountOfMandatoryCharacters = 4;
    private final int minPasswordLength = 4;


    public String generatePassword(int length) throws IllegalPasswordSizeException {
        if (length < minPasswordLength) {
            throw new IllegalPasswordSizeException("Illegal password size given");
        }

        StringBuilder password = new StringBuilder(length);
        try {
            password.append(generateCharacter(CharacterType.LOWER));
            password.append(generateCharacter(CharacterType.UPPER));
            password.append(generateCharacter(CharacterType.DIGIT));
            password.append(generateCharacter(CharacterType.SPECIAL));
        } catch (IllegalCharacterTypeException e) {
            //TODO: Logging for errors
        }

        CharacterType characterType;
        for (int i = 0; i < length - amountOfMandatoryCharacters; ++i) {
            characterType = pickRandomCharacterType();
            try {
                password.append(generateCharacter(characterType));
            } catch (IllegalCharacterTypeException e) {
                //TODO: Logging for errors
            }
        }
        return shufflePassword(password.toString());
    }

    protected CharacterType pickRandomCharacterType() {
        int typePicker = typePicker = (int) (Math.random() * 100);
        if (typePicker < 30) {
            return CharacterType.LOWER;
        } else if (typePicker < 60) {
            return CharacterType.UPPER;
        } else if (typePicker < 90) {
            return CharacterType.DIGIT;
        }
        return CharacterType.SPECIAL;
    }

    protected char generateCharacter(CharacterType type) throws IllegalCharacterTypeException {
        switch (type) {
            case LOWER:
                return generateLowerCaseChar();
            case UPPER:
                return generateUpperCaseChar();
            case DIGIT:
                return generateDigit();
            case SPECIAL:
                return generateSpecialChar();
            default:
                throw new IllegalCharacterTypeException("Unknown character type given");
        }
    }

    protected char generateLowerCaseChar() {
        //ASCII codes: 97-122 (decimal)
        int left = 97,
            right = 122;
        return generateCharacterInBounds(97, 122);
    }

    protected char generateUpperCaseChar() {
        //ASCII codes: 65-90 (decimal)
        int left = 65,
            right = 90;
        return generateCharacterInBounds(left, right);
    }

    protected char generateDigit() {
        //ASCII codes: 48-57 (decimal)
        int left = 48,
            right = 57;
        return generateCharacterInBounds(left, right);
    }

    protected char generateCharacterInBounds(int leftBound, int rightBound) {
        return (char) (Math.random() * (rightBound - leftBound) + leftBound);
    }

    protected char generateSpecialChar() {
        //ASCII codes: are not monolith
        //TODO: Find a solution to generate random special characters (or their codes)
        return '!';
    }

    protected String shufflePassword(String password) {
        List<Character> characters = new ArrayList<>();
        for (char ch: password.toCharArray()) {
            characters.add(ch);
        }
        StringBuilder shuffledPassword = new StringBuilder(password.length());
        while (characters.size() != 0) {
            int randPos = (int) (Math.random() * characters.size());
            shuffledPassword.append(characters.remove(randPos));
        }

        return shuffledPassword.toString();
    }

    protected enum CharacterType {
        LOWER,
        UPPER,
        DIGIT,
        SPECIAL
    }
}
