package com.sqli.chatbot.naivebayes.util.opennlp;

import com.sqli.chatbot.naivebayes.util.dto.Entity;
import com.sqli.chatbot.naivebayes.util.dto.OpenNlpResponse;

import java.io.IOException;
import java.util.List;

public interface OpenNLPService {
    OpenNlpResponse getMostPredicatedResult(String training_file_path, String training_model_path, String searchQuery) throws IOException;
    List<String> getMostPredicatedKeywords(String training_file_path, String training_model_path, String searchQuery) throws  IOException;
    List<Entity> getEntityModelsFromSearchQuery(String training_file_path, String training_model_path, String searchQuery) throws  IOException;
    List<Entity> getEntityKeyWordsFromSearchQuery(String training_file_path, String training_model_path, String searchQuery) throws  IOException;
}
