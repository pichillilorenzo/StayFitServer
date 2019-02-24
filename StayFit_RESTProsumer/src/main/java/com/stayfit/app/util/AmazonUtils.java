package com.stayfit.app.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.stayfit.app.model.ItemAttributes;
import com.stayfit.app.model.ListPrice;
import com.stayfit.app.model.Product;
import com.stayfit.app.model.ListProducts;



public class AmazonUtils {
	
	/**
	  * This function maps the XML response of amazon API with Model ListProduct 
	  * 
	  */
	
	
	public static ListProducts convert( Document doc) {
		ListProducts prod = new ListProducts();
		
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getElementsByTagName("Item");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Product productWsdl = new Product();
			ItemAttributes ItemAttributesWsdl = new ItemAttributes();
			ListPrice ListPrice = new ListPrice();
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
	
	/**
	  * This Function convert a string into XML Document
	  */
	public static Document convertStringToDocument(String xmlStr) {

		
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
