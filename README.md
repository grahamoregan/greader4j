##Intro

I like to use Google Reader on my mobile to favourite articles and then come back to them when I'm on my laptop or desktop. This library gives me easy access to the favourite list so I can pump the links straight into Instapaper so I can read them on the go. The code was heavily inspired by http://www.chrisdadswell.co.uk/android-coding-example-authenticating-clientlogin-google-reader-api/ I refactored it a little but it is still close to the original. 

## Usage
I have added greader4j.GoogleReaderNG to show an example of how the library can be used. If you drop a properties file in your home directort called '.greader4j' with two properties called 'username' and 'password' and it should write the titles of your starred items to STDOUT.

## Future
This is running on a private JVM at the moment to periodically sync Google Reader and Instapaper, but I'd like to get it working with OAuth so the password doesn't need to be stored so I could let others use it too, but I'll have to look into that later.
