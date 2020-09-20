package com.luxoft.training.msa.platform.service

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service


@Service
@EnableConfigurationProperties(ServiceProperties::class)
class UserService(private val serviceProperties: ServiceProperties) {
    fun message(): String? {
        return serviceProperties.message
    }
}