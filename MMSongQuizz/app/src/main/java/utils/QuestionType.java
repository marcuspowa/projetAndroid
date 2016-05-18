package utils;

import java.util.Random;

/**
 * Created by remy on 13/05/2016.
 */
public enum QuestionType {
    BASIC,
    SOUND,
    IMAGE;

    public static QuestionType getRandom(){
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(QuestionType.values().length);
        return QuestionType.values()[index];
    }
}
