package hu.elte.eventcenter;

import hu.elte.eventcenter.model.Event;
import hu.elte.eventcenter.model.User;
import java.util.List;
import static org.assertj.core.api.Java6Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;




@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerRestTest {

        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

        private String getTokenForUser(String username, String password) throws Exception {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            ResponseEntity<String> responseAuth = restTemplate.postForEntity("/api/auth", user, String.class);
            String jsonString = responseAuth.getBody();
            JSONObject json2 = new JSONObject(jsonString);
            return json2.getString("token");
        }

        private HttpEntity getRequestEntityForUser(String username, String password) throws Exception {
            String token = getTokenForUser(username, password);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", "token=" + token);
            return new HttpEntity(null, headers);
        }

        @Test
        public void shouldReturnAllEventsForUser() throws Exception {
            System.out.println("Test 1");
            HttpEntity requestEntity = getRequestEntityForUser("user", "user");
            ResponseEntity<List<Event>> response = restTemplate.exchange("http://localhost:" + port + "/events/My", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Event>>() {});
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().size()).isEqualTo(1);
        }

        @Test
        public void shouldReturnAllEvents() throws Exception {
            System.out.println("Test 2");
            HttpEntity requestEntity = getRequestEntityForUser("user", "user");
            ResponseEntity<List<Event>> response = restTemplate.exchange("http://localhost:" + port + "/events/Global", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Event>>() {});
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().size()).isEqualTo(2);
        }

        /*@Test
        public void shouldReturnTheSecondEventAsString() throws Exception {
            System.out.println("Test 3");
            HttpEntity requestEntity = getRequestEntityForUser("user", "user");
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/events/2", HttpMethod.GET, requestEntity, String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            JSONAssert.assertEquals("{title:Budapest Zoo & Botanical Garden,description:The Budapest Zoo, built in 1866, is the oldest and largest zoo park of Hungary}", response.getBody(), false);
        }*/


}


