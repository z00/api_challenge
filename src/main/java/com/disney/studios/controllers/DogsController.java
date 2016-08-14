package com.disney.studios.controllers;

import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.disney.studios.domain.Dog;
import com.disney.studios.service.DogsDAO;

@RestController
public class DogsController {

    @Autowired
    private DogsDAO dogsDao;

    @RequestMapping(value = "/breeds", method = RequestMethod.GET, produces = { "application/json" })
    public Callable<List<Dog>> getBreeds() {

        return new Callable<List<Dog>>() {
            @Override
            public List<Dog> call() {
                return dogsDao.getAllBreeds();
            }
        };
    }

    @RequestMapping(value = "/breeds/{breedName}", method = RequestMethod.GET, produces = { "application/json" })
    public Callable<List<Dog>> getByBreedName(@PathVariable String breedName) {

        return new Callable<List<Dog>>() {
            @Override
            public List<Dog> call() {
                return dogsDao.getDogsByBreed(breedName);
            }
        };
    }

    @RequestMapping(value = "/dogs/{id}/upvote", method = RequestMethod.PUT)
    public Callable<Boolean> dogPicUpVote(@PathVariable(value = "id") String id, HttpServletRequest request) {

        return new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return dogsDao.updatePicUpVote(Integer.valueOf(id), request.getRemoteAddr());
            }
        };
    }

    @RequestMapping(value = "/dogs/{id}/downvote", method = RequestMethod.PUT)
    public Callable<Boolean> dogPicDownVote(@PathVariable(value = "id") String id, HttpServletRequest request) {

        return new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return dogsDao.updatePicDownVote(Integer.valueOf(id), request.getRemoteAddr());
            }
        };
    }

    @RequestMapping(value = "/dogs/{id}", method = RequestMethod.GET, produces = { "application/json" })
    public Callable<Dog> getDogById(@PathVariable String id) {

        return new Callable<Dog>() {
            @Override
            public Dog call() {
                return dogsDao.getDogById(Integer.valueOf(id));
            }
        };
    }
}
