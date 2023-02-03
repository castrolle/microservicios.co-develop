package com.test.api;


import com.test.util.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.test.business.ControllerBusiness;

import com.test.dto.*;
import java.util.List;

@RestController
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Accion exitosa"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@Api(value = "app-account-service-api ApiController", description = "test",  tags = {"",""})
public class ApiController {

    @Autowired
    ControllerBusiness controllerBusiness;

	@Value("${spring.application.version}")
	private String version;

	@GetMapping("version")
    public ResponseEntity<String> version() {
        return new ResponseEntity(version, HttpStatus.OK);
    }







@PostMapping("serviceTestCustomer")
public BookResponseDTO customer(@RequestBody BookRequestDTO request){
	return controllerBusiness.serviceTestcustomer(request);
}


}
