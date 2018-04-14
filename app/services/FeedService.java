package services;

import data.FeedResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class FeedService {
    public FeedResponse getFeedByQuery(String query){
        FeedResponse feedResponse = new FeedResponse();
        try{
            WSRequest queryRequest = WS.url("https://news.google.com/news");
            CompletionStage<WSResponse> responsePromise = queryRequest
                    .setQueryParameter("query",query)
                    .setQueryParameter("output","rss")
                    .get();
            //
            Document response = responsePromise.thenApply(WSResponse::asXml).toCompletableFuture().get();
            Node item = response.getFirstChild().getChildNodes().item(10);
            feedResponse.title = item.getChildNodes().item(0).getFirstChild().getNodeValue();
           // feedResponse.pubDate = item.getFirstChild().getChildNodes().item(4).toString();
           // feedResponse.description = item.getChildNodes().item(5).toString();
            feedResponse.pubDate = item.getChildNodes().item(4).getFirstChild().getNodeValue();
            feedResponse.description = item.getChildNodes().item(5).getFirstChild().getNodeValue();
        } catch (Exception e){
            e.printStackTrace();
        }
        return feedResponse;
    }

}
