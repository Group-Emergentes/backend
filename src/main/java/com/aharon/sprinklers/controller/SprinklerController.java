package com.aharon.sprinklers.controller;

import com.aharon.common.dto.ApiResponse;
import com.aharon.sprinklers.dto.SprinklerActionRequest;
import com.aharon.sprinklers.dto.CreateSprinkler;
import com.aharon.sprinklers.dto.SprinklerResponse;
import com.aharon.sprinklers.service.SprinklerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprinklers")
@AllArgsConstructor
public class SprinklerController {
    private final SprinklerService sprinklerService;

    @PostMapping
    public ResponseEntity<ApiResponse<SprinklerResponse>> addSprinkler(@Valid @RequestBody CreateSprinkler createSprinkler) {
        SprinklerResponse sprinklerResponse = sprinklerService.addSprinkler(createSprinkler);

        ApiResponse<SprinklerResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Sprinkler created successfully.");
        apiResponse.setData(sprinklerResponse);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("zone/{zoneId}")
    public ResponseEntity<ApiResponse<List<SprinklerResponse>>> getSprinklersByZoneId(@PathVariable Long zoneId){
        List<SprinklerResponse> sprinklerResponseList = sprinklerService.getAllSprinklersByZoneId(zoneId);

        ApiResponse<List<SprinklerResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("All Sprinklers by zone "+ zoneId);
        apiResponse.setData(sprinklerResponseList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("{sprinklerId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteSprinkler(@PathVariable String sprinklerId){
        Boolean deleted = sprinklerService.deleteSprinkler(sprinklerId);

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Sprinklers deleted "+ sprinklerId);
        apiResponse.setData(deleted);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("zone/active-disable-sprinklers")
    public ResponseEntity<ApiResponse<Boolean>> changeSprinklersByZoneId(@RequestBody SprinklerActionRequest sprinklerActionRequest){
        Boolean change = false;
        if(sprinklerActionRequest.getActive()){
            change = sprinklerService.activeAllSprinklersByZoneId(sprinklerActionRequest);
        }else{
            change = sprinklerService.disableAllSprinklersByZoneId(sprinklerActionRequest);
        }


        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Sprinklers change in zone "+ sprinklerActionRequest.getZoneId());
        apiResponse.setData(change);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
