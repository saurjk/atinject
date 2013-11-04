package org.atinject.api.usertopology;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.atinject.api.user.entity.UserEntity;
import org.atinject.core.cache.DistributedCache;
import org.atinject.core.cdi.Named;
import org.infinispan.remoting.transport.TopologyAwareAddress;

@ApplicationScoped
public class UserTopologyService
{

    @Inject @Named("user") private DistributedCache<String, UserEntity> userCache;
    
    @PostConstruct
    public void initialize(){
        // ensure we're a topology aware cluster
        if ( ! (userCache.getRpcManager().getAddress() instanceof TopologyAwareAddress))
        {
            throw new RuntimeException("must be in a topology aware cluster");
        }
        if (userCache.getDistributionManager() == null){
            throw new RuntimeException("must be a distributed cache");
        }
    }
    
    public TopologyAwareAddress getUserKeyPrimaryLocation(String userId){
        return (TopologyAwareAddress) userCache.getDistributionManager().getPrimaryLocation(userId);
    }
    
}
