package com.jcondotta.transfer.request;

import io.restassured.RestAssured;
import org.apache.commons.validator.routines.IBANValidator;
import org.iban4j.Iban;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

import static io.restassured.RestAssured.given;

class RequestInternalTransferApiLoadTest {

    private static final int TOTAL_REQUESTS = 500;
    private static final int THREADS = 40;
    private static final Random RANDOM = new Random();

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8091;
    }

    @Test
    void shouldProcessInternalTransferRequestsWithPerformance() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        CountDownLatch latch = new CountDownLatch(TOTAL_REQUESTS);
        long start = System.nanoTime();

        for (int i = 0; i < TOTAL_REQUESTS; i++) {
            executor.submit(() -> {
                given()
                    .contentType("application/json")
                    .body(generateRandomPayload())
                    .when()
                    .post("/api/v1/bank-transfers/internal")
                    .then()
                    .statusCode(202);
                latch.countDown();
            });
        }

        latch.await();
        long elapsedMillis = (System.nanoTime() - start) / 1_000_000;

        System.out.printf("Processed %d requests in %d ms (%.2f RPS)%n",
            TOTAL_REQUESTS, elapsedMillis,
            (TOTAL_REQUESTS * 1000.0) / elapsedMillis);
    }

    private String generateRandomPayload() {
        UUID senderAccountId = UUID.randomUUID();
        String recipientIban = generateFakeIban();
        String amount = generateRandomAmount().toPlainString();
        String reference = "Ref " + RANDOM.nextInt(10000);

        return """
            {
                "senderAccountId": "%s",
                "recipientIban": "%s",
                "amount": "%s",
                "currency": "EUR",
                "reference": "%s"
            }
            """.formatted(senderAccountId, recipientIban, amount, reference);
    }

    private BigDecimal generateRandomAmount() {
        return BigDecimal.valueOf(0.01 + (999.99 - 0.01) * RANDOM.nextDouble())
            .setScale(2, RoundingMode.HALF_UP);
    }

    private String generateFakeIban() {
        return Iban.random().toString();
    }
}
