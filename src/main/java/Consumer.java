import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

	public static void main(String[] args) {

		ConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616" );


		try {
			Connection connection = factory.createConnection();
			connection.start();		// ovo je za Consumera neophodno za razliku od Publishera koji ne mora da ima stalno aktivno slusanje

			Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			Destination destination = session.createQueue("demoQueue");

			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(new MessageListener() {

				public void onMessage(Message message) {
					TextMessage textMessage = (TextMessage) message;

					try {
						System.out.println(textMessage.getText());
					} catch (JMSException e) {
						throw new RuntimeException(e);
					}
				}
			});

		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
}
