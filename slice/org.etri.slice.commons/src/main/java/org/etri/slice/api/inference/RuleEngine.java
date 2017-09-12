package org.etri.slice.core.rule;

import org.kie.api.builder.ReleaseId;

public interface RuleEngine {
	
	void start();
	
	void stop();	
	
	ReleaseId getReleaseId();
	
	ReleaseId newReleaseId(String version);

}
