package com.sqli.informationsREST.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class FeedBack {
    @Id
    private ObjectId _id;
    private String searchSentence;
    private int feedBack;
    private String comment;

    public FeedBack() {
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getSearchSentence() {
        return searchSentence;
    }

    public void setSearchSentence(String searchSentence) {
        this.searchSentence = searchSentence;
    }

    public int getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(int feedBack) {
        this.feedBack = feedBack;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
