var app = angular.module('chatApp', ['ngMaterial']);

app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('red')
        .accentPalette('red');
});
//https: //img.etimg.com/thumb/msid-62710870,width-640,resizemode-4,imgsize-216230/panigale-v4-price.jpg
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