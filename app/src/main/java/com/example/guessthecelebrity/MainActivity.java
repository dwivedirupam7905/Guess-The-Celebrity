package com.example.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> celebURLs = new ArrayList<>();
    ArrayList<String> celebNames = new ArrayList<>();
    int chosenCeleb = 0;
    String[] answers = new String[4];
    int locationOfCorrectAnswer = 0;
    int incorrectAnswerLocation;
    ImageView imageView;
    Button button0, button1, button2, button3;

    // Function to check whether the option chosen is correct or not and change the question after every click
    public void celebChosen(View view){
        // Checking whether the option chosen is correct or not
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            Toast.makeText(getApplicationContext(), "Correct! " + ("\ud83d\ude04"), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Wrong! It was " + answers[locationOfCorrectAnswer] + (" \ud83d\ude16"), Toast.LENGTH_SHORT).show();
        }

        // Calling newQuestion() method
        newQuestion();
    }

    // Function to get new question after every click
    public void newQuestion(){
        Random random = new Random();
        chosenCeleb = random.nextInt(celebNames.size());
        Log.i("Celeb: ", Integer.toString(chosenCeleb));

        // To download the images from provided URL as argument
        ImageDownloader imageDownloader = new ImageDownloader();
        Bitmap celebImage = null;
        try {
            celebImage = imageDownloader.execute(celebURLs.get(chosenCeleb)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Updating the imageView as per the choosen celeb
        imageView.setImageBitmap(celebImage);

        locationOfCorrectAnswer = random.nextInt(4);

        for(int i=0 ; i<4 ; i++){
            if(i == locationOfCorrectAnswer)
                answers[i] = celebNames.get(chosenCeleb);
            else{
                incorrectAnswerLocation = random.nextInt(celebNames.size());

                // To avoid the repeated celebrity name
                while(incorrectAnswerLocation == chosenCeleb)
                    incorrectAnswerLocation = random.nextInt(celebNames.size());

                answers[i] = celebNames.get(incorrectAnswerLocation);
            }
        }
        // Setting up the names of celebs in the buttons from the 'answers' string
        button0.setText(answers[0]);
        button1.setText(answers[1]);
        button2.setText(answers[2]);
        button3.setText(answers[3]);
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                return BitmapFactory.decodeStream(inputStream);
                //ALITER :-
                //Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                //return myBitmap;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection;
            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();

                // To read the URL data character by character
                while(data != -1){
                    char current = (char) data;
                    result.append(current);
                    data = inputStreamReader.read();
                }
                return result.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        DownloadTask task = new DownloadTask();
        String result;

        try{
            // To access the complete HTML document of the website through provided URL
            result = task.execute("https://www.imdb.com/list/ls052283250/").get();

            //Log.i("Contents of URL: ", result);

            // To access all the celebrity names from the website and add them in the created arraylist 'celebNames'
            Pattern pattern = Pattern.compile("<img alt=\"(.*?)\"");
            Matcher matcher = pattern.matcher(result);
            while(matcher.find())
                celebNames.add(matcher.group(1));

            // To access all the celebrity images from the website and add them in the created arraylist 'celebURLs'
            pattern = Pattern.compile("src=\"(.*?).jpg\"");
            matcher = pattern.matcher(result);
            while(matcher.find())
                celebURLs.add(matcher.group(1) + ".jpg");

            // Calling newQuestion() method
            newQuestion();

            Toast.makeText(this, "Make sure that the Internet is on!", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}