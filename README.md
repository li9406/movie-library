# Movie Library

The app allows users to add, remove and display movies in the Movie Library using features and gestures.

Every movie has the attributes (fields):

* title
* year
* country
* cost
* genre
* keywords

All movies are saved in an SQLite database and the Google firebase database. Whenever a new movie is saved in the SQLite database, it will be pushed to the Google firebase database as well.

Features of the app:

* Toolbar
* Options menu:
  * Clear Fields - clear all fields
* Navigation menu option:
  * Remove All Movies - all movies in the Google firebase database and local database
  * Add Movie - add a new movie
  * Remove Last Movie - remove the last movie added
* Floating Action Button - add a new movie
* Listview - show all movies added so far
* List All Movie - display all attributes in CardView

Gesture tricks:

1. add a new movie to the database by swiping the screen from left to right
2. clear all fields by swiping the screen from top to bottom or long pressing the screen
3. move the app to the background when flinging on the screen
4. increase the cost of a movie by single tapping on the screen
5. load default values to all fields by double tapping on the screen
6. increase the year of a movie by scrolling from left to right
7. decrease the year of a movie by scrolling from right to left
