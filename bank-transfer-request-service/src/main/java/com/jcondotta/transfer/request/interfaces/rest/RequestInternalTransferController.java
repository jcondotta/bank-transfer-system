package com.jcondotta.transfer.request.interfaces.rest;

import com.jcondotta.transfer.request.interfaces.rest.model.InternalTransferRestRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.UUID;

@RequestMapping("${api.v1.bank-transfers.root-path}")
public interface RequestInternalTransferController {

    @Operation(summary = "Initiate internal transfer from Account ID to IBAN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "The internal bank transfer request was accepted and is being processed."),
        @ApiResponse(responseCode = "400", description = "Invalid request. One or more fields are missing or contain invalid values."),
        @ApiResponse(responseCode = "500", description = "Unexpected error occurred while processing the transfer request.")
    })
    @PostMapping(path = "/internal", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<String> requestInternalBankTransfer(
        @Valid @RequestBody InternalTransferRestRequest request,
        @RequestHeader(name = "X-Idempotency-Key") UUID idempotencyKey,
        Locale locale);
}