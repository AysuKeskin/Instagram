Project Overview::

This project is a simulation of an Instagram Feed Manager system. The application allows users to create accounts, post content, interact with posts (likes and views), manage follow relationships, and generate personalized feeds. The primary focus is on implementing efficient data structures and algorithms to handle operations within the constraints of time and memory limits.

Application Capabilities

User Management:

Create users with unique IDs.
Follow or unfollow other users.

Post Management:

Create posts with unique IDs and associated content.
View and interact with posts (like/unlike).

Feed Generation:

Generate personalized feeds based on post popularity and visibility.
Feeds exclude posts already seen by the user or created by the user themselves.

Post Sorting:

Sort a user’s posts by the number of likes.
Break ties lexicographically by post ID.

Input and Output Files:

Input File:

The input file contains a series of commands in the following structure:
<command> <parameters>
Example commands:

create_user user1

follow_user user1 user2

generate_feed user1 5

Output File:

The output file logs all system activities and results, adhering to the specified format. For example:

Created user with Id user1.

user1 followed user2.

Feed for user1:

Post ID: post1, Author: user2, Likes: 10

No more posts available for user1.

Execution Instructions:

Compiling the Code

Use the following command to compile the program:

javac *.java
Running the Program

Run the program using the following command:

java Main <input_file> <output_file>

<input_file>: Path to the input file containing commands.

<output_file>: Path to the output file where logs will be written.
