package com.mycompany;

import org.apache.wicket.atmosphere.EventBus;
import org.apache.wicket.atmosphere.EventBusMock;
import org.apache.wicket.atmosphere.MapperContextMock;
import org.apache.wicket.atmosphere.config.AtmosphereLogLevel;
import org.apache.wicket.atmosphere.config.AtmosphereTransport;
import org.apache.wicket.core.request.mapper.IMapperContext;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AtmosphereUnitTest
{
	public static WicketApplication webApp;
	protected static transient WicketTester tester;
	protected static String pageDocument;

	@BeforeClass
	public static void setUpBeforeClass()
	{
		AtmosphereUnitTest.webApp = new WicketApplication()
		{
			private static final long serialVersionUID = 1L;
			private MapperContextMock mapperContext;

			@Override
			public void init()
			{
				this.mapperContext = new MapperContextMock();
				this.eventBus = new EventBusMock(this, this.mapperContext);
				this.eventBus.addRegistrationListener(this);
				this.eventBus.getParameters().setTransport(AtmosphereTransport.WEBSOCKET);
				this.eventBus.getParameters().setLogLevel(AtmosphereLogLevel.DEBUG);
			}

			@Override
			public EventBus getEventBus()
			{
				return this.eventBus;
			}

			@Override
			protected IMapperContext newMapperContext()
			{
				return this.mapperContext;
			}

		};

		AtmosphereUnitTest.tester = new WicketTester(AtmosphereUnitTest.webApp);
	}

	@Test
	public void testAtmosphere()
	{
		final EventBusMock bus = ((EventBusMock)AtmosphereUnitTest.webApp.getEventBus());
		bus.registerPage(bus.getResource().uuid(),
				AtmosphereUnitTest.tester.startPage(HomePage.class));
		AtmosphereUnitTest.tester.assertRenderedPage(HomePage.class);

		final FormTester form = AtmosphereUnitTest.tester.newFormTester("form");
		form.setValue("input", "Atmosphere rocks!");

		AtmosphereUnitTest.tester.clickLink("form:send", true);
		AtmosphereUnitTest.pageDocument = AtmosphereUnitTest.tester.getLastResponse().getDocument();
		System.out.println(AtmosphereUnitTest.pageDocument);

		Assert.assertFalse("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ajax-response></ajax-response>"
				.equals(AtmosphereUnitTest.pageDocument));
	}

}
