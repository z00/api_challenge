package com.disney.studios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.disney.studios.domain.Dog;
import com.disney.studios.service.DogsDAO;

/**
 * Loads stored objects from the file system and builds up the appropriate
 * objects to add to the data source.
 *
 * Created by fredjean on 9/21/15.
 */
@Component
public class PetLoader implements InitializingBean, Ordered {
    // Resources to the different files we need to load.
    @Value("classpath:data/labrador.txt")
    private Resource labradors;

    @Value("classpath:data/pug.txt")
    private Resource pugs;

    @Value("classpath:data/retriever.txt")
    private Resource retrievers;

    @Value("classpath:data/yorkie.txt")
    private Resource yorkies;

    @Autowired
    private DogsDAO  dao;

    /**
     * Load the different breeds into the data source after the application is
     * ready.
     *
     * @throws Exception
     *             In case something goes wrong while we load the breeds.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        loadBreed("Labrador", labradors);
        loadBreed("Pug", pugs);
        loadBreed("Retriever", retrievers);
        loadBreed("Yorkie", yorkies);
    }

    /**
     * Reads the list of dogs in a category and (eventually) add them to the
     * data source.
     *
     * @param breed
     *            The breed that we are loading.
     * @param source
     *            The file holding the breeds.
     * @throws Exception
     *             In case things go horribly, horribly wrong.
     */
    private void loadBreed(String breed, Resource source) throws Exception {
        List<Dog> dogs = new ArrayList<>(64);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(source.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                dogs.add(new Dog(0, null, breed, line));
            }
        }
        dao.bulkInsertDogs(dogs);

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
