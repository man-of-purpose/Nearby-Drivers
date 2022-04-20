# Introduction
Hi! I worked on this technical test last month for a company called Heetch in France, and the test was to build an app that pulls the nearest Heetch drivers into a list view.

However, they already hired someone else who had completed all stages of the interview before I did. Hence, they had to stop my interview process.

I am submitting this as my Paconiq test because it was stated in the Payconiq technical assessment document that I can submit an already existing project/repo I worked on recently. Please see more details about the technical test below:

# Technical Test Instructions (Word for word from my Heetch interview)
As you'll see, the app right now doesn't do much. 
But we have big ambitions about it: display a nice, ordered, up-to-date list of drivers (ok maybe not that big).

The specs are quite simple: 
- You see that FAB button ? It can be turned to play or pause by a simple tap
- When visible and in "play" position, the FAB should automatically poll a list of drivers every 5 seconds
- For every update, the visible list of drivers should be refreshed, and reordered by distance from your current location

Too easy? Probably. But we voluntarily made an ugly app with zero organization and it will be your job to make it better.

No need to over-engineer it, just please make sure your app is stable, maintainable, well-structured and that it showcases your understanding of reactive programming principles and your ConstraintLayout skills 😉

Oh, also, as we're greedy, we'd also like to provide an alternate, premium version of the same app. It doesn't really have to be different, but be somehow "golden". I'm sure you'll figure something out.


### What we have done for you
We know your time is precious so the current code already provides some of the most boring parts:

- Runtime permissions
- The location module to retrieve you last known location
- Distance computation
- FAB button and his assets
- The network layer to call our web service
- A loader image module

Feel free to use/edit/drop them as you'll see fit.

### Bonuses
If you want to make some extra points you can

- Find a cool name and a nice icon for your app
- Improve the general design

Those are not as important as the above requirements, so if you're running out of time, you should make sure to validate all the previous points before.


### How to submit your work
- Checkout the project 📥
- Create a new branch for the implementation 🎋
- Implement your version of the app 🚧
- The code must be tested (unit tests) 🧞‍♂️
- Open a Pull Request on this repository to submit your work 📤
