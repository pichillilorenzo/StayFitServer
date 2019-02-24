package ${package}.app.model;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ItemAttributes {
	
	    protected String title;
	    protected String brand;
	    protected String img;
	    protected ListPrice listPrice;
	    

}
