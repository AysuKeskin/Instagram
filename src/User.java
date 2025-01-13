import java.util.ArrayList;

public class User {
    String userId;
    ArrayList<User> following_array; // list of users that user follows
    MySet<User> following_set; // set of users that user follows
    MySet<Post> seen_posts; // set of posts that user has seen
    MySet<Post> liked_posts; // set of posts that user liked
    ArrayList<Post> users_posts_array; // list of posts of the user

    public User(String userId) {
        this.userId = userId;
        seen_posts = new MySet<>();
        users_posts_array = new ArrayList<>();
        liked_posts = new MySet<>();
        following_set = new MySet<>();
        following_array = new ArrayList<>();
    }
    public boolean follow(User user){
        if (following_set.contains(user) || this == user)
            return false;
        following_set.put(user);
        following_array.add(user);
        return true;
    }
    public boolean unfollow(User user){
        if ( ! following_set.contains(user)) // if user is not followed
            return false;
        following_set.remove(user);
        following_array.remove(user);
        return true; // unfollowing has been successful
    }
    public void create_post(Post post){ // adds a new post
        users_posts_array.add(post);
    }
    public void see_post(Post post){
        seen_posts.put(post);
    }
    public void see_all_posts(User user){
        for(Post post:user.users_posts_array){ // iterate through users post and see all
            seen_posts.put(post);
        }
    }
    public int like(Post post){ // changes the status of like
        if (liked_posts.contains(post)){ // if post is liked take back the like
            //unlikes
            liked_posts.remove(post);
            post.likeCount--;
            return -1;
        } else { // if post is not liked, like the post
            //likes
            liked_posts.put(post);
            seen_posts.put(post);
            post.likeCount++;
            return 1;
        }
    }
    public String generate_feed(int num){
        StringBuilder stringBuilder = new StringBuilder("Feed for ").append(userId).append(":");
        ArrayList<Post> feed_array = new ArrayList<>();
        for (User user: following_array){ // generate an array containing all posts from people that user follows
            feed_array.addAll(user.users_posts_array);
        }
        MaxHeap maxHeap = new MaxHeap(feed_array); // build heap
        while ( (! maxHeap.isEmpty()) && (num > 0) ) { // get the maximum element for "num" times as long as heap is not empty
            Post post = maxHeap.deleteMax();
            if ( ! seen_posts.contains(post)) {
                stringBuilder.append("\n").append("Post ID: ").append(post.postId).append(", Author: ").append(post.author).append(", Likes: ").append(post.likeCount);
                num--;
            }
        }
        if (num > 0) // check if num has been stasfied
            stringBuilder.append("\n").append("No more posts available for ").append(userId).append(".");
        return stringBuilder.toString();
    }
    public String scroll_through_feed(int num, String[] likes){
        StringBuilder stringBuilder = new StringBuilder(userId).append(" is scrolling through feed:");
        ArrayList<Post> feed_array = new ArrayList<>();
        for (User user: following_array){ // generate an array containing all posts from people that user follows
            feed_array.addAll(user.users_posts_array);
        }
        int current_num = 0;
        MaxHeap maxHeap = new MaxHeap(feed_array); // build heap
        while ( (! maxHeap.isEmpty()) && (current_num < num) ) { // get the maximum element for "num" times as long as heap is not empty
            Post post = maxHeap.deleteMax();
            if ( ! seen_posts.contains(post)) { // write the post to the output only if it is not seen
                // if corresponding element of likes is 1 click the like button, otherwise don't click
                if (Integer.parseInt(likes[current_num]) == 1) {
                    like(post);
                    stringBuilder.append("\n").append(userId).append(" saw ").append(post.postId).append(" while scrolling and clicked the like button."); //different in description
                }
                else {
                    see_post(post);
                    stringBuilder.append("\n").append(userId).append(" saw ").append(post.postId).append(" while scrolling.");
                }
                current_num++;
            }
        }
        if (current_num < num) // check if num has been stasfied
            stringBuilder.append("\nNo more posts in feed.");
        return stringBuilder.toString();
    }
    public String sort_posts(){ // method to sort all the posts of the user
        StringBuilder stringBuilder = new StringBuilder();
        MaxHeap users_posts_heap = new MaxHeap(users_posts_array); // build heap

        if (users_posts_heap.isEmpty())
            return stringBuilder.append("No posts from ").append(userId).append(".").toString();
        stringBuilder.append("Sorting ").append(userId).append("'s posts:");

        while (! users_posts_heap.isEmpty()) { // delete maximum and write it to the output until heap is empty
            Post post = users_posts_heap.deleteMax();
            stringBuilder.append("\n").append(post.postId).append(", Likes: ").append(post.likeCount);
        }
        return stringBuilder.toString();
    }
}
