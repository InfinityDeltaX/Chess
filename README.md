Chess Engine written entirely in Java.

Implements UCI protocol, as seen [here](http://wbec-ridderkerk.nl/html/UCIProtocol.html), which allows playing with a front end, such as [Arena 3.5](http://www.playwitharena.com/).

As an alternative, the game can be played at the command line (without a GUI) by entering the command `text` immediately when the program starts, breaking out of the UCI protocol. The initial gamestate can either be set with a [FEN string](https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation), which describes a gamestate, or alternatively with the command `new`, which starts a new game. Moves can be entered using the coordinate system, where the initial position of the piece to be moved is followed by its destination. For example, to move a pawn from e2 to e4, the correct command is `e2e4`. When using this mode, it is highly recommended to use an additional physical chess board which mirrors the gamestate. 

To run the program, you must have Java 1.8 installed. Download the latest release from the releases tab, and run it with `java -jar chess.jar`. 

If you want to poke around with the source, I'm using Eclipse (Neon at the time of writing) as an IDE, and I've included the project files in the git repo for convenience.
