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
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 *
 * @author myal6
 */
public class MessageConverter {

    public Message CreateMessageFromSoapMessage(SoapMessage soapMessage) {

        Message msg = new Message();
        ObjectFactory factory = new ObjectFactory();

        //msg.setAppVersion(new JAXBElement(new QName(soapMessage.getAppVersion()), String.class, soapMessage.getAppVersion()));
        msg.setAppVersion(factory.createMessageAppVersion(soapMessage.getAppVersion()));
        
        //msg.setData(new JAXBElement(new QName(soapMessage.getData().getClass().getSimpleName()), Object.class, soapMessage.getData()));
        ArrayOfanyType array = factory.createArrayOfanyType();        
        array.getAnyType().addAll(Arrays.asList(soapMessage.getData()));
        msg.setData(factory.createMessageData(array));
        
        //msg.setInfo(new JAXBElement(new QName(soapMessage.getInfo()), String.class, soapMessage.getInfo()));
        msg.setInfo(factory.createMessageInfo(soapMessage.getInfo()));

        //msg.setOperationName(new JAXBElement(new QName(soapMessage.getOperationName()), String.class, soapMessage.getOperationName()));
        msg.setOperationName(factory.createMessageOperationName(soapMessage.getOperationName()));

        //msg.setOperationVersion(new JAXBElement(new QName(soapMessage.getOperationVersion()), String.class, soapMessage.getOperationVersion()));
        msg.setOperationVersion(factory.createMessageOperationVersion(soapMessage.getOperationVersion()));
        //msg.setTokenApp(new JAXBElement(new QName(soapMessage.getTokenApp()), String.class, soapMessage.getTokenApp()));
        msg.setTokenApp(factory.createMessageTokenApp(soapMessage.getTokenApp()));
        //msg.setTokenUser(new JAXBElement(new QName(soapMessage.getTokenUser()), String.class, soapMessage.getTokenUser()));
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
