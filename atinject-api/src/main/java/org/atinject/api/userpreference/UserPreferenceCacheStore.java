package org.atinject.api.userpreference;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.atinject.api.userpreference.entity.UserPreferencesEntity;
import org.atinject.core.cache.DistributedCache;
import org.atinject.core.cdi.Named;
import org.atinject.core.tiers.CacheStore;

@ApplicationScoped
public class UserPreferenceCacheStore extends CacheStore {

    @Inject @Named("user-preferences") private DistributedCache<String, UserPreferencesEntity> cache;
    
    public UserPreferencesEntity getUserPreferences(String userId){
        return cache.get(userId);
    }
    
    public void lockUserPreferences(String userId){
        cache.lock(userId);
    }
    
    public void putUserPreferences(UserPreferencesEntity userPreferences){
        cache.put(userPreferences.getUserId(), userPreferences);
    }
    
    public void removeUserPreferences(UserPreferencesEntity userPreferences){
        cache.remove(userPreferences.getUserId());
    }
}
