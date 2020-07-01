/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appjee.processing.logic;

import com.appjee.receptionfacade.domain.SoapMessage;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author myal6
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/facadeQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class QueueProcessor implements MessageListener {

    private DecipherTester decipherTester;

    public QueueProcessor() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            try (Reader targetReader = new StringReader(message.getBody(String.class))) {

                JAXBContext jaxbContext = JAXBContext.newInstance(SoapMessage.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                SoapMessage soapMessage = (SoapMessage) jaxbUnmarshaller.unmarshal(targetReader);

                System.out.println("--------------------------");
                System.out.println(soapMessage.getInfo());
                System.out.println(soapMessage.getStatusOp());
//                System.out.println(soapMessage.getData()[0].toString());
                System.out.println("--------------------------");

                try {
                    try {
                        decipherTester = new DecipherTester(soapMessage);
                    } catch (SQLException ex) {
                        Logger.getLogger(QueueProcessor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(QueueProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } catch (JAXBException | JMSException | IOException ex) {
            Logger.getLogger(QueueProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
