package com.jcondotta.test_support.clock;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface TestClockExamples {

    Instant FIXED_INSTANT_UTC = Instant.parse("2022-06-24T12:45:01Z");

    ZonedDateTime FIXED_DATE_TIME_UTC = ZonedDateTime.ofInstant(FIXED_INSTANT_UTC, ZoneId.of("UTC"));
    ZonedDateTime FIXED_DATE_TIME_MADRID = ZonedDateTime.ofInstant(FIXED_INSTANT_UTC, ZoneId.of("Europe/Madrid"));

    Clock FIXED_CLOCK_UTC = Clock.fixed(FIXED_DATE_TIME_UTC.toInstant(), FIXED_DATE_TIME_UTC.getZone());
    Clock FIXED_CLOCK_MADRID = Clock.fixed(FIXED_DATE_TIME_MADRID.toInstant(), FIXED_DATE_TIME_MADRID.getZone());

}
