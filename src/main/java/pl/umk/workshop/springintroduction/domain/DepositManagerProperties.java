package pl.umk.workshop.springintroduction.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "depositmanager")
public record DepositManagerProperties(Integer maxNumber) {
}

// non-record version
//
//@ConfigurationProperties(prefix = "depositmanager")
//public class DepositManagerProperties {
//
//    private final Integer maxNumber;
//
//    public DepositManagerProperties(Integer maxNumber) {
//        this.maxNumber = maxNumber;
//    }
//
//    public Integer getMaxNumber() {
//        return maxNumber;
//    }
//}
