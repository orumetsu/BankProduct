package com.multipolar.bootcamp.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipolar.bootcamp.gateway.dto.ErrorMessageDTO;
import com.multipolar.bootcamp.gateway.dto.ProductDTO;
import com.multipolar.bootcamp.gateway.kafka.AccessLog;
import com.multipolar.bootcamp.gateway.service.AccessLogService;
import com.multipolar.bootcamp.gateway.util.RestTemplateUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.net.ConnectException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final String PRODUCT_URL = "http://localhost:8081/product";
    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;

    private final AccessLogService accessLogService;


    public ApiController(RestTemplateUtil restTemplateUtil, ObjectMapper objectMapper, AccessLogService accessLogService) {
        this.restTemplateUtil = restTemplateUtil;
        this.objectMapper = objectMapper;
        this.accessLogService = accessLogService;
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts() throws JsonProcessingException {
        try{
            ResponseEntity<?> response = restTemplateUtil.getList(PRODUCT_URL, new ParameterizedTypeReference<>() {});
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog("GET", PRODUCT_URL,
                    response.getStatusCodeValue(), response.getBody().toString());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException | HttpServerErrorException ex){
            List<ErrorMessageDTO> errorResponse =
                    objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            //kirim AccessLog fail
            AccessLog accessLog = new AccessLog("GET", PRODUCT_URL,
                    ex.getRawStatusCode(), ex.getResponseBodyAsString());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @PostMapping("/createProduct")
    public ResponseEntity<?> postProduct(@RequestBody ProductDTO productDTO) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.post(PRODUCT_URL, productDTO, ProductDTO.class);
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog("POST", PRODUCT_URL,
                    response.getStatusCodeValue(), response.getBody().toString());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            //kirim AccessLog fail
            AccessLog accessLog = new AccessLog("POST", PRODUCT_URL,
                    ex.getRawStatusCode(), ex.getResponseBodyAsString());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) throws JsonProcessingException {
        //access products API and obtain product data
        String getByIdUrl = PRODUCT_URL + "/" + id;
        try{
            ResponseEntity<?> response = restTemplateUtil.get(getByIdUrl, ProductDTO.class);
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog("GET", getByIdUrl,
                    response.getStatusCodeValue(), response.getBody().toString());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException | HttpServerErrorException ex){
            List<ErrorMessageDTO> errorResponse =
                    objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            //kirim AccessLog fail
            AccessLog accessLog = new AccessLog("GET", getByIdUrl,
                    ex.getRawStatusCode(), ex.getResponseBodyAsString());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable String id, @RequestBody ProductDTO productDTO)
            throws JsonProcessingException {
        String getByIdUrl = PRODUCT_URL + "/" + id;
        try{
            ResponseEntity<?> response = restTemplateUtil.put(getByIdUrl, productDTO, ProductDTO.class);
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog("PUT", getByIdUrl,
                    response.getStatusCodeValue(), response.getBody().toString());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException | HttpServerErrorException ex){
            List<ErrorMessageDTO> errorResponse =
                    objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            //kirim AccessLog fail
            AccessLog accessLog = new AccessLog("GET", getByIdUrl,
                    ex.getRawStatusCode(), ex.getResponseBodyAsString());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable String id) throws JsonProcessingException {
        String getByIdUrl = PRODUCT_URL + "/" + id;
        try{
            ResponseEntity<?> response = restTemplateUtil.delete(getByIdUrl);
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog("DELETE", getByIdUrl,
                    response.getStatusCodeValue(), "DELETED ID: " + id);
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException | HttpServerErrorException ex){
            List<ErrorMessageDTO> errorResponse =
                    objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            //kirim AccessLog fail
            AccessLog accessLog = new AccessLog("DELETE", getByIdUrl,
                    ex.getRawStatusCode(), ex.getResponseBodyAsString());
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }
}
