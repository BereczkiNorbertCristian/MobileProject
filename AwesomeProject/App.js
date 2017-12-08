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

import {ListBooks} from './Book/ListBooks'
import {DetailViewBook} from './Book/DetailViewBook'
import {CreateBook} from './Book/CreateBook'

class HomeScreen extends React.Component {
    static navigationOptions = {
        title: 'BookUSeller',
    };

    render() {
        const {navigate} = this.props.navigation;
        return (
            <View>
                <Text>Choose wisely:</Text>

                <Button
                    onPress={() => navigate('ListBooks')}
                    title="List current books"
                />
            </View>
        );
    }
}

const SimpleApp = StackNavigator({
    Home: {screen: HomeScreen},
    ListBooks: {screen: ListBooks},
    DetailViewBook: {screen: DetailViewBook},
    CreateBook: {screen: CreateBook},
});

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