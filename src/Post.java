
public class Post implements Comparable<Post> {
    String postId;
    String content;
    String author;
    int likeCount;

    public Post(String postId, String content, String author) {
        this.author = author;
        this.postId = postId;
        this.content = content;
        this.likeCount = 0; // like count is 0 when post is first created
    }

    public int compareTo(Post other) { // method to compare two posts
        if (this.likeCount != other.likeCount) {
            return Integer.compare(this.likeCount, other.likeCount); // compares the like counts
        }
        return this.postId.compareTo(other.postId); // lexicographical comparison
    }
}
