package org.rasea.core.manager;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rasea.core.domain.Application;

import br.gov.frameworkdemoiselle.util.DemoiselleRunner;

@Ignore
@RunWith(DemoiselleRunner.class)
public class ApplicationManagerTest {

	@Inject
	private ApplicationManager manager;
	
	private Application getNewFakeApplicationInstance() {
		Application app = new Application("fakeapp");
		app.setDisplayName("Fake Application");
		app.setUrl("http://fakeapp.com");
		app.getOwners().add("fakeuser");
		return app;
	}

	@Test
	public void createApplicationSuccessfully() {
		Application app = getNewFakeApplicationInstance();

		manager.create(app);
		Application persisted = manager.findByName(app.getName());

		assertNotNull(persisted);
		assertEquals(app.getName(), persisted.getName());
		assertEquals(app.getDisplayName(), persisted.getDisplayName());
		assertEquals(app.getUrl(), persisted.getUrl());
		assertNull(persisted.getAccessCount());
		assertNotNull(app.getOwners());
		assertEquals(1, app.getOwners().size());

		manager.delete(app);
	}

	@Test
	public void deleteApplicationSuccessfully() {
		Application app = getNewFakeApplicationInstance();

		manager.create(app);
		manager.delete(app);

		Application persisted = manager.findByName(app.getName());
		assertNull(persisted);
	}

}
