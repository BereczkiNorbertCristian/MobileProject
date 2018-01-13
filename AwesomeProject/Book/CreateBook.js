import React from 'react';
import {StyleSheet, Text, View, TextInput, Button, AsyncStorage} from 'react-native';
import DatePicker from 'react-native-datepicker'


export class CreateBook extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            title: "",
            price: "",
            sellerName: "",
            email: this.props.navigation.state.params.email,
        }
        this.auth = global.firebaseApp.auth();
        this.auth.onAuthStateChanged((user) => {
            if(user){
                this.dbRefBooks = global.firebaseApp.database().ref().child('books')
            }
        })
    }

    render() {
        return (
            <View>
                <TextInput placeholder="Title" onChangeText={(title) => this.setState({title})}></TextInput>
                <TextInput placeholder="Price" onChangeText={(price) => this.setState({price})}/>
                <TextInput placeholder={this.state.email}/>
                <TextInput placeholder="Seller Name" onChangeText={(sellerName) => this.setState({sellerName})}/>
                <DatePicker date={this.state.publishingDate}
                            mode="date"
                            placeholder="select date"
                            onDateChange={(date) => {
                                this.setState({publishingDate: date})
                            }}
                />
                <Button title="ADD" onPress={() => {
                    this.dbRefBooks.child(this.state.title).set({
                        title: this.state.title,
                        description: "default description",
                        price: this.state.price,
                        sellerEmail: this.state.email,
                        sellerName: this.state.sellerName,
                        publishingDate: this.state.publishingDate
                    }).then(() => {
                        this.props.navigation.state.params.stateModified();
                        this.props.navigation.goBack();
                    });
                }
                }
                />
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
