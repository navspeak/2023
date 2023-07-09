package com.nav.speak;

import java.time.*;

public class Test {
    public static void main(String[] args) {
        LocalDateTime t1 = LocalDateTime.now();

        System.out.println(t1.toString());


        Instant nowUtc = Instant.now();
        ZoneId asiaSingapore = ZoneId.of("Asia/Kolkata");
        ZonedDateTime nowIndia = ZonedDateTime.ofInstant(nowUtc, asiaSingapore);
        System.out.println(nowUtc);
        System.out.println(nowIndia);
        System.out.println(t1.toInstant(ZoneOffset.UTC));
    }
}
