package com.disney.studios.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Hold a dog's info. Denormalized. Since each dog has 1 pic, no need to put pic
 * in a different table.
 *
 * @author hzineddin
 *
 */
public class Dog {

    private int            id;
    private String         name;
    private String         breedName;
    private String         picURL;
    private List<VoteInfo> votes = new ArrayList<>();

    public Dog() {
    }

    public Dog(int id, String name, String breedName, String picURL) {
        this.id = id;
        this.breedName = breedName;
        this.picURL = picURL;
        this.name = name;
        votes = new ArrayList<>();
    }

    // public Dog(int id, String name, String breedName, String picURL,
    // List<VoteInfo> votes) {
    // this.id = id;
    // this.name = name;
    // this.breedName = breedName;
    // this.picURL = picURL;
    // this.votes = votes;
    // }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreedName() {
        return breedName;
    }

    public String getPicURL() {
        return picURL;
    }

    @Override
    public String toString() {
        return "Dog [id=" + id + ", breedName=" + breedName + ", picURL=" + picURL + "] " + votes.toString();
    }

    public List<VoteInfo> getVotes() {
        return votes;
    }

    public void addVote(VoteInfo vote) {
        this.votes.add(vote);
    }

}
