package pl.umk.workshop.springintroduction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import pl.umk.workshop.springintroduction.domain.numbermanager.DepositNumberManager;
import pl.umk.workshop.springintroduction.domain.UmkCloakroomFacade;
import pl.umk.workshop.springintroduction.domain.models.ExceededMaxNumberException;
import pl.umk.workshop.springintroduction.domain.models.Item;
import pl.umk.workshop.springintroduction.domain.models.Student;
import pl.umk.workshop.springintroduction.domain.UmkCloakroomRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SpringIntroductionApplicationTests extends TestsBase {

    private UmkCloakroomFacade umkCloakroomFacade;

    private UmkCloakroomRepository umkCloakroomRepository;

    private DepositNumberManager depositNumberManager;

    private ApplicationContext context;

    @Autowired
    public SpringIntroductionApplicationTests(
            UmkCloakroomFacade umkCloakroomFacade,
            UmkCloakroomRepository umkCloakroomRepository,
            DepositNumberManager depositNumberManager,
            ApplicationContext context
    ) {
        this.umkCloakroomFacade = umkCloakroomFacade;
        this.umkCloakroomRepository = umkCloakroomRepository;
        this.depositNumberManager = depositNumberManager;
        this.context = context;
    }


    // By default beans are singletons.
    // Every time we request it from the context, we get the same instance.
    @Test
    void singletonScope() {
        // when
        var incrementalNumberManager1 = (DepositNumberManager) context.getBean("incrementalDepositNumberManager");
        var incrementalNumberManager2 = (DepositNumberManager) context.getBean("incrementalDepositNumberManager");

        // then
        assertEquals(incrementalNumberManager1, incrementalNumberManager2);
        assertEquals(1, incrementalNumberManager1.getNextFreeNumber());
        assertEquals(2, incrementalNumberManager2.getNextFreeNumber());
    }

    // Let's see how we can create prototype scoped beans.
    // So that every time we request it from the context, we get a new instance.
    // TIP: @Scope
    @Test
    void prototypeScope() {
        // when
        var incrementalNumberManager1 = (DepositNumberManager) context.getBean("prototypeDepositNumberManager");
        var incrementalNumberManager2 = (DepositNumberManager) context.getBean("prototypeDepositNumberManager");

        // then
        assertNotEquals(incrementalNumberManager1, incrementalNumberManager2);
        assertEquals(1, incrementalNumberManager1.getNextFreeNumber());
        assertEquals(1, incrementalNumberManager2.getNextFreeNumber());
    }

    private void fillCloakroom(Student student, List<Item> items) {
        while (true) {
            try {
                umkCloakroomFacade.depositItems(student, items);
            } catch (ExceededMaxNumberException ex) {
                break;
            }
        }
    }
}
