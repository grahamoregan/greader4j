##Intro

I like to use Google Reader to filter articles when I'm commuting, I star the articles and then come back to them later to read in full. I wanted to be able to create a feed from my starred items so I can pump that into Instapaper so I can sync it up on the go. The code was heavily inspired by http://www.chrisdadswell.co.uk/android-coding-example-authenticating-clientlogin-google-reader-api/ I refactored it a little but it is still close to the original. 

## Usage
I have added greader4j.GoogleReaderNG to show an example of how the library can be used. If you drop a properties file in your home directort called '.greader4j' with two properties called 'username' and 'password' and it should write the titles of your starred items to STDOUT.

## Future
This is running on a private JVM at the moment to periodically sync Google Reader and Instapaper, but I'd like to get it working with OAuth so the password doesn't need to be stored so I could let others use it too, but I'll have to look into that later.
