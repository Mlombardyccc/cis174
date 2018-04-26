package edu.yccc.cis174.michaellombard.slack;

import org.springframework.stereotype.Component;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;

/**
 *   Michael Lombard
 */


@Component

public class SlackService 
{
	// #integration webhook url.  Create your own channel and add the webhook app.  Then update this with your value.
	private String webHookUrl = "https://hooks.slack.com/services/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";
	private SlackApi api = new SlackApi(webHookUrl);

	public void sendMessage(String channel, String userName, String message) 
	{
		//System.out.println(channel + "," + userName + "," + message);
		api.call(new SlackMessage(channel, userName, message));
	}
}