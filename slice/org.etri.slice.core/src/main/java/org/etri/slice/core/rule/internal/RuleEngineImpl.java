package org.etri.slice.core.rule.internal;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Context;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.extender.DeclarationBuilderService;
import org.apache.felix.ipojo.extender.DeclarationHandle;
import org.apache.felix.ipojo.extender.InstanceBuilder;
import org.apache.felix.ipojo.extender.InstanceDeclaration;
import org.etri.slice.core.rule.ProductionMemory;
import org.etri.slice.core.rule.RuleEngine;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate
public class RuleEngineImpl implements RuleEngine {
	
	@Property(name="groupId", value="org.etri.slice")
	private String m_groupId;
	
	@Property(name="artifiactId", value="org.etri.slice.rules.aircon")
	public String m_artifactId;	
	
	@Property(name="version", value="0.0.1")
	public String m_version;
	
	@Context
	private BundleContext m_context;
	
	@Requires
	private DeclarationBuilderService m_builder;
	
//	@Requires
//	private ProductionMemory m_pm;
	
//	@Requires
//	private ServiceRegistry m_registry;
	
//	@Requires
//	private LogService m_logger;	
	
	private DeclarationHandle m_handle;
	private KieServices m_ks;
	private ReleaseId m_releaseId;
	
	@Override
	public ReleaseId getReleaseId() {
		return m_releaseId;
	}

	@Override
	public ReleaseId newReleaseId(String version) {
		return m_ks.newReleaseId(m_groupId, m_artifactId, version);
	}

	@Override
	@Validate
	public void start() {
		m_ks = KieServices.Factory.get();
		m_releaseId = m_ks.newReleaseId(m_groupId, m_artifactId, m_version);
		KieContainer kieContainer = m_ks.newKieContainer(m_releaseId);
				
		InstanceBuilder builder = m_builder.newInstance(ProductionMemoryImpl.class.getName());
		m_handle = builder.name(KieContainer.class.getName()).configure()
																	.property("KieContainer", kieContainer)
																	.build();
		m_handle.publish();
		ServiceReference ref = m_context.getServiceReference(InstanceDeclaration.class.getName());
		InstanceDeclaration instance = (InstanceDeclaration) m_context.getService(ref);
		ProductionMemory pm = (ProductionMemory) instance.getConfiguration().get(ProductionMemoryImpl.class.getName());
		String xxx = pm.getCurrentVersion();
	}
	
	@Override
	@Invalidate
	public void stop() {
//		m_handle.retract();
	}
}
