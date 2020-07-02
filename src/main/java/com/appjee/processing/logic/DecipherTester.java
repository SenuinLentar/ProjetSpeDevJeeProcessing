/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appjee.processing.logic;

import com.appjee.processing.convertion.MessageConverter;
import com.appjee.processing.dao.DAO;
import com.appjee.processing.webservice.IMessageService;
import com.appjee.processing.webservice.Message;
import com.appjee.processing.webservice.MessageService;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.soap.Addressing;

/**
 *
 * @author myal6
 */
@Named(value = "decipherTester")
@RequestScoped
public class DecipherTester implements IDecipherTester {

    private SoapMessage soapMessage;
    private DAO dao;
    private MessageConverter msgConverter;

//    @WebServiceRef(MessageService.class)
//    private IMessageService messageService;
    
    private ResponseSender responseSender = new ResponseSender();

    public DecipherTester() {
    }

    @Override
    public void verifyTextIsCLear(SoapMessage message) {

        dao = new DAO();
        msgConverter = new MessageConverter();

        //Connection to Oracle databaseS
        dao.connectionToDb();

//        //Starting the verification of the text
//        verifyTextIsCLear();
//        
//        //Close the connection to the Oracle Database
//        dao.closeConnection();
//        System.out.println(soapMessage.getData()[0].toString());
        this.soapMessage = message;

        //Split the text into single words
        String[] fileWords = soapMessage.getData()[0].toString().split("[ '.&\"(_)=)]");

        Boolean verificationResult = false;
        List<Boolean> verificationResultList = new ArrayList();

        int sampleSize = 25;            //in percentage
        int accuracyMinimum = 75;       //in percentage
        //Represents the total number of words that will be tested in the Oracle database
        int numberOfWordsToTest = Math.round((float) fileWords.length * (float) sampleSize / 100);

        int wordIndex = 0;
        int trueOccurences = 0;
        float resultAccuracy = 0;

        //Tests random words from the text
        for (int i = 0; i < numberOfWordsToTest; i++) {
            wordIndex = (int) (Math.random() * (fileWords.length - 1));
            try {
                //            System.out.println(wordIndex);
                verificationResult = dao.getWordQuery(fileWords[wordIndex].toLowerCase());
            } catch (SQLException ex) {
                Logger.getLogger(DecipherTester.class.getName()).log(Level.SEVERE, null, ex);
            }
            verificationResultList.add(verificationResult);
//            System.out.println(verificationResult);
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

        //If the accuracy is high enough, search for the secret message in the text
        if (resultAccuracy >= accuracyMinimum) {
            searchSecretMessage();
        }
    }

    //Search the secret message in the text
    private void searchSecretMessage() {
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(soapMessage.getData()[0].toString());
        while (m.find()) {
            System.out.println(m.group(1));
        }
        soapMessage.setOperationName("updateResult");
        Message msg = msgConverter.CreateMessageFromSoapMessage(soapMessage);

//        messageService.servicing(msg);
        System.out.println("responseSender : " + responseSender);
        System.out.println("Message Content test : " + msg.getOperationName().getValue());
        
        responseSender.sendResponse(msg);
    }

    public DAO getDao() {
        return dao;
    }
}
