import React from 'react';
import {
    AppRegistry,
    ListView,
    StyleSheet,
    Text,
    View,
    Button,
    TextInput,
    FlatList,
    TouchableOpacity,
    Alert
} from 'react-native';
import {StackNavigator} from 'react-navigation';
import * as firebase from 'firebase'

import {ListBooks} from './Book/ListBooks'
import {DetailViewBook} from './Book/DetailViewBook'
import {CreateBook} from './Book/CreateBook'
import {LogInScreen} from './Screens/LogInScreen'
import {SignUpScreen} from "./Screens/SignUpScreen";

const SimpleApp = StackNavigator({
    Home: {screen: LogInScreen},
    ListBooks: {screen: ListBooks},
    DetailViewBook: {screen: DetailViewBook},
    CreateBook: {screen: CreateBook},
    SignUpScreen: {screen: SignUpScreen},
});

const firebaseConfig = {
    apiKey: "AIzaSyCwW0mZpdgSIla2b1AmG0R4S7boyhIjFi4",
    authDomain: "bookseller-72d9e.firebaseapp.com",
    databaseURL: "https://bookseller-72d9e.firebaseio.com/",
    storageBucket: "bookseller-72d9e.appspot.com",
};
global.firebaseApp = firebase.initializeApp(firebaseConfig);

global.ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});

export default class App extends React.Component {
    render() {
        return <SimpleApp/>;
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center'
    },
    listitm: {
        padding: 10,
        marginTop: 3,
        backgroundColor: '#d9f9b1',
        alignItems: 'center',
    }
});