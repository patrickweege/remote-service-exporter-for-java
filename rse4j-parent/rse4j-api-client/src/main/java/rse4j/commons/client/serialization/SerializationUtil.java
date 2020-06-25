package rse4j.commons.client.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class SerializationUtil {

	public static <T extends Serializable> byte[] serialize(Object object) {

		try (ByteArrayOutputStream baout = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(baout)) {

			out.writeObject(object);
			out.flush();
			out.close();

			return baout.toByteArray();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void serialize(Object object, OutputStream out) {
		try(ObjectOutputStream oout = new ObjectOutputStream(out)) {
			oout.writeObject(object);
			oout.flush();
			oout.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static <T extends Serializable> T deserialize(byte[] bytes) {
		try (ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bain)) {

			return (T) ois.readObject();
			
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
