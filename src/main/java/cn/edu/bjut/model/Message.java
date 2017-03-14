package cn.edu.bjut.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/14.
 */
public class Message {
    private int id;
    private int toId;
    private int fromId;
    private String content;
    private String conversationId;
    private int hasRead;
    private Date createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId() {
        if(fromId < toId)
            this.conversationId = String.format("%d_%d",fromId,toId);
        else
            this.conversationId = String.format("%d_%d",toId,fromId);
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
