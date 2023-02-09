package Model;

import java.util.List;

public record Question(String question, List<String> answer, int right) {

    /**
     * The number of answers of a question
     */
    public static final int NUMBER_OF_ANSWER = 4;
}
