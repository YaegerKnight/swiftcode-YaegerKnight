package services;

import com.fasterxml.jackson.databind.JsonNode;
import data.NewsAgentResponse;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class NewsAgentService {
    //NewsAgentResponse is the class that handles the response from DialogFlow i.e the NLP parsed
    //context of the input query.
   public NewsAgentResponse getNewsAgentResponse(String query,UUID sessionId){

       NewsAgentResponse newsAgentResponse = new NewsAgentResponse();
       try {
           //This command is used to wrap a webSocket around a regular HTTP request.
           WSRequest queryRequest = WS.url("https://api.api.ai/api/query");
           //Promise beginning
            CompletionStage<WSResponse> responsePromise = queryRequest
                    .setQueryParameter("v","20150910")
                    .setQueryParameter("query",query)
                    .setQueryParameter("lang", "en")
                    .setQueryParameter("sessionId",sessionId.toString())
                    .setQueryParameter("timezone","2018-13-04T10:29:23+0530")
                    .setHeader("Authorization","Bearer 054a388ef08e46c3beb61cd9a12dd13f")
                    .get();
            //Promise Ending

           //Future definition
            JsonNode response= responsePromise.thenApply(WSResponse::asJson).toCompletableFuture().get();
            newsAgentResponse.query = response.get("result").get("parameters").get("keyword").asText().isEmpty()?
                    (response.get("result").get("parameters").get("source").asText().isEmpty()?
                            response.get("result").get("parameters").get("category").asText():
                            response.get("result").get("parameters").get("source").asText()):(response.get("result").get("parameters").get("keyword").asText());

       } catch (InterruptedException e) {
           e.printStackTrace();
       } catch (ExecutionException e) {
           e.printStackTrace();
       }
       return newsAgentResponse;
   }
   

}