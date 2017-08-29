package org.etri.slice.core.rule.internal;

import java.io.File;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.apache.felix.ipojo.extender.DeclarationBuilderService;
import org.apache.felix.ipojo.extender.DeclarationHandle;
import org.apache.felix.ipojo.extender.InstanceBuilder;
import org.etri.slice.core.rule.ProductionMemory;
import org.etri.slice.core.rule.RuleEngine;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.scanner.MavenRepository;
import org.osgi.service.log.LogService;

@Component(publicFactory=false, immediate=true)
@Provides
public class ProductionMemoryImpl implements ProductionMemory {

	@Property(name="KieContainer")
	private KieContainer m_container;
	
	@Requires
	private DeclarationBuilderService m_builder;	
	
	@Requires
	private RuleEngine m_engine;
	
	@Property(name="logger")
	private LogService m_logger;
	
	private MavenRepository m_repository;
	
	@Override
	public String getCurrentVersion() {
		ReleaseId releaseId = m_engine.getReleaseId();
		return releaseId.getVersion();
	}

	@Override
	public void install(String version, byte[] jarContent, byte[] pomContent) {
		ReleaseId releaseId = m_engine.newReleaseId(version);
		m_repository.installArtifact(releaseId, jarContent, pomContent);
	}

	@Override
	public void install(String version, File jar, File pomFile) {
		ReleaseId releaseId = m_engine.newReleaseId(version);
		m_repository.installArtifact(releaseId, jar, pomFile);
	}

	@Override
	public void update(String version, String... rules) {
		ReleaseId releaseId = m_engine.newReleaseId(version);
	}

	@Validate
	public void start() {
//		m_logger.log(LogService.LOG_INFO, this.getClass().getName() + " started.");
		System.out.println(this.getClass().getName() + " started.");
		ReleaseId releaseId = m_engine.getReleaseId();
		System.out.println(releaseId);
		m_repository = MavenRepository.getMavenRepository();		
		KieSession kieSession = m_container.newKieSession();
		
		InstanceBuilder builder = m_builder.newInstance(WorkingMemoryImpl.class.getName());
		DeclarationHandle handle = builder.name(KieSession.class.getName()).configure()
																				.property("KieSession", kieSession)
																				.build();
		handle.publish();
	}
	
	@Invalidate
	public void stop() {
		
	}
		
}
