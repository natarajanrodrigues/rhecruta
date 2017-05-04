/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.core.services.upload;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import javax.ejb.Stateless;

/**
 *
 * @author Pedro Arthur
 */

@Stateless
public class DropboxBean {
    
    private final static String ACCESS_TOKEN
            = "PIrBwAnAFsAAAAAAAAAAF_0xEhTF_XPTXhT90ZG9vRK3NHljO4fs5MPN-2UypaIw";
    //rhecrutapp@gmail.com
    private final DbxClientV2 client;
    
    public DropboxBean() {
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        this.client = new DbxClientV2(config, ACCESS_TOKEN);
    }
    
    public DbxClientV2 getClient() {
        return this.client;
    }
}
