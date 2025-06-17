var app = angular.module('checkersApp', []);

app.controller('CheckersController', function($scope, $http) {
    $scope.board = [];
    $scope.selected = null;
    $scope.currentPlayer = null;
    $scope.message = '';
    $scope.isGameOver = false;

    $scope.loadGameState = function() {
        $http.get('/api/checkers/state').then(function(resp) {
            var data = resp.data;
            $scope.currentPlayer = data.currentPlayer;
            $scope.isGameOver = data.isGameOver;
            $scope.message = $scope.isGameOver ? ('Game over! Winner: ' + data.winner) : ('Current turn: ' + data.currentPlayer);

            $scope.board = [];
            for (var i = 0; i < 8; i++) {
                $scope.board[i] = [];
                for (var j = 0; j < 8; j++) {
                    var cell = data.board.find(function(c) { return c.row === i && c.col === j; });
                    $scope.board[i][j] = cell || {row:i, col:j, owner:null, type:null};
                }
            }
        }, function() {
            $scope.message = "Failed to load game state.";
        });
    };

    $scope.selectCell = function(row, col) {
        if ($scope.isGameOver) return;

        var cell = $scope.board[row][col];
        if ($scope.selected) {
            if (cell.row === $scope.selected.row && cell.col === $scope.selected.col) {
                $scope.selected = null;
                return;
            }
            var move = {
                from: {row: $scope.selected.row, col: $scope.selected.col},
                to: {row: row, col: col}
            };

            $http.post('/api/checkers/move', move).then(function(resp) {
                $scope.message = resp.data;
                $scope.selected = null;
                $scope.loadGameState();
            }, function() {
                $scope.message = "Move failed.";
                $scope.selected = null;
            });
        } else {
            if (cell.owner === $scope.currentPlayer) {
                $scope.selected = cell;
                $scope.message = 'Selected piece at (' + row + ', ' + col + ')';
            } else {
                $scope.message = "Please select one of your pieces.";
            }
        }
    };

    $scope.resetGame = function() {
        $http.post('/api/checkers/reset').then(function(resp) {
            $scope.message = resp.data;
            $scope.selected = null;
            $scope.isGameOver = false;
            $scope.loadGameState();
        });
    };

    $scope.loadGameState();
});