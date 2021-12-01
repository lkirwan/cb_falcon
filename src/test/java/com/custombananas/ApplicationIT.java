package com.custombananas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationIT {


    @Test
    public void applicationContextTest() {
        Application.main(new String[] {});
    }
}
