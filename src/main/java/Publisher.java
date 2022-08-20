import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class Publisher {

	public static void main(String[] args) {

		ConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616" );


		try {
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("demoQueue");

			String [] messages = {"Prva poruka", "Druga poruka", "Treca poruka", "Cetvrta poruka"};

			MessageProducer producer = session.createProducer(destination);

			for (String message : messages) {
				TextMessage textMessage = session.createTextMessage(message);
				producer.send(textMessage);
			}

			System.out.println("Message Published!");

			session.close();
			connection.close();

		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
}
