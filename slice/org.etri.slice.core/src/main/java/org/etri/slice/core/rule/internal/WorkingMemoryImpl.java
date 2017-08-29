package org.etri.slice.core.rule.internal;

import java.util.Collection;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.etri.slice.core.rule.WorkingMemory;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;
import org.kie.api.runtime.rule.FactHandle;
import org.osgi.service.log.LogService;

@Component(publicFactory=false, immediate=true)
@Provides
public class WorkingMemoryImpl implements WorkingMemory {

	@Property(name="KieSession")
	private KieSession m_session;
	
//	@Requires(proxy=false)
//	private LogService log;

	@Override
	public void insert(Object fact) {
		m_session.insert(fact);
	}

	@Override
	public void delete(Object fact) {
		FactHandle handle = m_session.getFactHandle(fact);
		m_session.delete(handle);
	}

	@Override
	public void update(Object before, Object after) {
		FactHandle handle = m_session.getFactHandle(before);
		m_session.update(handle, after);
	}

	@Override
	public long getFactCount() {
		
		return m_session.getFactCount();
	}

	@Override
	public Collection<? extends Object> getObjects() {
		
		return m_session.getObjects();
	}

	@Override
	public Collection<? extends Object> getObjects(ObjectFilter filter) {
		
		return m_session.getObjects(filter);
	}

	@Validate
	public void start() {
		System.out.println(this.getClass().getName() + " started.");
//		log.log(LogService.LOG_INFO, this.getClass().getName() + " started.");
		
	}
	
	@Invalidate
	public void stop() {
		
	}
		
	
}
