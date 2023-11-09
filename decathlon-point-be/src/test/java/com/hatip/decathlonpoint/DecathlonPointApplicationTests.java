package com.hatip.decathlonpoint;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "classpath:db/test.sql")
@ActiveProfiles("test")
class DecathlonPointApplicationTests {

    @Test
    void contextLoads() {
    }

}
