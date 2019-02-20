/**
 * 
 */
package com.stayfit.userservice.app.util;

/**
 * @author lorenzo
 *
 */
public enum JobKind {
	
	SEDENTARY, 
	NORMAL, 
	HARD;
	
	public String value() {
        return name();
    }

    public static JobKind fromValue(String v) {
        return valueOf(v);
    }
    
}
