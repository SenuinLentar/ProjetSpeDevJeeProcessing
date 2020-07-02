/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appjee.processing.logic;

import com.appjee.processing.convertion.MessageConverter;
import com.appjee.processing.dao.DAO;
import com.appjee.processing.webservice.Message;
import com.appjee.receptionfacade.domain.SoapMessage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author myal6
 */
@Named(value = "decipherTester")
@RequestScoped
public class DecipherTester implements IDecipherTester {

    private SoapMessage soapMessage;
    private SoapMessage soapMessageToSend;

    private DAO dao;
    private MessageConverter msgConverter;
    private List<String> data = new ArrayList<>();

    private ResponseSender responseSender = new ResponseSender();

    public DecipherTester() {
    }

    @Override
    public void verifyTextIsCLear(SoapMessage message) {
        soapMessageToSend = new SoapMessage();
        dao = new DAO();
        msgConverter = new MessageConverter();

        //Connection to Oracle database
        dao.connectionToDb();

        this.soapMessage = message;

        Boolean verificationResult = false;
        List<Boolean> verificationResultList = new ArrayList();

        int sampleSize = 25;            //in percentage
        int accuracyMinimum = 75;       //in percentage
        //Represents the total number of words that will be tested in the Oracle database
        int numberOfWordsToTest = 0;

        int wordIndex = 0;
        int trueOccurences = 0;
        float resultAccuracy = 0;
        for (int j = 0; j < soapMessage.getData().length - 1; j = j + 2) {
            //Split the text into single words
            String[] fileWords = soapMessage.getData()[j + 1].toString().split("[ '.&\"(_)=)]");
            //Get the number of words to be tested
            numberOfWordsToTest = Math.round((float) fileWords.length * (float) sampleSize / 100);

            //Tests random words from the text
            for (int i = 0; i < numberOfWordsToTest; i++) {
                wordIndex = (int) (Math.random() * (fileWords.length - 1));
                try {
                    verificationResult = dao.getWordQuery(fileWords[wordIndex].toLowerCase());
                } catch (SQLException ex) {
                    Logger.getLogger(DecipherTester.class.getName()).log(Level.SEVERE, null, ex);
                }
                verificationResultList.add(verificationResult);
            }

            //Calculate the percentage of words that had been recognised in the Oracle database
            trueOccurences = Collections.frequency(verificationResultList, true);
            resultAccuracy = Math.round((float) trueOccurences / (float) numberOfWordsToTest * 100);

            System.out.println("--------------------------");
            System.out.println("numberOfWordsToTest : " + numberOfWordsToTest);
            System.out.println("trueOccurences : " + trueOccurences);
            System.out.println("fileWords.length : " + fileWords.length);
            System.out.println("resultAccuracy : " + resultAccuracy);
            System.out.println("--------------------------");

            soapMessageToSend.setAppVersion(soapMessage.getAppVersion());
            soapMessageToSend.setOperationName("updateResult");
            soapMessageToSend.setInfo(soapMessage.getInfo());
            soapMessageToSend.setOperationVersion(soapMessage.getOperationVersion());
            soapMessageToSend.setTokenApp(soapMessage.getTokenApp());
            soapMessageToSend.setTokenUser(soapMessage.getTokenUser());

            data.clear();
            //Put the File name in the List
            data.add(soapMessage.getData()[j].toString());
            //Put the File content in the List
            data.add(soapMessage.getData()[j + 1].toString());
            //Put the File key in the List
            data.add(soapMessage.getData()[soapMessage.getData().length - 1].toString());

            soapMessageToSend.setData(data.toArray());

            callSender(soapMessageToSend);

            //If the accuracy is high enough, search for the secret message in the text
            if (resultAccuracy >= accuracyMinimum) {
                searchSecretMessage(j);
            }
        }
    }

    //Search the secret message in the text
    private void searchSecretMessage(int loopIndex) {
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(soapMessage.getData()[loopIndex + 1].toString());
        System.out.println(soapMessage.getData()[loopIndex + 1].toString());
        String secretMessage = "";

        while (m.find()) {
            secretMessage = m.group(1);
            System.out.println(m.group(1));
        }

        if (secretMessage != "") {

            soapMessageToSend.setAppVersion(soapMessage.getAppVersion());
            soapMessageToSend.setOperationName("updateSecretResult");
            soapMessageToSend.setInfo(soapMessage.getInfo());
            soapMessageToSend.setOperationVersion(soapMessage.getOperationVersion());
            soapMessageToSend.setTokenApp(soapMessage.getTokenApp());
            soapMessageToSend.setTokenUser(soapMessage.getTokenUser());
            

            data.clear();
            //Put the File name in the List
            data.add(soapMessage.getData()[loopIndex].toString());
            //Put the File key in the List
            data.add(soapMessage.getData()[soapMessage.getData().length - 1].toString());
            //Put the File secret message in the List
            data.add(secretMessage);

            soapMessageToSend.setData(data.toArray());

            callSender(soapMessageToSend);
        }
    }

    private void callSender(SoapMessage messageToSend) {
        Message msg = msgConverter.CreateMessageFromSoapMessage(messageToSend);

        System.out.println("responseSender : " + responseSender);
        System.out.println("Message Content test : " + msg.getOperationName().getValue());

        responseSender.sendResponse(msg);
    }

    public DAO getDao() {
        return dao;
    }
}
