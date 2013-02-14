package org.atinject.api.session;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.atinject.core.cache.CacheName;
import org.atinject.core.cache.ClusteredCache;
import org.infinispan.distexec.mapreduce.Collator;
import org.infinispan.distexec.mapreduce.Collector;
import org.infinispan.distexec.mapreduce.Mapper;
import org.infinispan.distexec.mapreduce.Reducer;

/**
 * Note : session cache be replicated, optimistic and should not be persisted by any mean
 */
@CacheName("session")
@ApplicationScoped
public class SessionCache extends ClusteredCache<String, Session>
{
    public Session getSession(String sessionId){
        return get(sessionId);
    }

    /**
     * Note : this method use map reduce operation to search
     */
    public Session getSessionByUserId(String userId){
        return performMapReduce(
                new GetSessionByUserIdMapper(userId),
                new GetSessionByUserIdReducer(),
                new GetSessionByUserIdCollator());
    }
    
    public static class GetSessionByUserIdMapper implements Mapper<String, Session, String, Session>{
        private static final long serialVersionUID = 1L;

        private String userId;
        
        public GetSessionByUserIdMapper(String userId){
            this.userId = userId;
        }
        
        @Override
        public void map(String key, Session value, Collector<String, Session> collector) {
            if (value.getUserId().equals(userId)){
                collector.emit(value.getUserId(), value);
            }
        }
    }
    
    public static class GetSessionByUserIdReducer implements Reducer<String, Session>{
        private static final long serialVersionUID = 1L;

        @Override
        public Session reduce(String reducedKey, Iterator<Session> iter) {
            if (! iter.hasNext()){
                return null;
            }
            return iter.next();
        }
    }

    public static class GetSessionByUserIdCollator implements Collator<String, Session, Session>{
        @Override
        public Session collate(Map<String, Session> reducedResults)
        {
            Iterator<Session> iterator = reducedResults.values().iterator();
            if (iterator.hasNext()){
                return iterator.next();
            }
            return null;
        }
    }
    
    public Map<String, Session> getAllSessions(String... sessionIds){
        return getAll(sessionIds);
    }
    
    public Map<String, Session> getAllSessions(List<String> sessionIds){
        return getAll(sessionIds);
    }
    
    public List<Session> getAllSessionsByMachineId(){
        return null;
    }
    
    public List<Session> getAllSessionsByRackId(){
        return null;
    }
    
    public List<Session> getAllSessionsBySiteId(){
        return null;
    }
    
    public void put(Session session){
        put(session.getSessionId(), session);
    }
    
    public void remove(Session session){
        remove(session.getSessionId());
    }
}