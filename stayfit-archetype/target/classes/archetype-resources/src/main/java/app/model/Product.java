package ${package}.app.model;

import ${package}.app.model.ItemAttributes;


public class Product {
	
    protected String detailPageURL;
    protected ItemAttributes itemAttributes;


    public String getDetailPageURL() {
        return detailPageURL;
    }


    public void setDetailPageURL(String value) {
        this.detailPageURL = value;
    }


    public ItemAttributes getItemAttributes() {
        return itemAttributes;
    }

    public void setItemAttributes(ItemAttributes value) {
        this.itemAttributes = value;
    }


}
