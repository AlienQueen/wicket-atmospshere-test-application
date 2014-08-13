package com.mycompany;

import org.apache.wicket.atmosphere.tester.AtmosphereTester;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AtmosphereUnitTest
{
	protected static WicketTester tester;
	protected String pageDocument;

	@BeforeClass
	public static void setUpBeforeClass()
	{
		AtmosphereUnitTest.tester = new WicketTester();// AtmosphereUnitTest.webApp);
	}

	@Test
	public void testAtmosphere()
	{
		final AtmosphereTester at = new AtmosphereTester(AtmosphereUnitTest.tester, new HomePage(
				new PageParameters()));
		AtmosphereUnitTest.tester.assertRenderedPage(HomePage.class);

		final FormTester form = AtmosphereUnitTest.tester.newFormTester("form");
		form.setValue("input", "Atmosphere rocks!");

		AtmosphereUnitTest.tester.clickLink("form:send", true);
		this.pageDocument = AtmosphereUnitTest.tester.getLastResponse().getDocument();
		System.out.println(this.pageDocument);

		Assert.assertFalse("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ajax-response></ajax-response>"
				.equals(this.pageDocument));

		AtmosphereUnitTest.tester.assertComponentOnAjaxResponse("message");
		AtmosphereUnitTest.tester.assertLabel("message", "Atmosphere rocks!");

		Assert.assertFalse(this.pageDocument
				.endsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ajax-response></ajax-response>"));
	}

}
