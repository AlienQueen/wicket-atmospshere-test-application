package com.mycompany;

import org.apache.wicket.atmosphere.tester.AtmosphereTester;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class AtmosphereUnitTest {

	@Test
	public void testAtmosphere() {
		WicketTester tester = new WicketTester(new WicketApplication());
		AtmosphereTester waTester = new AtmosphereTester(tester, new HomePage(new PageParameters()));
		tester.assertRenderedPage(HomePage.class);

		final FormTester form = tester.newFormTester("form");
		form.setValue("input", "Atmosphere rocks!");
		tester.clickLink("form:send", true);

		String atmosphereResponse = waTester.getPushedResponse();
		waTester.switchOnTestMode();
		System.out.println(atmosphereResponse);
		tester.assertComponentOnAjaxResponse("message");
		tester.assertLabel("message", "Atmosphere rocks!");

		waTester = new AtmosphereTester(tester, new HomePage(new PageParameters()));
		final FormTester form2 = tester.newFormTester("form");
		form2.setValue("input", "");
		form2.submit("addCard");

		atmosphereResponse = waTester.getPushedResponse();
		waTester.switchOnTestMode();
		System.out.println(atmosphereResponse);
		tester.assertComponentOnAjaxResponse("parent");
		WebMarkupContainer wmc = (WebMarkupContainer) tester.getComponentFromLastRenderedPage("parent");
		assertFalse(wmc.hasErrorMessage());
	}

}
