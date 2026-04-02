package dev.maksymzapisov.jsonplaceholder.model;

public class Comment {

    private Object postId;
    private Object id;
    private String name;
    private String email;
    private String body;

    public Comment() {}

    public Comment(Object postId, Object id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public Object getPostId() { return postId; }
    public void setPostId(Object postId) { this.postId = postId; }

    public Object getId() { return id; }
    public void setId(Object id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    @Override
    public String toString() {
        return "Comment{postId=" + postId + ", id=" + id + ", name='" + name +
                "', email='" + email + "', body='" + body + "'}";
    }
}