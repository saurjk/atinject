package org.atinject.core.security;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SHA1Digester extends MessageDigester {

    public static final String ALGORITHM = "SHA-1";
    
    public String digest(String input) {
        return super.digest(input, ALGORITHM);
    }

}
