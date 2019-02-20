/**
 * 
 */
package com.stayfit.Amazon.app.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;

import com.stayfit.amazonservice.Products;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


/**
 * @author Matteo
 *
 */
@Component
public class AmazonUtils {
	
	
	
	public Products convert( Document doc) {
		Products prod = new Products();
		
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getElementsByTagName("Item");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			com.stayfit.amazonservice.Product productWsdl = new com.stayfit.amazonservice.Product();
			com.stayfit.amazonservice.ItemAttributes ItemAttributesWsdl = new com.stayfit.amazonservice.ItemAttributes();
			com.stayfit.amazonservice.ListPrice ListPrice = new com.stayfit.amazonservice.ListPrice();
			Node nNode = nList.item(temp);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element eElement = (Element) nNode;
				productWsdl.setDetailPageURL(eElement.getElementsByTagName("DetailPageURL").item(0).getTextContent());
				ItemAttributesWsdl.setTitle(eElement.getElementsByTagName("Title").item(0).getTextContent());
				ItemAttributesWsdl.setBrand(eElement.getElementsByTagName("Brand").item(0).getTextContent());
				ItemAttributesWsdl.setImg(eElement.getElementsByTagName("Img").item(0).getTextContent());
				ListPrice.setAmount(eElement.getElementsByTagName("Amount").item(0).getTextContent());
				ListPrice.setCurrency(eElement.getElementsByTagName("Currency").item(0).getTextContent());
				
				ItemAttributesWsdl.setListPrice(ListPrice);
				productWsdl.setItemAttributes(ItemAttributesWsdl);

			}
			prod.getProduct().add(productWsdl);
			
		}
		
		
		return prod;
	}
	
	
	public Document convertStringToDocument(String xmlStr) {

		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try  
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
    }
	
}