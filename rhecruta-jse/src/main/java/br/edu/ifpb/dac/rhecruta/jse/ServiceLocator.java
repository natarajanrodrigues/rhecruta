/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.jse;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Pedro Arthur
 */
public class ServiceLocator {
    
    
    public <T> T lookup(String resource, Class<T> type) {
        try {
            InitialContext context = new InitialContext(createProperties());
            return (T) context.lookup(resource);
            
        } catch (NamingException ne) {
            
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            ne.printStackTrace();
            throw new RuntimeException(ne);
        }
    }  
    
    private Properties createProperties() {
        
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
        //docker configs
//        props.put("org.omg.CORBA.ORBInitialHost", "rhecruta-core-server");
//        props.put("org.omg.CORBA.ORBInitialPort", "3700");

        //localhost configs
        props.put("org.omg.CORBA.ORBInitialHost", "localhost");
        props.put("org.omg.CORBA.ORBInitialPort", "3700");

        return props;
    }

}
