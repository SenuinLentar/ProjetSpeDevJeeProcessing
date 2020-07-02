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
    
    private String test = "coucou";

    public String getTest() {
        return test;
    }
    
    @WebServiceRef(name = "MessageService")
    private MessageService messageService;
    
    @Override
    public void sendResponse(Message message){
        System.out.println("messageService : " + messageService);
        //messageService.servicing(message);        
    }
}
