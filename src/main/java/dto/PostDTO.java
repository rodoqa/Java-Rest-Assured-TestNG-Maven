package dto;

public class PostDTO {
    private int post_author;
    private String post_format;
    private String post_title;
    private String post_content;
    private String post_status;

    public int getAuthor() {
        return post_author;
    }

    public String getFormat() {
        return post_format;
    }

    public String getTitle() {
        return post_title;
    }

    public String getPostContent() {
        return post_content;
    }

    public String getStatus() {
        return post_status;
    }

    public void setAuthor(int author) {
        this.post_author = author;
    }

    public void setFormat(String format) {
        this.post_format = format;
    }

    public void setTitle(String title) {
        this.post_title = title;
    }

    public void setPostContent(String content) {
        this.post_content = content;
    }

    public void setStatus(String status) {
        this.post_status = status;
    }

    public PostDTO() {
    }

    public void getPostDTO() {
        System.out.println(getTitle() + "\n" + getPostContent() + "\n" + getAuthor() + "\n" + getStatus() + "\n" + getFormat());
    }
}
