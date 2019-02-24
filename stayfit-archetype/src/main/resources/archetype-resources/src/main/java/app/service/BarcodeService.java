package ${package}.app.service;

import org.springframework.transaction.annotation.Transactional;

public interface BarcodeService {

	String getNameByBarcode(String barcode) throws Exception;

}