import React from 'react';
import {
    StyleSheet,
    Text,
    View,
    TextInput,
    ListView,
    TouchableHighlight,
    TouchableOpacity,
    Button,
    Alert,
    AsyncStorage
} from 'react-native';
import {StackNavigator} from 'react-navigation';
import {DetailViewBook} from './DetailViewBook';

export class ListBooks extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            ds: global.ds.cloneWithRows([]),
            uid: this.props.navigation.state.params.uid,
            email: this.props.navigation.state.params.email,
        };
        Alert.alert("This: " + this.state.email);
        this.refBooks = global.firebaseApp.database().ref().child('books');
        this.refUsers = global.firebaseApp.database().ref().child('users/' + this.state.uid);
        this.auth = global.firebaseApp.auth();

        this.stateModified();
    }

    updateWithAsyncStorage() {
        AsyncStorage.getAllKeys().then((keys) => {
            lst = [];
            for (k in keys) {
                AsyncStorage.getItem(keys[k]).then((book) => {
                    lst.push(JSON.parse(book));
                    this.setState({ds: global.ds.cloneWithRows(lst)});
                });
            }
        });
    }

    stateModified() {
        this.refBooks.on('value', (snap) => {
            var bookList = [];
            snap.forEach(element => {
                bookList.push(element.val())
            });
            console.log(bookList);
            this.setState({ds: global.ds.cloneWithRows(bookList)});
        })
    }

    renderRow(bk) {
        return (
            <View>
                <TouchableOpacity onPress={() => this.props.navigation.navigate('DetailViewBook', {
                    book: bk,
                    uid: this.state.uid,
                    stateModified: this.stateModified.bind(this)
                })}>
                    <View style={{flexDirection: 'row', padding: 10}}>
                        <View stle={{flex: 1}}>
                            <Text>{bk.title}</Text>
                        </View>
                        <View style={{flex: 1}}>
                            <Text style={{textAlign: 'right'}}>{bk.price}</Text>
                        </View>
                    </View>
                </TouchableOpacity>
            </View>
        );
    }

    render() {
        return (
            <View>
                <ListView
                    dataSource={this.state.ds}
                    renderRow={this.renderRow.bind(this)}
                />
                <Button title="ADD"
                        onPress={() => {
                            this.refUsers.on('value',(snap) => {
                                if(snap.val().premium){
                                    this.props.navigation.navigate('CreateBook',
                                        {stateModified: this.stateModified.bind(this), email: this.state.email})
                                }
                                else{
                                    Alert.alert("You are not a premium user!")
                                }
                                })
                            }}
                        />
                <Button title="LOG OUT" onPress={() => {
                    this.auth.signOut()
                    this.props.navigation.goBack();
                }}/>
            </View>
        );
    }
}


const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
});
