import NormalTTT.MainRunTTT;
import ReverseTTT.MainRunReversed;
import Treblecross.MainRunTreblecross;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MapGUI extends Application {
    private int statCount = 0;
    private int[][] map = {
            {0, 0, 2, 0, 0, 0, 1, 1, 1, 1, 0, 0, 2, 0, 0, 0, 1, 1, 1, 1},
            {0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1},
            {0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1},
            {0, 1, 0, 1, 1, 0, 0, 2, 0, 1, 0, 1, 0, 1, 1, 0, 0, 2, 0, 1},
            {0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1},
            {0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1},
            {0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1},
            {0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 2, 1, 0, 0, 1},
            {0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1},
            {0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1},
            {0, 1, 1, 0, 0, 2, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 2, 0, 1},
            {0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0},
            {0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0},
            {0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0},
            {2, 0, 0, 0, 1, 1, 0, 0, 0, 1, 2, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1},
            {0, 0, 2, 0, 0, 0, 1, 1, 1, 1, 0, 0, 2, 0, 0, 0, 1, 1, 1, 1},
            {0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 2, 0, 0, 1, 1},
            {0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1},
            {0, 1, 0, 1, 1, 0, 0, 2, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1},
            {0, 1, 0, 1, 1, 2, 0, 1, 0, 1, 0, 1, 0, 1, 1, 2, 0, 0, 0, 1},
            {0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1},
            {0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1},
            {0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1},
            {0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1},
            {0, 1, 1, 0, 0, 2, 1, 1, 0, 1, 0, 1, 1, 0, 0, 2, 1, 1, 0, 1},
            {0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0},
            {0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0},
            {0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0},
            {2, 0, 0, 0, 1, 1, 0, 0, 0, 1, 2, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 3},
    };


    private int playerRow = 0;
    private int playerColumn = 0;
    private boolean[][] visitedCells;

    private boolean gameStarted = false;
    private boolean gameOver = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        // Create the map grid
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                Rectangle cell = new Rectangle(15, 15);
                cell.setStroke(Color.BLACK);
                gridPane.add(cell, col, map.length - row - 1);
            }
        }

        // Initialize the visitedCells array
        visitedCells = new boolean[map.length][map[0].length];

        // Set the initial colors of the cells
        redrawMap(gridPane);

        // Create the scene and set the key event handler
        Scene scene = new Scene(gridPane);
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode(), gridPane));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Map GUInew");
        primaryStage.show();
    }

    private void redrawMap(GridPane gridPane) {
        // Check if the game is over, reset the player position if true
        if (gameOver) {
            playerRow = 0;
            playerColumn = 0;
        }
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle cell = (Rectangle) node;
                int row = GridPane.getRowIndex(cell);
                int col = GridPane.getColumnIndex(cell);

                // Set the fill color based on the map data
                int value = map[row][col];
                switch (value) {
                    case 0:
                        cell.setFill(Color.BLACK);
                        break;
                    case 1:
                        cell.setFill(Color.LIGHTGREY);
                        // Apply a drop shadow effect to simulate a block
                        DropShadow dropShadow = new DropShadow(10, Color.DARKGREY);
                        cell.setEffect(dropShadow);
                        break;
                    case 2:
                        cell.setFill(Color.LIME);
                        break;
                    case 3:
                        cell.setFill(Color.MAGENTA);
                        break;
                }

                // Set the fill color of the player's current position
                if (row == playerRow && col == playerColumn) {
                    cell.setFill(Color.BLUE);
                    visitedCells[row][col] = true; // Mark the cell as visited

                    // Check if the player has reached the cell with the number 3
                    if (value == 3) {
                        showCongratulatoryMessage();
                        gameOver = true;
                    }
                } else if (visitedCells[row][col]) {
                    cell.setFill(Color.CYAN); // Set color for previously visited cells
                }
            }
        }
    }
    private void startGame(int game) {
        gameStarted = true;
        MainRunTTT game1 = new MainRunTTT();
        MainRunReversed game2 = new MainRunReversed();
        MainRunTreblecross game3 = new MainRunTreblecross();
        if(game == 1)
        {
            game1.mode();
        }
        else if (game == 2)
        {
            game2.chooseGame();
        }
        else if (game == 3)
        {
            game3.mode();
        }
        else if (game == 4)
        {
            game3.mode();
        }

    }

    private void showCongratulatoryMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText(null);
        alert.setContentText("You have reached the goal!");
        alert.showAndWait();
    }
    private void handleKeyPress(KeyCode keyCode, GridPane gridPane) {
        if (gameOver) {
            playerRow = 0;
            playerColumn = 0;
            redrawMap(gridPane);
            gameOver = false;
            return;
        }

        int newRow = playerRow;
        int newColumn = playerColumn;

        switch (keyCode) {
            case UP:
                newRow--;
                break;
            case DOWN:
                newRow++;
                break;
            case LEFT:
                newColumn--;
                break;
            case RIGHT:
                newColumn++;
                break;
            default:
                return;
        }

        // Check if the new position is valid
        if (isValidMove(newRow, newColumn)) {
            // Change the color of the previously visited cell back to its original color
            Rectangle previousCell = getCellAtPosition(gridPane, playerRow, playerColumn);
            previousCell.setFill(Color.GREY); // Set the original color of the cell

            playerRow = newRow;
            playerColumn = newColumn;


            // Check if the player has reached the cell with the number 2
            if (map[playerRow][playerColumn] == 2) {
                statCount++;
                if(statCount == 1)
                {
                    startGame(3);
                }
                else if (statCount == 2)
                {
                    startGame(2);
                }
                else if (statCount == 3)
                {
                    startGame(2);
                }
                else if (statCount == 4)
                {
                    startGame(3);
                }
            }

            redrawMap(gridPane);
        } else {
            // Show an alert for an invalid move
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Invalid Move");
            alert.setHeaderText(null);
            alert.setContentText("You cannot move to that position!");
            alert.showAndWait();
        }
    }

    private boolean isValidMove(int newRow, int newColumn) {
        // Check if the new position is within the map bounds
        if (newRow >= 0 && newRow < map.length && newColumn >= 0 && newColumn < map[0].length) {
            // Check if the new position is not a block (represented by 1 in the map)
            return map[newRow][newColumn] != 1;
        }
        return false;
    }

    private Rectangle getCellAtPosition(GridPane gridPane, int row, int col) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (node instanceof Rectangle) {
                int currentRow = GridPane.getRowIndex(node);
                int currentCol = GridPane.getColumnIndex(node);
                if (currentRow == row && currentCol == col) {
                    return (Rectangle) node;
                }
            }
        }
        return null; // Cell not found
        }
}