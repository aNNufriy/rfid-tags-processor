package ru.testfield.tags.service.consumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.testfield.tags.model.RFIDNotification;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

public class PostXMLListConsumer<V,T extends List<V>> implements Consumer<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String tfsRecieverUrl;

    public PostXMLListConsumer(String receiverUrl) {
        this.tfsRecieverUrl = receiverUrl;
    }

    @Override
    public void accept(T pack) {
        logger.debug("Sending pack of size: " + pack.size());
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            Marshaller marshaller = JAXBContext.newInstance(RFIDNotification.class).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(pack, stringWriter);
            HttpPost httppost = new HttpPost(tfsRecieverUrl);
            httppost.setEntity(new ByteArrayEntity(stringWriter.toString().getBytes(StandardCharsets.UTF_8)));
            HttpResponse response = httpclient.execute(httppost);
            logger.debug("Sent pack of size: {}, response: {}", pack.size(), response.getStatusLine());
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        } catch (JAXBException e) {
            logger.error("JAXBException: " + e.getMessage());
        }
    }
}
