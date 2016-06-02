package interfaces;

import utils.QuestionType;

/**
 * Created by remy on 22/03/2016.
 */
public interface IQuestion {

    QuestionType getType();
    String getQuestion();
    String getResponse();
    boolean checkResponse(String response);

}
