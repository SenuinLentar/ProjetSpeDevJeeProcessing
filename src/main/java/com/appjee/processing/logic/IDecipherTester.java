/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appjee.processing.logic;

import com.appjee.receptionfacade.domain.SoapMessage;
import javax.ejb.Local;

/**
 *
 * @author myal6
 */
@Local
public interface IDecipherTester {
    public void verifyTextIsCLear(SoapMessage soapMessage);
}
