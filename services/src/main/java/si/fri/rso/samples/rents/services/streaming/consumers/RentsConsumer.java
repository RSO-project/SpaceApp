package si.fri.rso.samples.rents.services.streaming.consumers;

import com.kumuluz.ee.streaming.common.annotations.StreamListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import si.fri.rso.samples.rents.services.RentsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.logging.Logger;

@ApplicationScoped
public class RentsConsumer {

    private Logger log = Logger.getLogger(RentsConsumer.class.getName());

    @Inject
    private RentsBean rentsBean;

    @StreamListener(topics = {"9pagnfwv-default"})
    public void onMessage(ConsumerRecord<String, String> record) {

        log.info(String.format("Consumed message: offset = %d, key = %s, value = %s%n", record.offset(), record.key()
                , record.value()));

        JsonReader jsonReader = Json.createReader(new StringReader(record.value()));
        JsonObject data = jsonReader.readObject();
        Integer id = data.getInt("id");
        String status = data.getString("status");

        log.info("Status for rent " + id + " set to " + status);

        rentsBean.setRentStatus(id, status);

    }

}
