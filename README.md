# Final_Project
Features
    - Graphical Poker Interface
        Interactive layout with click support for card selection
    - Computer Opponents
        Play against 2 computers with selectable strategies
        - Easy: Random strategy
        - Hard: Smarter decision making by measuring hand
    - Betting System
        Players can call, check, fold, and raise in betting rounds
    - Play Again
        After the game is over, players can choose to play again or exit
    - User Profile
        User data is saved on exit to maintain balance

How to Play
    1. Launch the game
    2. Select difficulty (Easy or Hard) from the dropdown
    3. Click "Draw Cards" to start the round
    4. Participate in betting (raise, call, fold, check)
    5. Choose cards to discard by clicking on them (highlighted in yellow)
    6. Click "Draw New Cards" to complete your hand
    7. Observe results and choose to play again or exit

UI Overview
    Top Left/Right: Computer player hands (face-down)
    Center: Your hand with clickable cards
    Betting controls (Raise, Fold, Call, Check)
    Buttons:
        "Draw Cards" – Begin the game
        "Draw New Cards" – Discard selected cards and draw new ones
        "Play Again" – Restart the game after a round
        "Exit" – Exit the game and save progress

Code Structure
    view/ – UI components and main JFrame (View and GameView)
    controller/ – Game logic and event handling
    model/ – Card, Player, and other game state classes
    database/ – User save/load functionality

Notes
    This project supports multiple developers
    Contributions were made by Kris, Isaac, Dwij, and Bassam
    
