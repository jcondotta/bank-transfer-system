package com.jcondotta.transfer.request.interfaces.rest.model;

import com.jcondotta.transfer.request.interfaces.rest.validation.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(description = "Request DTO for initiating an internal bank transfer from a sender account ID to a recipient account ID.")
public record InternalTransferFromAccountIdToAccountIdRestRequest(

    @NotNull(message = ValidationErrorMessages.SENDER_ACCOUNT_ID_NOT_NULL)
    @Schema(
        description = "Unique identifier of the sender's internal bank account (UUID).",
        requiredMode = RequiredMode.REQUIRED,
        example = "550e8400-e29b-41d4-a716-446655440000"
    )
    UUID senderAccountId,

    @NotNull(message = ValidationErrorMessages.RECIPIENT_ACCOUNT_ID_NOT_NULL)
    @Schema(
        description = "Unique identifier of the recipient's internal bank account (UUID).",
        requiredMode = RequiredMode.REQUIRED,
        example = "e78bfe7e-3762-4b12-bd70-5dc3f3aaef3b"
    )
    UUID recipientAccountId,

    @NotNull(message = ValidationErrorMessages.MONETARY_AMOUNT_NOT_NULL)
    @PositiveOrZero(message = ValidationErrorMessages.MONETARY_AMOUNT_ZERO_OR_POSITIVE)
    @Digits(integer = 12, fraction = 2, message = ValidationErrorMessages.MONETARY_AMOUNT_INVALID_PRECISION)
    @Schema(
        description = "Amount to transfer. Maximum 12 integer digits and 2 decimal places.",
        requiredMode = RequiredMode.REQUIRED,
        example = "250.17"
    )
    BigDecimal amount,

    @NotNull(message = ValidationErrorMessages.MONETARY_CURRENCY_NOT_NULL)
    @Pattern(regexp = "^[A-Z]{3}$", message = ValidationErrorMessages.MONETARY_CURRENCY_INVALID_FORMAT)
    @Schema(
        description = "Currency code in ISO 4217 3-letter format (e.g., EUR, USD).",
        requiredMode = RequiredMode.REQUIRED,
        examples = {"EUR", "USD"}
    )
    String currency,

    @Size(max = 50, message = ValidationErrorMessages.REFERENCE_TOO_LONG)
    @Schema(
        description = "Optional reference or description of the transfer. Maximum 50 characters.",
        example = "Invoice 456789"
    )
    String reference
) {}
