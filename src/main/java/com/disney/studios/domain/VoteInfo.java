package com.disney.studios.domain;

public class VoteInfo {

    private int    picUpVote;
    private int    picDownVote;
    private String voteClientId;
    private int    dogId;
    private int    id;

    public VoteInfo(int id, int dogId, int picUpVote, int picDownVote, String voteClientId) {
        this.picUpVote = picUpVote;
        this.picDownVote = picDownVote;
        this.voteClientId = voteClientId;
        this.dogId = dogId;
        this.id = id;
    }

    public int getPicUpVote() {
        return picUpVote;
    }

    public int getPicDownVote() {
        return picDownVote;
    }

    public String getVoteClientId() {
        return voteClientId;
    }

    public int getDogId() {
        return dogId;
    }

    public int getId() {
        return id;
    }

}
