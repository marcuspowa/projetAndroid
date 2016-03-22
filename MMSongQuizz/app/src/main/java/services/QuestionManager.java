package services;

import javax.inject.Inject;

import interfaces.IQuestion;
import models.Question;

/**
 * Created by remy on 22/03/2016.
 */
public class QuestionManager {

    @Inject
    public QuestionManager(){

    }

    public IQuestion getRandomQuestion(){
        return new Question("Quel artiste interprète 'Carry on my wayward son'?", "kansas");
    }


}
