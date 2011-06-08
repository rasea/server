package org.rasea.core.util;

import static junit.framework.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.util.DemoiselleRunner;

@RunWith(DemoiselleRunner.class)
public class AmazonUtilTest {

	@Inject
	private AmazonUtil util;

	@Test
	public void accessKeyEnvironmentVariableWasDefinedInSystem() {
		assertNotNull(util.getAccessKey());
	}

	@Test
	public void secretKeyEnvironmentVariableWasDefinedInSystem() {
		assertNotNull(util.getSecretKey());
	}
}
