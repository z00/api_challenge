package com.disney.studios;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testBreeds() {

        String s = this.restTemplate.getForObject("/dogs/5", String.class);
        System.out.println(s);

        // TODO write custom HTTP msg converter so below would work.

        // Dog dog = this.restTemplate.getForObject("/dogs/5", Dog.class);
        // Assert.assertEquals(dog.getId(), 5);
        // Assert.assertEquals(dog.getVotes().size(), 0);
        //
        // this.restTemplate.put("/dogs/5/upvote", String.class);
        // this.restTemplate.put("/dogs/5/downvote", String.class);
        //
        // Dog dog2 = this.restTemplate.getForObject("/dogs/5", Dog.class);
        // Assert.assertEquals(dog2.getId(), 5);
        // Assert.assertEquals(dog2.getVotes().size(), 2);

        // can test other get methods as well
    }
}
