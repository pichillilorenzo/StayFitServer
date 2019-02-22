/**
 * 
 */
package com.stayfit.app.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author Matteo
 *
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class ListProducts {
	
    protected List<Product> product = new ArrayList<Product>();


}
