package ru.aaf.finshop.datacenter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DataCenterMainApp {
    public static void main(String[] args) {
        com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean)
                java.lang.management.ManagementFactory.getOperatingSystemMXBean();

        log.info("availableProcessors:{}", Runtime.getRuntime().availableProcessors());
        log.info("TotalMemorySize, mb:{}", os.getTotalMemorySize() / 1024 / 1024);
        log.info("maxMemory, mb:{}", Runtime.getRuntime().maxMemory() / 1024 / 1024);
        log.info("freeMemory, mb:{}", Runtime.getRuntime().freeMemory() / 1024 / 1024);

        SpringApplication.run(DataCenterMainApp.class, args);
    }
}
