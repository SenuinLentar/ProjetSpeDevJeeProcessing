/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appjee.processing.convertion;

import com.appjee.processing.webservice.ArrayOfanyType;
import com.appjee.processing.webservice.Message;
import com.appjee.processing.webservice.ObjectFactory;
import com.appjee.receptionfacade.domain.SoapMessage;
import java.util.Arrays;

/**
 *
 * @author myal6
 */
public class MessageConverter {

    public Message CreateMessageFromSoapMessage(SoapMessage soapMessage) {

        Message msg = new Message();
        ObjectFactory factory = new ObjectFactory();

        msg.setAppVersion(factory.createMessageAppVersion(soapMessage.getAppVersion()));
        
        ArrayOfanyType array = factory.createArrayOfanyType();        
        array.getAnyType().addAll(Arrays.asList(soapMessage.getData()));
        msg.setData(factory.createMessageData(array));
        
        msg.setInfo(factory.createMessageInfo(soapMessage.getInfo()));

        msg.setOperationName(factory.createMessageOperationName(soapMessage.getOperationName()));

        msg.setOperationVersion(factory.createMessageOperationVersion(soapMessage.getOperationVersion()));
        
        msg.setTokenApp(factory.createMessageTokenApp(soapMessage.getTokenApp()));
        
        msg.setTokenUser(factory.createMessageTokenUser(soapMessage.getTokenUser()));

        System.out.println("--------------------------");
        //System.out.println(msg.getInfo().getValue());
        //System.out.println(msg.getTokenApp().getValue());
        //System.out.println(msg.getTokenUser().getValue());
        System.out.println(msg.getOperationName().getValue());
        System.out.println("--------------------------");

        return msg;
    }
}
