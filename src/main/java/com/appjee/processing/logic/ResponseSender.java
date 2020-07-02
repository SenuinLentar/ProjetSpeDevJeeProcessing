/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appjee.processing.logic;

import com.appjee.processing.webservice.IMessageService;
import com.appjee.processing.webservice.Message;
import com.appjee.processing.webservice.MessageService;
import javax.ejb.Stateless;
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
//        MessageService messageService = new MessageService();

            messageService = new MessageService();
            System.out.println("messageService : " + messageService);
            IMessageService port = messageService.getBasicHttpBindingIMessageService();
            System.out.println("OperationName : " + message.getOperationName().getValue());
            // TODO initialize WS operation arguments here
//            org.datacontract.schemas._2004._07.contract.Message msg = new org.datacontract.schemas._2004._07.contract.Message();
            // TODO process result here
            port.servicing(message);

//            try { // Call Web Service Operation
//                org.tempuri.MessageService service = new org.tempuri.MessageService();
//                org.tempuri.IMessageService port = service.getBasicHttpBindingIMessageService();
//                // TODO initialize WS operation arguments here
//                org.datacontract.schemas._2004._07.contract.Message msg = new org.datacontract.schemas._2004._07.contract.Message();
//                // TODO process result here
//                org.datacontract.schemas._2004._07.contract.Message result = port.servicing(msg);
//                System.out.println("Result = " + result);
//            } catch (Exception ex) {
//                // TODO handle custom exceptions here
//            }

            System.out.println("FIN");
//            System.out.println("Result = "+result);
    }
}
