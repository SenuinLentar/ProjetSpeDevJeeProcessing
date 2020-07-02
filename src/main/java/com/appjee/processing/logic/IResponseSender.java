/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appjee.processing.logic;

import com.appjee.processing.webservice.Message;
import javax.ejb.Local;

/**
 *
 * @author myal6
 */
@Local
public interface IResponseSender {
    public void sendResponse(Message message);
}
