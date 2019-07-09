package com.sqli.chatbot.naivebayes.util.opennlp;

import com.sqli.chatbot.naivebayes.util.constantes.Constant;
import com.sqli.chatbot.naivebayes.util.dto.Entity;
import com.sqli.chatbot.naivebayes.util.dto.OpenNlpResponse;
import com.sqli.chatbot.naivebayes.util.factory.OpenNlpResponseFactory;

import opennlp.tools.doccat.*;
import opennlp.tools.ml.AbstractTrainer;
import opennlp.tools.util.*;

import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenNLPCore implements OpenNLPService {
    /**
     * read the training data
     *
     * @param training_file_path training file path
     * @param charset_name       charset to use eg: UTF-8
     * @return return an ObjectStream
     * @throws IOException
     */
    private static ObjectStream readTrainingData(String training_file_path, String charset_name) throws IOException {
        InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File(training_file_path));
        ObjectStream lineStream = new PlainTextByLineStream(dataIn, Constant.CHARSET_NAME);
        return new DocumentSampleStream(lineStream);
    }

    /**
     * define the training parameters
     *
     * @param iterations
     * @param cutoff
     * @param algorithm  algorithm to use to detect your domain
     * @return
     */
    private static TrainingParameters defineTrainingParameters(String iterations, String cutoff, String algorithm) {
        // define the training parameters
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, iterations);
        params.put(TrainingParameters.CUTOFF_PARAM, cutoff);
        params.put(AbstractTrainer.ALGORITHM_PARAM, algorithm);
        return params;
    }

    /**
     * create a model from traning data
     *
     * @param language_code language training code eg:en
     * @param sampleStream  training data
     * @param params        training parameters
     * @return return Document Category Model
     * @throws IOException
     */
    private static DoccatModel createModelFromTrainingData(String language_code, ObjectStream sampleStream, TrainingParameters params) throws IOException {
        DoccatModel model = DocumentCategorizerME.train(language_code, sampleStream, params, new DoccatFactory());
        return model;
    }

    /**
     * save the model to local
     *
     * @param model               document category model
     * @param training_model_path model
     * @return return docuemnt category model
     * @throws IOException
     */
    private static DoccatModel saveModelToLocal(DoccatModel model, String training_model_path) throws IOException {
        BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream(training_model_path));
        model.serialize(modelOut);
        return model;
    }
    // test the model file by subjecting it to prediction

    /**
     * @param model       document category model
     * @param searchQuery entered search query
     * @return the most probably predicated result
     */
    private OpenNlpResponse testModelFileAndPredictBestResult(DoccatModel model, String searchQuery) {
        DocumentCategorizer doccat = new DocumentCategorizerME(model);
        String[] docWords = searchQuery.replaceAll("[^A-Za-z]", " ").split(" ");
        double[] aProbs = doccat.categorize(docWords);
        return OpenNlpResponseFactory.createOpenNlpDomainResponse(doccat.getBestCategory(aProbs), getMaxOfDoubleArray(aProbs));
    }

    private List<String> testModelFileAndPredictBestKeywords(DoccatModel model, String searchQuery) {
        DocumentCategorizer doccat = new DocumentCategorizerME(model);
        String[] docWords = searchQuery.replaceAll("[^A-Za-z]", " ").split(" ");
        double[] aProbs = doccat.categorize(docWords);

        int i = 0;
        List<String> results = new ArrayList<>();
        double[] probs = new double[doccat.getNumberOfCategories()];
    /*    for (int j = 0; j < doccat.getNumberOfCategories(); j++) {
            probs[j] = 0;
        }*/
        double seuil = (1.0 / (doccat.getNumberOfCategories()));
        for (double prob : aProbs) {
//            System.out.println("seuil :" + seuil + " keyword : " + doccat.getCategory(i) + " prob : " + prob);
            if (prob > seuil) {
                /*for (int j = 0; j < doccat.getNumberOfCategories(); j++) {
                    probs[j] = 0;
                }*/
                /*probs[i] = prob;
                String keyword = doccat.getBestCategory(probs);

                if (!results.contains(keyword)) {
                    results.add(keyword);
                }*/

                results.add(doccat.getCategory(i));
            }
            i++;
        }

        return results;
    }

    private List<Entity> getEntityFromASearchQuery(DoccatModel model, String searchQuery) {
        DocumentCategorizer doccat = new DocumentCategorizerME(model);
        double seuil = ((double) 1 / doccat.getNumberOfCategories()) * 2.5 ;
        return getEntities(model, searchQuery, seuil);
    }
    private List<Entity> getEntityFromASearchQuery(DoccatModel model, String searchQuery, double seuil) {
        return getEntities(model, searchQuery, seuil);
    }

    private List<Entity> getEntities(DoccatModel model, String searchQuery, double seuil) {
        DocumentCategorizer doccat = new DocumentCategorizerME(model);
        String[] docWords = searchQuery.replaceAll("[^A-Za-z]", " ").split(" ");
        double[] aProbs = doccat.categorize(docWords);
        int i = 0;
        List<Entity> results = new ArrayList<>();
        for (double prob : aProbs) {
            System.out.println("seuil :" + seuil + " keyword : " + doccat.getCategory(i) + " prob : " + prob);
            if (prob > seuil) {
                results.add(new Entity(doccat.getCategory(i), prob));
            }
            i++;
        }

        return results;
    }

    @Override
    public OpenNlpResponse getMostPredicatedResult(String training_file_path, String training_model_path, String searchQuery) throws IOException {
        DoccatModel model = loadOrGenerateModel(training_file_path, training_model_path);
        return testModelFileAndPredictBestResult(model, searchQuery);
    }

    @Override
    public List<String> getMostPredicatedKeywords(String training_file_path, String training_model_path, String searchQuery) throws IOException {
        DoccatModel model = loadOrGenerateModel(training_file_path, training_model_path);
        return testModelFileAndPredictBestKeywords(model, searchQuery);
    }

    @Override
    public List<Entity> getEntityModelsFromSearchQuery(String training_file_path, String training_model_path, String searchQuery) throws IOException {
        DoccatModel model = loadOrGenerateModel(training_file_path, training_model_path);
        return getEntityFromASearchQuery(model, searchQuery,0.4);
    }

    @Override
    public List<Entity> getEntityKeyWordsFromSearchQuery(String training_file_path, String training_model_path, String searchQuery) throws IOException {
        DoccatModel model = loadOrGenerateModel(training_file_path, training_model_path);
        return getEntityFromASearchQuery(model, searchQuery);
    }

    public DoccatModel loadOrGenerateModel(String training_file_path, String training_model_path) throws IOException {
        File trainingModel = Paths.get(training_model_path).toFile();
        return trainingModel.exists() ? new DoccatModel(trainingModel) : generateModel(training_file_path, training_model_path);
    }

    public static DoccatModel generateModel(String training_file_path, String training_model_path) throws IOException {
        new File("botNLP/model").mkdir();
        ObjectStream sampleSteam = readTrainingData(training_file_path, Constant.CHARSET_NAME);
        TrainingParameters params = defineTrainingParameters(Constant.ITERATIONS_PARAM, Constant.CUTOFF_PARAM, Constant.ALGORITHM_PARAM);
        DoccatModel model = createModelFromTrainingData(Constant.LANGUAGE_TRAINING_CODE, sampleSteam, params);
        return saveModelToLocal(model, training_model_path);
    }

    private double getMaxOfDoubleArray(double[] probs) {
        double max = 0;
        for (int i = 0; i < probs.length; i++) {
            max = Math.max(max, probs[i]);
        }
        return max;
    }
}