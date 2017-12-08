import React from 'react';
import {StyleSheet, Text, View, TextInput, Button, AsyncStorage} from 'react-native';
import DatePicker from 'react-native-datepicker'


export class CreateBook extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isbn: "",
            title: "",
            author: "",
            nrPages: "",
            endDate: new Date()
        }
    }

    render() {
        return (
            <View>
                <TextInput placeholder="Title" onChangeText={(title) => this.setState({title})}></TextInput>
                <TextInput placeholder="Price" onChangeText={(price) => this.setState({price})}/>
                <TextInput placeholder="Seller Email" onChangeText={(sellerEmail) => this.setState({sellerEmail})}/>
                <TextInput placeholder="Seller Name" onChangeText={(sellerName) => this.setState({sellerName})}/>
                <DatePicker date={this.state.publishingDate}
                            mode="date"
                            placeholder="select date"
                            onDateChange={(date) => {
                                this.setState({publishingDate: date})
                            }}
                />
                <Button title="ADD" onPress={() => {
                    AsyncStorage.setItem(this.state.title, JSON.stringify({
                        title: this.state.title,
                        price: this.state.price,
                        sellerEmail: this.state.sellerEmail,
                        sellerName: this.state.sellerName,
                        publishingDate: this.state.publishingDate
                    })).then(() => {
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
