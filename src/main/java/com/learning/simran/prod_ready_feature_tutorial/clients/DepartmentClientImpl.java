package com.learning.simran.prod_ready_feature_tutorial.clients;

import com.learning.simran.prod_ready_feature_tutorial.DTOs.ApiResponse;
import com.learning.simran.prod_ready_feature_tutorial.DTOs.DepartmentDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentClientImpl implements DepartmentClient {

    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(DepartmentClientImpl.class);

    @Override
    public List<DepartmentDTO> getDepartments() {

        log.error("Error logs");
        log.warn("Warn logs");
        log.info("Info logs");
        log.debug("Debug logs");
        log.trace("Trace logs");

        try {
            ApiResponse<List<DepartmentDTO>> response = restClient
                    .get()
                    .uri("departments")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
                    //.body(DepartmentDTO)
            log.info("Successfully retrieved the departments in getDepartments");
            log.info("Retrieved department with title {}", "Hello");
            return response.getData();
        } catch (Exception ex){
            log.error("Exception occurred in getDepartments", ex);
            throw new RuntimeException("failure");
        }
    }

    @Override
    public DepartmentDTO getDepartmentById(Long departmentId) {
        try {
            ApiResponse<DepartmentDTO> response = restClient
                    .get()
                    .uri("departments/{departmentId}", departmentId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            //.body(DepartmentDTO)
            return response.getData();
        } catch (Exception ex){
            throw new RuntimeException("failure");
        }
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        try {
            //ResponseEntity<ApiResponse<DepartmentDTO>> response = restClient
            ApiResponse<DepartmentDTO> response = restClient
                    .post()
                    .uri("departments")
                    .body(departmentDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, res) -> {
                        System.out.println("Error occured" + res.getBody().readAllBytes());
                        throw new RuntimeException();
                    }))
                    .onStatus(HttpStatusCode::is5xxServerError, ((request, res) -> {
                        System.out.println("Error occured" + res.getBody().readAllBytes());
                        throw new RuntimeException();
                    }))
                    //.toEntity(new ParameterizedTypeReference<>() {
                    //                    });
                    .body(new ParameterizedTypeReference<>() {
                    });
            //.body(DepartmentDTO)
            return response.getData();
        } catch (Exception ex){
            throw new RuntimeException("failure");
        }
    }
}
