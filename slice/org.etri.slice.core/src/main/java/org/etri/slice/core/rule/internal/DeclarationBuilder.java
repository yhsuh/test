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
import org.etri.slice.core.rule.InstancesBuilder;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.osgi.framework.BundleContext;

@Component(publicFactory=false, immediate=true)
@Provides
@Instantiate
public class DeclarationBuilder implements InstancesBuilder {

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
	
	private DeclarationHandle m_handle;
	private KieServices m_ks;	
	
	@Validate
	public void start() {
//		m_ks = KieServices.Factory.get();
//		ReleaseId releaseId = m_ks.newReleaseId(m_groupId, m_artifactId, m_version);
//		KieContainer kieContainer = m_ks.newKieContainer(releaseId);
//		
//		InstanceBuilder builder1 = m_builder.newInstance(RuleEngineImpl.class.getName());		
//		DeclarationHandle handle = builder1.name(RuleEngineImpl.class.getName()).configure()
//																					.property("groupId", "org.etri.slice")
//																					.property("artifiactId", "org.etri.slice.rules.aircon")
//																					.property("version", "0.0.1")
//																					.build();
//		handle.publish();
//		
//		InstanceBuilder builder = m_builder.newInstance(ProductionMemoryImpl.class.getName());
//		m_handle = builder.name(KieContainer.class.getName()).configure()
//																	.property("KieContainer", kieContainer)
//																	.build();
//		m_handle.publish();
	}
	
	@Invalidate
	public void stop() {
		m_handle.retract();
	}
}	

