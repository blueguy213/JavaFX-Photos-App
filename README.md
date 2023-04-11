
# Desired src directory structure

## src
---
### app
- PhotoLibrary.java
---
### model
- User.java
- Photo.java
- Album.java
- Tag.java
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

# UML Class Description

## ~~User~~

### Attributes:
- ~~serialVersionUID: long~~
- ~~username: String~~
- ~~albums: List\<Album>~~
### Operations:
- ~~createUser(username: String, password: String): void~~
- ~~deleteUser(username: String): void~~
- ~~listUsers(): List<String>~~
- ~~createAlbum(name: String): void~~
- ~~deleteAlbum(name: String): void~~
- ~~renameAlbum(oldName: String, newName: String): void~~
- ~~openAlbum(name: String): Album~~
---
## Admin (inherits User)
### Attributes:
### Operations:
- createUser(username: String, password: String): void
- deleteUser(username: String): void
- listUsers(): List\<String>
---
## Album
### Attributes:
- name: String
- photos: List\<Photo>
### Operations:
- getName(): String
- setName(name: String): void
- getPhotos(): List\<Photo>
- addPhoto(photo: Photo): void
- removePhoto(photo: Photo): void
---
## Photo
### Attributes:
- location: String
- lastModifiedDate: Date
- caption: String
- tags: List\<Tag>
### Operations:
- getLocation(): String
- getLastModifiedDate(): Date
- getCaption(): String
- setCaption(caption: String): void
- getTags(): List<Tag>
- addTag(tag: Tag): void
- removeTag(tag: Tag): void
---
## Tag
### Attributes:
- name: String
- value: String
### Operations:
- getName(): String
- setName(name: String): void
- getValue(): String
- setValue(value: String): void
---

# UML State decription

## Class: User

### Attributes:
- username: String
- password: String
- isAdmin: boolean
- albums: List<Album>
### Operations:
- createAlbum(name: String): Album
- deleteAlbum(name: String): void
- renameAlbum(oldName: String, newName: String): void
- searchByDateRange(startDate: Date, endDate: Date): List<Photo>
- searchByTagTypeValuePairs(tagTypeValuePairs: Map<String, String>, conjunctionType: String): List<Photo>
- createAlbumFromSearchResults(name: String, searchResults: List<Photo>): Album
--- 
## Class: Album

### Attributes:
- name: String
- photos: List<Photo>
### Operations:
- addPhoto(photo: Photo): void
- removePhoto(photo: Photo): void
- getPhotos(): List<Photo>

## Class: Photo

### Attributes:
- filepath: String
- thumbnail: Image
- date: Date
- caption: String
- tags: List<Tag>
### Operations:
- setCaption(caption: String): void
- addTag(tag: Tag): void
- removeTag(tag: Tag): void

## Class: Tag

### Attributes:
- type: String
- value: String
### Operations:

## Class: DataStorageManager

### Attributes:
- users: Map<String, User>
### Operations:
- addUser(user: User): void
- deleteUser(username: String): void
- saveUserData(): void
- loadUserData(): void

--- 

# UML Sequence Description (Actions to support Unsupported, ~~Supported~~)

## Login

- LoginController: handleLoginButtonClick()

## Admin Subsystem
        
### 2.1. List users

- Admin: listUsers()

### 2.2. Create a new user

- Admin: createUser(username, password)

### 2.3. Delete an existing user

- Admin: deleteUser(username)

## Non-admin User Subsystem
        
### 3.1. Create albums

- User: createAlbum(name)

### 3.2. Delete albums

- User: deleteAlbum(name)

### 3.3. Rename albums

- User: renameAlbum(oldName, newName)

### 3.4. Open an album

- User: openAlbum(name)

### 3.5. Inside an open album:
    
### 3.5.1. Add a photo

- Album: addPhoto(photo)

### 3.5.2. Remove a photo

- Album: removePhoto(photo)

### 3.5.3. Caption/recaption a photo

- Photo: setCaption(caption)

### 3.5.4. Add a tag to a photo

- Photo: addTag(tag)

### 3.5.5. Delete a tag from a photo

- Photo: removeTag(tag)

### 3.5.6. Copy a photo from one album to another

- Album (source): getPhotos()
- Album (destination): addPhoto(photo)

### 3.5.7. Move a photo from one album (source) to another (destination)

- Album (source): removePhoto(photo)
- Album (destination): addPhoto(photo)

## Search for photos

### 4.1. Search for photos by a date range

- User: searchByDateRange(startDate, endDate)

### 4.2. Search for photos by tag type-value pairs

- User: searchByTagTypeValuePairs(tagTypeValuePairs, conjunctionType)

## Create an album containing the search results

- User: createAlbumFromSearchResults(name, searchResults)

## Logout

- User: logout()

---

## Edit a file

You’ll start by editing this README file to learn how to edit a file in Bitbucket.

1. Click **Source** on the left side.
2. Click the README.md link from the list of files.
3. Click the **Edit** button.
4. Delete the following text: *Delete this line to make a change to the README from Bitbucket.*
5. After making your change, click **Commit** and then **Commit** again in the dialog. The commit page will open and you’ll see the change you just made.
6. Go back to the **Source** page.

---

## Create a file

Next, you’ll add a new file to this repository.

1. Click the **New file** button at the top of the **Source** page.
2. Give the file a filename of **contributors.txt**.
3. Enter your name in the empty file space.
4. Click **Commit** and then **Commit** again in the dialog.
5. Go back to the **Source** page.

Before you move on, go ahead and explore the repository. You've already seen the **Source** page, but check out the **Commits**, **Branches**, and **Settings** pages.

---

## Clone a repository

Use these steps to clone from SourceTree, our client for using the repository command-line free. Cloning allows you to work on your files locally. If you don't yet have SourceTree, [download and install first](https://www.sourcetreeapp.com/). If you prefer to clone from the command line, see [Clone a repository](https://confluence.atlassian.com/x/4whODQ).

1. You’ll see the clone button under the **Source** heading. Click that button.
2. Now click **Check out in SourceTree**. You may need to create a SourceTree account or log in.
3. When you see the **Clone New** dialog in SourceTree, update the destination path and name if you’d like to and then click **Clone**.
4. Open the directory you just created to see your repository’s files.

Now that you're more familiar with your Bitbucket repository, go ahead and add a new file locally. You can [push your change back to Bitbucket with SourceTree](https://confluence.atlassian.com/x/iqyBMg), or you can [add, commit,](https://confluence.atlassian.com/x/8QhODQ) and [push from the command line](https://confluence.atlassian.com/x/NQ0zDQ).