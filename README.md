# How to Build and Run

Compile all .java files in src directory into a bin directory. Then, copy the views directory into the bin directory and run Photos.java with the java command.

Compile command: mkdir -p bin && javac --module-path /Users/sreekommalapati/Desktop/SchoolStuff/SoftwareMethodolody/javafx-sdk-19.0.2.1/lib --add-modules javafx.controls,javafx.fxml -d bin src/**/*.java

Run: java bin/app/Photos.java

# Desired src directory structure

## src
---
### app
- Photos.java
---
### model
- User.java
- Photo.java
- Album.java
- Tag.java
- DataManager.java
---
### view
- Login.fxml
- Admin.fxml
- User.fxml
- Album.fxml
- Search.fxml
---
### controller
---
- LoginController.java
- AdminController.java
- UserController.java
- AlbumController.java
- SearchController.java
---
### utils
---
- JavaFXUtils.java
- SerializationUtils.java
---
