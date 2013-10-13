package org.rgn.jms.richards.ch02.chat;

public class RokkrrChat extends Chat {

	public RokkrrChat(String topicFactory, String topicName, String username)
			throws Exception {
		super(topicFactory, topicName, username);
	}

	public static void main(String[] args) throws Exception {
		Chat chat = new Chat("TopicCF", "topic1", "rokkrr");

		chat.performChat();
	}

}
