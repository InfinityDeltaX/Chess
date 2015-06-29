Chess Engine written in entirely in Java.

Implements UCI protocol, as seen here: http://wbec-ridderkerk.nl/html/UCIProtocol.html

Can be used with a front-end such as Arena 3.5: http://www.playwitharena.com/

As an alternative, the game can be played at the command line (without a GUI) by typing 'text' as the first command when the program starts. Moves can be entered using the coordinate system, where the initial position of the piece to be moved is followed by its destination. For example, to move a pawn from e2 to e4, the correct command is 'e2e4'. When using this mode, it is highly recommended to use an additional physical chess board which mirrors the gamestate.  
