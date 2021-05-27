package com.navigation.osmdataprocessor.annotations

import org.springframework.test.context.ActiveProfiles

import java.lang.annotation.*

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("integration-test")
@Documented
@interface IntegrationTest {
}
