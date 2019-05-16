package com.sqli.chatbot.naivebayes.util.constantes;

import opennlp.tools.ml.naivebayes.NaiveBayesTrainer;

import java.io.File;

public interface Constant {
    //String TRAINING_DOMAIN_FILE_PATH="train"+File.separator+"en-sqli-domain.train";
    //String TRAINING_DOMAIN_MODEL_PATH="model"+ File.separator+"en-ner-domain.bin";
    //String TRAINING_KEYWORD_FILE_PATH="train"+File.separator+"en-sqli-keyword.train";
    //String TRAINING_KEYWORD_MODEL_PATH="model"+ File.separator+"en-ner-keyword.bin";
    //String TRAINING_DOMAIN_FILE_PATH="train"+File.separator+"en-sqli-domain.train";
    String TRAINING_DOMAIN_FILE_PATH="botNLP/train"+File.separator+"en-sqli-domain.train";
    String TRAINING_DOMAIN_MODEL_PATH="botNLP/model"+ File.separator+"en-ner-domain.bin";
    String TRAINING_KEYWORD_FILE_PATH="botNLP/train"+File.separator+"en-sqli-keyword.train";
    String TRAINING_KEYWORD_MODEL_PATH="botNLP/model"+ File.separator+"en-ner-keyword.bin";
    String CHARSET_NAME="UTF-8";
    String LANGUAGE_TRAINING_CODE="en";
    String ITERATIONS_PARAM = 10 + "";
    String CUTOFF_PARAM = 0 + "";
    /**
     * naive bayes algorithm
     */
    String ALGORITHM_PARAM = NaiveBayesTrainer.NAIVE_BAYES_VALUE;
}
