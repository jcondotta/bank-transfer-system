package com.jcondotta.transfer.request.interfaces.rest;

import com.jcondotta.transfer.request.interfaces.rest.model.InternalTransferFromAccountIdToAccountIdRestRequest;
import com.jcondotta.transfer.request.interfaces.rest.model.InternalTransferFromAccountIdToIbanRestRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/bank-transfers")
public interface RequestInternalTransferController {

    @Operation(summary = "Initiate internal transfer from Account ID to IBAN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "The internal bank transfer request was accepted and is being processed."),
        @ApiResponse(responseCode = "400", description = "Invalid request. One or more fields are missing or contain invalid values."),
        @ApiResponse(responseCode = "500", description = "Unexpected error occurred while processing the transfer request.")
    })
    @PostMapping(path = "/from-account-id-to-iban", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<Void> requestInternalBankTransfer(@Valid @RequestBody InternalTransferFromAccountIdToIbanRestRequest request);

    @Operation(summary = "Initiate internal transfer from Account ID to Account ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "The internal bank transfer request was accepted and is being processed."),
        @ApiResponse(responseCode = "400", description = "Invalid request. One or more fields are missing or contain invalid values."),
        @ApiResponse(responseCode = "500", description = "Unexpected error occurred while processing the transfer request.")
    })
    @PostMapping(path = "/from-account-id-to-account-id", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<Void> requestInternalBankTransfer(@Valid @RequestBody InternalTransferFromAccountIdToAccountIdRestRequest request);
}