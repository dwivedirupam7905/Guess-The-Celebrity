# Guess-The-Celebrity
Built an android app which randomly displays the pictures of famous celebrities to the user by fetching the data from a URL through the Internet and asks to choose the correct answer from the given 4 options and tells whether it is correct or not and also tells the right answer on choosing the wrong option and the question keeps changing after every click.

## Tools and Tech Stack Used:
Java, Android studio, scrapping, Async task viewer etc.

## Challenges:
Connect to celebrites [URL](https://www.imdb.com/list/ls052283250/), download info from it, process that info and display relevant info in the UI, that is, names and pictures of the celebrities.

## Async Task:
It is a key tool used in this project. Async Task is an abstract class provided by Android which gives us the liberty to perform heavy tasks in the background and keep the UI thread light thus making the application more responsive. Android application runs on a single thread when launched. Heavy background processes can crash our app or activity if runs on the main thread i.e. UI thread. So, we use Async Task for that.
By passing the celebrities URL as an string argument to the execute() function of Async Task class, we can connect to weather API. execute() function is to start the background thread.

Being an abstract class, Async Task contains two abstract functions **doInBackground()** and **onPostExecute()** which need to be implemented by overriding them.

i) doInBackground(): This method contains the code which needs to be executed in background. To notify that the background processing has been completed we just need to use the return statements.

ii) onPostExecute(): This method is called after doInBackground method completes processing. Result from doInBackground is passed to this method.

iii) JSON getString(): Returns the string value of the associated JSON String mapping for the specified name.


JSON:
JSON stands for Javascript Object Notation. It is a light-weight data interchange format. It is the way in which data (or text) is formatted.
Since JSON format is text only, it can easily be sent to and from a aserver. If we have data stored in a Javascript object then we can easily convert the object into JSON and then send to a server.

SCREENSHOTS :-

![ScrShot1](https://user-images.githubusercontent.com/91591163/201874748-acea10ca-f8aa-41f9-a1d1-90be712425ee.jpg)
![ScrShot2](https://user-images.githubusercontent.com/91591163/201874851-30d81fb8-3888-40fc-bddb-38cd658f3e2e.jpg)
![ScrShot3](https://user-images.githubusercontent.com/91591163/201874903-bc5a55c1-c05c-4e78-94de-89c4ed9d64fe.jpg)
