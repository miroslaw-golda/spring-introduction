package pl.umk.workshop.springintroduction.domain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import pl.umk.workshop.springintroduction.domain.numbermanager.DepositNumberManager;
import pl.umk.workshop.springintroduction.domain.numbermanager.EvenDepositNumberManager;
import pl.umk.workshop.springintroduction.domain.numbermanager.IncrementalDepositNumberManager;

@Configuration
@EnableConfigurationProperties(DepositManagerProperties.class)
public class UmkCloakroomFacadeConfiguration {

    @Bean
    UmkCloakroomFacade umkCloakroomFacade(
            UmkCloakroomRepository umkCloakroomRepository,
            @Qualifier("incrementalDepositNumberManager") DepositNumberManager depositNumberManager
    ) {
        return new UmkCloakroomFacadeImpl(umkCloakroomRepository, depositNumberManager);
    }

    @Bean
    @Scope("singleton")
    DepositNumberManager incrementalDepositNumberManager(
            DepositManagerProperties depositManagerProperties
    ) {
//        return new IncrementalDepositNumberManager(depositManagerProperties.getMaxNumber()); // for class-based properties version
        return new IncrementalDepositNumberManager(depositManagerProperties.maxNumber());
    }

    @Bean
    @Scope("prototype")
    DepositNumberManager prototypeDepositNumberManager() {
        return new IncrementalDepositNumberManager();
    }

    @Primary
    @Bean
    DepositNumberManager evenDepositNumberManager() {
        return new EvenDepositNumberManager();
    }
}