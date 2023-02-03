package com.test.business;
 
import com.test.dto.*;
import com.test.services.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.test.util.GSonUtils;
import com.test.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;


@Component
public class ControllerBusiness {

	@Autowired
	LoggerUtil log;







@Autowired
ServiceTestService serviceTestService;
	public BookResponseDTO serviceTestcustomer(BookRequestDTO requestEntity){
		String request = GSonUtils.serialize(requestEntity);
		return GSonUtils.toObject(serviceTestService.customer(request), BookResponseDTO.class);	
    }

}
