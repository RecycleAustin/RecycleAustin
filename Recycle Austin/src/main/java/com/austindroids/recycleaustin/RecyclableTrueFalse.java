package com.austindroids.recycleaustin;

/*

This class is used to store the recyclable status of the sub-items. Use the constructor to
create an array of sub-otems for each major item category.  Use isTrueQuestion() to get the status
of each sub-item.

 */
public class RecyclableTrueFalse {


    private int recyclableQuestion;
    private boolean answerToQuestion;
    private String sQuestion;

    public RecyclableTrueFalse(int question, boolean isTrueQuestion){
        recyclableQuestion = question;
        answerToQuestion = isTrueQuestion;

    }


    public int getQuestion(){
        return recyclableQuestion;
    }

    public void setQuestion(int question){
        recyclableQuestion = question;
    }

    public boolean isTrueQuestion(){
        return answerToQuestion;
    }

    public void setAnswerToQuestion(boolean trueQuestion){
        answerToQuestion = trueQuestion;
    }


}

