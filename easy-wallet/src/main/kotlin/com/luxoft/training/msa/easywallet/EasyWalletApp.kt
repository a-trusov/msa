package com.luxoft.training.msa.easywallet

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.luxoft.training.msa"])
@SpringBootApplication
class EasyWalletApp

fun main(args: Array<String>) {
    runApplication<EasyWalletApp>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
