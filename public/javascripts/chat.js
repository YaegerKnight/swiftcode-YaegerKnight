var app = angular.module('chatApp', ['ngMaterial']);

app.controller('chatController', function ($scope) {
    $scope.messages = [
        {

            'sender': 'USER',
            'text': 'Hello'

		},
        {

            'sender': 'BOT',
            'text': 'Hello USER'

		},
        {

            'sender': 'USER',
            'text': 'What are you?'

		},
        {

            'sender': 'BOT',
            'text': 'ggwp'

		},
        {

            'sender': 'USER',
            'text': 'Tell a joke.'

		},
        {

            'sender': 'BOT',
            'text': 'What did one ocean say to the other? Nothing.They just waved.'

		}
 	];
});