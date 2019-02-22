/**
 * 
 */
package com.stayfit.app.model;

import java.util.ArrayList;
import java.util.List;



/**
 * @author Matteo
 *
 */
public class ListProducts {
	
    protected List<Product> product;

    public List<Product> getProduct() {
    	
        if (product == null) {
            product = new ArrayList<Product>();
        }
        return this.product;
    }


}
