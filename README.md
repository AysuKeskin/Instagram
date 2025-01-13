# Instagram Feed Manager Simulation

## 📖 Project Overview  
This project simulates an **Instagram Feed Manager** system. The application allows users to:  
- Create accounts.  
- Post content.  
- Interact with posts (like and view).  
- Manage follow relationships.  
- Generate personalized feeds.  

The primary focus is on implementing efficient **data structures** and **algorithms** to handle operations within time and memory constraints.

---

## ⚙️ Application Capabilities  

### 1️⃣ User Management  
- Create users with **unique IDs**.  
- Follow or unfollow other users.  

### 2️⃣ Post Management  
- Create posts with **unique IDs** and associated content.  
- View and interact with posts (like/unlike).  

### 3️⃣ Feed Generation  
- Generate personalized feeds based on:  
  - Post popularity.  
  - Post visibility.  
- Feeds exclude posts:  
  - Already seen by the user.  
  - Created by the user themselves.  

### 4️⃣ Post Sorting  
- Sort posts by **number of likes**.  
- Break ties **lexicographically** by post ID.  

---

## 📂 Input and Output  

### 📥 Input File  
The input file contains commands in the format:  
<command> <parameters>
Example Commands:

create_user user1  
follow_user user1 user2  
generate_feed user1 5


### 📤 Output File
The output file logs all system activities and results, adhering to the specified format.

Example Output:

Created user with Id user1.  
user1 followed user2.  
Feed for user1:  
Post ID: post1, Author: user2, Likes: 10  
No more posts available for user1.

## 🚀 Execution Instructions

### 📦 Compiling the Code
Use the following command to compile the program:

javac *.java

### ▶️ Running the Program
Run the program using the command:

java Main <input_file> <output_file>

<input_file>: Path to the input file containing commands.

<output_file>: Path to the output file where logs will be written.
