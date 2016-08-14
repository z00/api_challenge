package com.disney.studios.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.disney.studios.domain.Dog;
import com.disney.studios.domain.VoteInfo;

@Repository
public class DogsDAO {

    private static final String DOWNVOTE_UPDATE  = "insert into voteInfo values (default, ?,default, 1, ?)";
    private static final String UPVOTE_UPDATE    = "insert into voteInfo values (default, ?,1, default, ?)";
    private static final String ALL_BREEDS_QUERY = "select * from dogs order by breedName";
    private static final String By_BREED_QUERY   = "select * from dogs where breedName = ?";
    private static final String DOG_BY_ID        = "select * from dogs where id = ?";

    private static final String VOTES_BY_BREED   = "select * from VoteInfo v join dogs d on v.dogId = d.id where d.breedName = ? order by v.picUpVote";
    private static final String VOTES_BY_DOGID   = "select * from VoteInfo v join dogs d on v.dogId = d.id where d.id = ? order by v.picUpVote";
    private static final String VOTES_QUERY      = "select * from VoteInfo";

    @Autowired
    private JdbcTemplate        jdbcTemplate;

    public class DogRowMapper implements RowMapper<Dog> {

        @Override
        public Dog mapRow(ResultSet rs, int rownumber) throws SQLException {
            return new Dog(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
        }
    }

    public class VoteInfoRowMapper implements RowMapper<VoteInfo> {

        @Override
        public VoteInfo mapRow(ResultSet rs, int rownumber) throws SQLException {
            return new VoteInfo(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5));
        }
    }

    public List<Dog> getAllBreeds() {
        List<Dog> dogs = jdbcTemplate.query(ALL_BREEDS_QUERY, new DogRowMapper());
        List<VoteInfo> votes = jdbcTemplate.query(VOTES_QUERY, new VoteInfoRowMapper());

        return consolidate(dogs, votes);
    }

    public List<Dog> getDogsByBreed(String breedName) {
        List<Dog> dogs = jdbcTemplate.query(By_BREED_QUERY, new Object[] { breedName }, new DogRowMapper());
        List<VoteInfo> votes = jdbcTemplate.query(VOTES_BY_BREED, new Object[] { breedName }, new VoteInfoRowMapper());

        return consolidate(dogs, votes);
    }

    private List<Dog> consolidate(List<Dog> dogs, List<VoteInfo> votes) {
        for (Dog dog : dogs) {
            for (VoteInfo vote : votes) {
                if (dog.getId() == vote.getDogId()) {
                    dog.addVote(vote);
                }
            }
        }

        return dogs;
    }

    public Dog getDogById(int dogId) {
        Dog dog = jdbcTemplate.queryForObject(DOG_BY_ID, new Object[] { dogId }, new DogRowMapper());
        List<VoteInfo> votes = jdbcTemplate.query(VOTES_BY_DOGID, new Object[] { dogId }, new VoteInfoRowMapper());
        votes.forEach(v -> {
            dog.addVote(v);
        });
        return dog;
    }

    /**
     * Increment up vote column by 1
     *
     * @param dogId
     * @return true if any rows were updated
     */
    public boolean updatePicUpVote(int dogId, String voteClientId) {
        int res = this.jdbcTemplate.update(UPVOTE_UPDATE, dogId, voteClientId);
        return res > 0;
    }

    /**
     * Increment down vote column by 1
     *
     * @param dogId
     * @return true if any rows were updated
     */
    public boolean updatePicDownVote(int dogId, String voteClientId) {
        int res = this.jdbcTemplate.update(DOWNVOTE_UPDATE, dogId, voteClientId);
        return res > 0;
    }

    /**
     * Bulk insert dogs into DB for PetLoader
     *
     * @param dogs
     */
    public void bulkInsertDogs(List<Dog> dogs) {

        String dogsSQL = "INSERT INTO DOGS (name, breedName, picURL) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(dogsSQL, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Dog dog = dogs.get(i);
                ps.setString(1, dog.getName());
                ps.setString(2, dog.getBreedName());
                ps.setString(3, dog.getPicURL());
            }

            @Override
            public int getBatchSize() {
                return dogs.size();
            }
        });

        // String voteSQL = "INSERT INTO VOTEINFO (dogId, picUpVote,
        // picDownVote, voteClientId) VALUES (?, ?, ?, ?)";
        // jdbcTemplate.batchUpdate(voteSQL, new BatchPreparedStatementSetter()
        // {
        //
        // @Override
        // public void setValues(PreparedStatement ps, int i) throws
        // SQLException {
        // Dog dog = dogs.get(i);
        // ps.setInt(1, dog.getId());
        // ps.setInt(2, 0);
        // ps.setInt(3, 0);
        // ps.setString(4, "");
        // }
        //
        // @Override
        // public int getBatchSize() {
        // return dogs.size();
        // }
        // });
    }
}
