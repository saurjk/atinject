package org.atinject.core.timer;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.atinject.core.cache.CacheExtension;
import org.atinject.core.transaction.InMemoryTransactionServices;
import org.atinject.integration.ArquillianIT;
import org.atinject.integration.DefaultDeployment;
import org.atinject.integration.IntegrationTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.slf4j.Logger;

public class TimerServiceIT extends IntegrationTest {

    @Deployment
    public static JavaArchive createDeployment() {
    	return new DefaultDeployment(ArquillianIT.class)
			.appendEmptyBeansXml()
			.appendJavaxEnterpriseInjectSpiExtension(CacheExtension.class)
			.appendOrgJBossWeldBootstrapApiService(InMemoryTransactionServices.class)
			.appendResource("arquillian-logback.xml", "logback.xml")
			.appendResource("arquillian-jgroups.xml", "jgroups.xml")
			.getArchive();
    }
    
    @Inject
    private Logger logger;

    @Inject
    private TimerService timerService;
    
    private static int count;
    
    @Test @InSequence(1)
    public void testScheduleAt() throws Exception {
        timerService.toString();
        while(count < 10) {
            Thread.sleep(1000L);
        }
        Assertions.assertThat(count).isGreaterThan(9);
    }
    
    @Schedule(seconds={-1}, minutes={-1}, hours={-1})
    public void eachSecond(){
        logger.info("count {}", count++);
    }

}
