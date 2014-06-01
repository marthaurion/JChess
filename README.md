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

Known Issues:
- Frame is disposed for black when white makes its first move. This is disruptive, as the window closes, then re-opens.
- Certain moves cause errors to occur. This needs to be tested more extensively.

General Testing:
- Need to keep testing castling, en passant, and checkmate detection for bugs.
- Server connection hasn't been tested too well.
- New display class was created for simpler logic that needs to be tested more.

Things to be done:
- Add disambiguation for knights and rooks on the move list.
- Add better pawn promotion.
- Create an AI.
- Make it an app.
- Go through the code and make sure everything is well-documented. Keep Javadocs updated.
- Might want to change the attack map to be its own class instead of a 2D array of ints.
- Server doesn't seem super efficient. It can probably be optimized.

The images for the chess pieces were found [here](http://ixian.com/chess/) with permission from creator.
