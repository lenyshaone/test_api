
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class apitest {
    public static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return
                given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                        when().get(endpoint).
                        then().contentType(ContentType.JSON).extract().response();
    }

    public static void main(String[] args) {
        Response response = doGetRequest("https://cat-fact.herokuapp.com/facts");

        List<Map<String, String>> al = response.jsonPath().getList("all");
        int kolvo= al.size();
        String [] users=new String[kolvo];
        int k=0;

        List<Map<String, String>> user = response.jsonPath().getList("all.user.name");
        List<Map<String, String>> text = response.jsonPath().getList("all");

        int max=0;
        int maxuser=0;
        for(int i=0; i<kolvo-1; i++) {
            users[i] =  user.get(i).get("first") + " " + user.get(i).get("last");
            System.out.println("Fact "+i+": "+text.get(i).get("text"));
        }
        int h;
        for(h=0; h<kolvo-1; h++) {
            for (int i = 0; i < kolvo - 1; i++) {
                if (users[h].equals(users[i])) {
                    k = k + 1;
                }
            }
            if (k > max) {
                max = k;
                maxuser=h;


            }
            k = 0;
        }

        System.out.println("Most Facts from:  "+users[maxuser]);
    }

}



