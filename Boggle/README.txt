=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. I/O: I use I/O in 2 different ways. The first way is storing high scores to be referenced 
  any time the game is run. In order to achieve this, I use a bufferedWriter encapsulated in my 
  HighScoreWriter class. This writer writes names and scores seperated by an & and each entry is 
  on a unique line. At the end of each game, the person is prompted to enter their name if they want
  That way, they can be referenced later on. I use a bufferedReader where I store each "next" line
   as a string S, then check its validity just like with the fileLineIterator class I use the reader
  just to store every person and score in a map that I then find the top 5 scores of for the high 
  scores panel. My other I/O method is just a fileLineIterator (cuz I didn't need to make it fancy) 
  that reads every line of a dictionary and stores it in a treeset. This is then used to check if
  words are actually english/valid. Fun fact: the dictionary can be edited directly if you want 

  2. 2d array: ok, so I had a couple of these. The most important one of course was the actual 
  array of letters created in the Boggle.java class. This array stored each letter and its location.
  The next use of the 2d array came from storing JToggleButtons in the right places for those 
  letters. I had to create methods that would keep track of what was pressed so things couldn't
  be pressed multiple times, and allow the user to only press buttons that touched the one they 
  already had down. Of course, I had to use 2 2d arrays, one for buttons and one for letters since
  letters change between rounds, but if I had to make new buttons every time the reset button 
  was hit, it would be time consuming and it would waste memory, so I used 1 array for letters 
  and another for buttons

  3. collections: I use collections everywhere. The most important use is actually a linkedlist 
  that stores what buttons have been pressed so that you can undo a press and when you click a 
  new letter, I can enable the right buttons to follow that letter.

  4. Ok, so for testable components, I made the reader and writer into easily testable 
  methods where I could test the edge cases, then I made the Boggle.Java into a gmae that 
  essentially works on its own so that I could test the methods like setting up the board with 
  random letters and keeping track of score

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  GameBoard: serves as the home of all GUI with functionality. I use this as a homebase to set up 
  the timer, the board itself (with the 16 buttons), the updating word you build, your list of 
  words that updates, and of course, all the methods associated with these actions and buttons 
  resides here (except for the things directly related to boggle)
  
  Boggle: this serves as a base class for the game itself. We can generate boards, keep track 
  of the score, keep track of words, figure out what's a valid word and what's not, and all in all,
  it's mainly used to do the real work for the GUI in terms of monitoring the game
  
  Game: this class is essentially just to create all the panels and frames you see
  
  HighScoreWriter: This class takes care of a bufferedWriter that can write info to the 
  highScores.txt file. It writes the user with an & mark after their name, then the score.
  To avoid issues with closing the writer, this class actually requires that you use a new writer
  every time you want to write a line
  
  HighScoreReader: this class uses a bufferedReader to keep track of all the current 
  users and scores, and it then takes those users and scores and stores them in a map that 
  can be used to find top scores
  
  Person: Because there aren't tuples, storing a person's name with their score directly was 
  impossible if I planned on altering the map it was in, so I had to use this class to do so.
  


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

Yeah, so originally I had a recursive function to find all possible words in the boggle board and 
I was going to have people type a word in, but that function actually took forever to work and 
almost crashed my computer. Hence, I would call it a stumbling block


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
I would say that it has very little seperation of functionality. It was really hard to seperate 
the GameBoard Methods from the Boggle board methods, but I did what I could even though they 
ended up so overlapped. If I could refactor, I would try and clean up my gameboard class and see
if there's another way to approach the whole method of letting people only select letters that are
valid


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
