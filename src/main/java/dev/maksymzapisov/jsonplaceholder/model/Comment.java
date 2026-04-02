package dev.maksymzapisov.jsonplaceholder.model;

import java.util.Objects;

public class Comment {

    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    public Comment() {}

    public Comment(int postId, int id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return postId == comment.postId && id == comment.id &&
                Objects.equals(name, comment.name) &&
                Objects.equals(email, comment.email) &&
                Objects.equals(body, comment.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, id, name, email, body);
    }

    @Override
    public String toString() {
        return "Comment{postId=" + postId + ", id=" + id + ", name='" + name +
                "', email='" + email + "', body='" + body + "'}";
    }
}