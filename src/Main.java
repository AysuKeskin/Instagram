import java.io.*;
public class Main {
    private static MyHashMap<String, User> users_map = new MyHashMap<>();
    private static MyHashMap<String , Post> posts_map = new MyHashMap<>();
    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = args[1];
        String line;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
            while ((line = bufferedReader.readLine()) != null) {
                String output = process_command(line); // returns the output to be written in output file
                if (output != null && ! output.isEmpty()) {
                    writer.write(output);
                    writer.newLine();
                }
            }
            writer.flush(); // flush writer in case some arguments is not written
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String process_command(String line){ // method to do the given command
        String[] line_split = line.split(" ");
        String command = line_split[0];
        switch (command) { // command tells us what method to use
            case "create_user":
                return create_user(line_split[1]);
            case "follow_user":
                return follow_user(line_split[1], line_split[2]);
            case "unfollow_user":
                return unfollow_user(line_split[1], line_split[2]);
            case "create_post":
                return create_post(line_split[1], line_split[2], line_split[3]);
            case "see_post":
                return see_post(line_split[1], line_split[2]);
            case "see_all_posts_from_user":
                return see_all_posts_from_user(line_split[1], line_split[2]);
            case "toggle_like":
                return toggle_like(line_split[1], line_split[2]);
            case "sort_posts":
                return sort_posts(line_split[1]);
            case "generate_feed":
                return generate_feed(line_split[1], Integer.parseInt(line_split[2]));
            case "scroll_through_feed":
                String [] likes = new String[line_split.length - 3];
                System.arraycopy(line_split, 3, likes, 0, line_split.length - 3); // copies the part of array that represent likes
                return scroll_through_feed(line_split[1], Integer.parseInt(line_split[2]), likes);
            default:
                return null;
        }
    }
    private static String create_user(String userId){
        if (users_map.contains(userId)) // if user already exists give an error
            return "Some error occurred in create_user.";
        User new_user = new User(userId);
        users_map.put(userId, new_user);
        return "Created user with Id " + userId + ".";
    }
    private static String follow_user(String userId1, String userId2){
        User user1 = users_map.get(userId1);
        User user2 = users_map.get(userId2);
        if (user1 == null || user2 == null){ // if one of the users don't exist give an error
            return "Some error occurred in follow_user.";
        }
        if (! user1.follow(user2)) // if following has not been successful give an error
            return "Some error occurred in follow_user.";

        StringBuilder stringBuilder = new StringBuilder(userId1);
        return stringBuilder.append(" followed ").append(userId2).append(".").toString();

    }
    private static String unfollow_user(String userId1, String userId2){
        User user1 = users_map.get(userId1);
        User user2 = users_map.get(userId2);
        if (user1 == null || user2 == null){ // if one of the users don't exist give an error
            return "Some error occurred in unfollow_user.";
        }
        if ( ! user1.unfollow(user2)) // if unfollowing has not been successful give an error
            return "Some error occurred in unfollow_user.";

        StringBuilder stringBuilder = new StringBuilder(userId1);
        return stringBuilder.append(" unfollowed ").append(userId2).append(".").toString();

    }
    private static String create_post(String userId, String postId, String content){
        User user = users_map.get(userId);
        if (user == null){ // if the user doesn't exist give an error
            return  "Some error occurred in create_post.";
        }
        if (posts_map.contains(postId)){ // if the post already exists give an error
            return "Some error occurred in create_post.";
        }

        Post post = new Post(postId, content, userId);
        posts_map.put(postId, post);
        user.create_post(post);

        StringBuilder stringBuilder = new StringBuilder(userId);
        return stringBuilder.append(" created a post with Id ").append(postId).append(".").toString();
    }
    private static String see_post(String userId, String postId){
        User user = users_map.get(userId);
        // if either user or post doesn't exist give an error
        if (user == null){
            return  "Some error occurred in see_post.";
        }
        Post post = posts_map.get(postId);
        if (post == null){
            return "Some error occurred in see_post.";
        }
        user.see_post(post);
        StringBuilder stringBuilder = new StringBuilder(userId);
        return stringBuilder.append(" saw ").append(postId).append(".").toString();
    }
    private static String see_all_posts_from_user(String viewerId, String viewedId){
        User viewer = users_map.get(viewerId);
        // if one of the users don't exist give an error
        if (viewer == null){
            return  "Some error occurred in see_all_posts_from_user.";
        }
        User viewed = users_map.get(viewedId);
        if (viewed == null){
            return "Some error occurred in see_all_posts_from_user.";
        }
        viewer.see_all_posts(viewed);

        StringBuilder stringBuilder = new StringBuilder(viewerId);
        return stringBuilder.append(" saw all posts of ").append(viewedId).append(".").toString();
    }
    private static String toggle_like(String userId, String postId){
        // if either user or post doesn't exist give an error
        User user = users_map.get(userId);
        if (user == null){
            return  "Some error occurred in toggle_like.";
        }
        Post post = posts_map.get(postId);
        if (post == null){
            return "Some error occurred in toggle_like.";
        }

        StringBuilder stringBuilder = new StringBuilder(userId);
        if (user.like(post) == 1) // likes
            return stringBuilder.append(" liked ").append(postId).append(".").toString();
        else // unlikes
            return stringBuilder.append(" unliked ").append(postId).append(".").toString();
    }
    private static String sort_posts(String userId){
        User user = users_map.get(userId);
        if (user == null) // if user doesn't exist give an error
            return  "Some error occurred in sort_posts.";
        return user.sort_posts();
    }
    private static String generate_feed(String userId, int num){
        User user = users_map.get(userId);
        if (user == null) // if user doesn't exist give an error
            return "Some error occurred in generate_feed.";
        return user.generate_feed(num);
    }
    private static String scroll_through_feed(String userId, int num, String[] likes){
        User user = users_map.get(userId);
        if (user == null) // if user doesn't exist give an error
            return "Some error occurred in scroll_through_feed.";
        return user.scroll_through_feed(num, likes);
    }
}