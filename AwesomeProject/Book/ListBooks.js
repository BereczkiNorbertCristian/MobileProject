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
    AsyncStorage
} from 'react-native';
import {StackNavigator} from 'react-navigation';
import {DetailViewBook} from './DetailViewBook';

export class ListBooks extends React.Component {

    constructor() {
        super();
        this.state = {
            ds: global.ds.cloneWithRows([])
        }
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
        AsyncStorage.getAllKeys().then((keys) => {
            lst = [];
            for (k in keys) {
                AsyncStorage.getItem(keys[k]).then((book) => {
                    lst.push(JSON.parse(book));
                    this.setState({ds: global.ds.cloneWithRows(lst)});
                })
            }

        });
    }

    renderRow(bk) {
        return (
            <View>
                <TouchableOpacity onPress={() => this.props.navigation.navigate('DetailViewBook', {
                    book: bk,
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
                        onPress={() => this.props.navigation.navigate('CreateBook',
                            {stateModified: this.stateModified.bind(this)})}/>
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
