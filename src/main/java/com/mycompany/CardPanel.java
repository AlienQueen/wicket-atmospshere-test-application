package com.mycompany;

import java.util.UUID;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CardPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(CardPanel.class);

	private final UUID uuid;

	public CardPanel(final String id)
	{
		super(id);
		this.setOutputMarkupId(true);

		this.uuid = UUID.randomUUID();

		final WebMarkupContainer menutoggleButton = new WebMarkupContainer("menutoggleButton");
		menutoggleButton.setOutputMarkupId(true);
		menutoggleButton.setMarkupId("menutoggleButton");

		// menutoggleButton.add(new CardMoveBehavior(this, this.uuid));
		// menutoggleButton.add(new CardRotateBehavior(this, this.uuid));

		// final Image handleImage = new Image("handleImage", new
		// PackageResourceReference(
		// "images/arrow.png"));
		// handleImage.setMarkupId("handleImage" + this.uuid.toString());
		// handleImage.setOutputMarkupId(true);
		//
		// final Image tapHandleImage = new Image("tapHandleImage", new
		// PackageResourceReference(
		// "images/rightArrow.png"));
		// tapHandleImage.setMarkupId("tapHandleImage" + this.uuid.toString());
		// tapHandleImage.setOutputMarkupId(true);

		// final TooltipPanel cardBubbleTip = new TooltipPanel("cardTooltip",
		// bigImage);
		// cardBubbleTip.setOutputMarkupId(true);
		// cardBubbleTip.setMarkupId("cardTooltip" + this.uuid);
		// cardBubbleTip.add(new AttributeModifier("style", "display:none;"));

		final Image cardImage = new Image("cardImage", new PackageResourceReference(HomePage.class,
				"image/BalduvianHorde_small.jpg"));
		cardImage.setOutputMarkupId(true);
		cardImage.setMarkupId("card" + this.uuid.toString());
		menutoggleButton.add(cardImage);

		this.add(menutoggleButton);
	}

	@Override
	public void renderHead(final IHeaderResponse response)
	{
		// final TextTemplate template1 = new
		// PackageTextTemplate(HomePage.class,
		// "script/tooltip/easyTooltip.js");
		// final StringBuffer js = new
		// StringBuffer().append(template1.asString());
		// response.render(JavaScriptHeaderItem.forScript(js.toString(),
		// "easyTooltip.js"));
		//
		// final TextTemplate template2 = new
		// PackageTextTemplate(HomePage.class,
		// "script/tooltip/initTooltip.js");
		// final HashMap<String, Object> variables = new HashMap<String,
		// Object>();
		// variables.put("uuid", this.uuid);
		// template2.interpolate(variables);
		// final StringBuffer js2 = new
		// StringBuffer().append(template2.asString());
		// response.render(JavaScriptHeaderItem.forScript(js2.toString(),
		// "initTooltip.js" + this.uuid));
	}

}
