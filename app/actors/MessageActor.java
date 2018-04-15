package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import services.FeedService;
import services.NewsAgentService;

import java.util.UUID;

import static data.Message.Sender.BOT;
import static data.Message.Sender.USER;

public class MessageActor extends UntypedActor {

    private final ActorRef out;

    //self reference to the actor
    public MessageActor(ActorRef out) {
        this.out = out;
    }

    //define the props
    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out);
    }

    private NewsAgentService newsAgentServiceObject = new NewsAgentService();
    private FeedService feedServiceObject = new FeedService();

    //define another actor reference

    @Override
    public void onReceive(Object message) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        if (message instanceof String) {
            Message messageObject = new Message();
            messageObject.text = (String) message;
            messageObject.sender = USER;
            //to send the message received by the server to the client
            out.tell(objectMapper.writeValueAsString(messageObject), self());

            NewsAgentResponse newsAgentResponseObject = newsAgentServiceObject.getNewsAgentResponse(messageObject.text, UUID.randomUUID());
            FeedResponse feedResponseObject = feedServiceObject.getFeedByQuery(newsAgentResponseObject.query);
            messageObject.text = feedResponseObject.title == null ? "No results found" : "Showing results for:" + newsAgentResponseObject.query;
            messageObject.feedResponse = feedResponseObject;
            messageObject.sender = BOT;
            out.tell(objectMapper.writeValueAsString(messageObject), self());
        }
    }

}