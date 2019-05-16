package com.sqli.chatbot.naivebayes.util.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DomainOrKeywordWriter {


    public void writeInFileDomainOrKeyword(String training_file, String data) throws IOException {
        //True = append file
        File file = new File(training_file);
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fw);
        bufferedWriter.write("\n" + data);
        bufferedWriter.close();
        fw.close();


    }
}
