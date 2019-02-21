/**
 * 
 */
package com.stayfit.userservice.app.util;

/**
 * @author lorenzo
 * 
 * This Enumeration represents kind of jobs used in a user's diet request.
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
