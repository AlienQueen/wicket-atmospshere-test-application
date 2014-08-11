/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mycompany;

import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.atmosphere.EventBus;
import org.apache.wicket.atmosphere.Subscribe;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private Component timeLabel;
	private Component messageLabel;
	private TextField<String> input;

	WebMarkupContainer parent;
	int placeholder = 0;

	public HomePage(final PageParameters parameters)
	{
		super(parameters);

		this.add(this.timeLabel = new Label("time", Model.of("start")).setOutputMarkupId(true));
		this.add(this.messageLabel = new Label("message", Model.of("-")).setOutputMarkupId(true));

		final Form<Void> form = new Form<Void>("form");
		this.add(form);
		form.add(this.input = new TextField<String>("input", Model.of("")));
		form.add(new AjaxSubmitLink("send", form)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form)
			{
				final List<String> allClients = WicketApplication.get().getUuids();
				for (final String client : allClients)
				{
					EventBus.get().post(HomePage.this.input.getModelObject(), client);
				}
			}

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form)
			{
			}
		});

		this.parent = new WebMarkupContainer("parent");
		this.parent.setOutputMarkupId(true);
		this.parent.setMarkupId("parent");

		form.add(new AjaxSubmitLink("addCard", form)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form)
			{
				EventBus.get().post(++HomePage.this.placeholder);
			}

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form)
			{
			}
		});

		for (int i = 1; i <= 10; i++)
		{
			final WebMarkupContainer cardPlaceholder = new WebMarkupContainer("card" + i);
			cardPlaceholder.setOutputMarkupId(true);
			cardPlaceholder.setMarkupId("card" + i);
			this.parent.add(cardPlaceholder);
		}

		form.add(this.parent);

		this.setVersioned(false);
	}

	@Subscribe
	public void updateTime(final AjaxRequestTarget target, final Date event)
	{
		this.timeLabel.setDefaultModelObject(event.toString());
		target.add(this.timeLabel);
	}

	@Subscribe
	public void receiveMessage(final AjaxRequestTarget target, final String message)
	{
		this.messageLabel.setDefaultModelObject(message);
		target.add(this.messageLabel);
	}

	@Subscribe
	public void addCard(final AjaxRequestTarget target, final Integer _placeholder)
	{
		if (_placeholder < 10)
		{
			final CardPanel card = new CardPanel("card" + _placeholder);
			card.setOutputMarkupId(true);
			card.setMarkupId("card" + _placeholder);

			HomePage.this.parent.addOrReplace(card);
			target.add(HomePage.this.parent);
		}
	}
}
