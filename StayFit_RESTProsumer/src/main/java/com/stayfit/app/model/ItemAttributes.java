/**
 * 
 */
package com.stayfit.app.model;


/**
 * @author Matteo
 *
 */
public class ItemAttributes {
	
	    protected String title;
	    protected String brand;
	    protected String img;
	    protected ListPrice listPrice;
	    
	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String value) {
	        this.title = value;
	    }
	    
	    public String getBrand() {
	        return brand;
	    }

	    public void setBrand(String value) {
	        this.brand = value;
	    }

 
	    public String getImg() {
	        return img;
	    }


	    public void setImg(String value) {
	        this.img = value;
	    }

	    public ListPrice getListPrice() {
	        return listPrice;
	    }

	    public void setListPrice(ListPrice value) {
	        this.listPrice = value;
	    }

}
