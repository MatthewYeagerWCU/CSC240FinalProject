# CSC240 Final Project
By Matthew Yeager  & Aidan Neff

## Project Description
This program works as an Email Spam detector using a Naive Bayes Algorithm. This is only a simple application, with many more complex versions available.

In simple, each Email can only be one of two values, spam or ham. To find the probability that each email is spam or ham, we find the individual chance that each word is spam or ham.

We find this using the proportion of spam emails that contain such word, and proportion of ham emails that use said word. Combining this with the overall chance that an email is spam or ham we can find a "spam probability" for each word.

Using each word in an email, we can combine these probabilities and find the overall probability that an entire email is spam. Finally, we use a similar process to find the ham probability and compare directly, giving us a final prediction for the email.

[Click here for more information on the mathematics behind this algorithm](https://digitalcommons.morris.umn.edu/horizons/vol2/iss1/2/)

This program takes in email data from a [csv file](https://github.com/MatthewYeagerWCU/CSC240FinalProject/blob/main/Data/spam_or_not_spam.csv), and outputs some basic email data to a separate [csv file](https://github.com/MatthewYeagerWCU/CSC240FinalProject/blob/main/Data/email_data.csv).

## UML
(NEEDS TO BE ADDED)

## Program Accuracy
(NEEDS TO BE ADDED)