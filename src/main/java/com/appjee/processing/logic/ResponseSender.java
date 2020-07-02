/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appjee.processing.logic;

import com.appjee.processing.webservice.IMessageService;
import com.appjee.processing.webservice.Message;
import com.appjee.processing.webservice.MessageService;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author myal6
 */
public class ResponseSender implements IResponseSender {

    @WebServiceRef(MessageService.class)
    private MessageService messageService;

    @Override
    public void sendResponse(Message message) {

            messageService = new MessageService();
            System.out.println("messageService : " + messageService);
            IMessageService port = messageService.getBasicHttpBindingIMessageService();
            System.out.println("OperationName : " + message.getOperationName().getValue());
            port.servicing(message);

            System.out.println("FIN");
    }
}
