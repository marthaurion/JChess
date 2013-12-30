JChess
=======

A Java-based chess program I'm creating.
Initially, this was my own side project, but then I used it for a final assignment in school.
After turning in the assignment, I decided to remove some of the things I created so that I can make this my own way.

Things that are done:
- Can tell when the king is in check.
- Created attack maps for checking threatened squares.
- Move list displays each move that has been played.
- Chess pieces are images.
- Added castling and en passant as valid moves.
- Added a form of checkmate detection.
- Pawn will auto-promote to queen.
- Stops the player from leaving the king in check.
- Highlights possible moves in blue for the turn player.
- Added some fancy Javadocs.
- Created a more simple display class that does less board logic (needs testing).

Things to be done:
- Need to keep testing castling, en passant, and checkmate detection for bugs.
- Add disambiguation for knights and rooks on the move list.
- Add better pawn promotion.
- Add online aspect back.
- Create an AI.
- Make it an app.
- Go through the code and make sure everything is well-documented.
- Might want to change the attack map to be its own class instead of a 2D array of ints.

The images for the chess pieces were found [here](http://ixian.com/chess/) with permission from creator.
