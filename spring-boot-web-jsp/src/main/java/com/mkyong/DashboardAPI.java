package com.mkyong;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardAPI {

	@SuppressWarnings("unchecked")
	@GetMapping("/sale/product")
	public ResponseEntity<?> productWiseSale(Map<String, Object> model){
		try {
			 return new ResponseEntity(new ReadWriteExcelFile().readXLSXFileString(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
