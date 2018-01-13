import React from 'react';
import {StyleSheet, Text, View, TextInput, Button, AsyncStorage, processColor, Alert} from 'react-native';
import DatePicker from 'react-native-datepicker'
import {Bar} from 'react-native-pathjs-charts'

export class DetailViewBook extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            uid: this.props.navigation.state.params.uid,
            title: this.props.navigation.state.params.book.title,
            price: this.props.navigation.state.params.book.price,
            sellerEmail: this.props.navigation.state.params.book.sellerEmail,
            sellerName: this.props.navigation.state.params.book.sellerName,
            publishingDate: this.props.navigation.state.params.book.publishingDate,
            data: [[{type: "DUMMY", price: 12},{type: "DUMMY2", price: 22}]]
        };
        this.auth = global.firebaseApp.auth();
        this.auth.onAuthStateChanged((user) => {
            if(user){
                this.dbRef = global.firebaseApp.database().ref().child('books');
                this.dbRefUsers = global.firebaseApp.database().ref().child('users/' + user.uid);
            }
        })
    }

    render() {
        opts = {
            width: 300,
            height: 300,
            margin: {
                top: 20,
                left: 25,
                bottom: 50,
                right: 20
            },
            color: '#2980B9',
            gutter: 20,
            animate: {
                type: 'oneByOne',
                duration: 200,
                fillTransition: 3
            },
            axisX: {
                showAxis: true,
                showLines: true,
                showLabels: true,
                showTicks: true,
                zeroAxis: false,
                orient: 'bottom',
                label: {
                    fontFamily: 'Arial',
                    fontSize: 8,
                    fontWeight: true,
                    fill: '#34495E',
                    rotate: 45
                }
            },
            axisY: {
                showAxis: true,
                showLines: true,
                showLabels: true,
                showTicks: true,
                zeroAxis: false,
                orient: 'left',
                label: {
                    fontFamily: 'Arial',
                    fontSize: 8,
                    fontWeight: true,
                    fill: '#34495E'
                }
            }
        };

        return (
                <View>
                    <Text>{this.props.navigation.state.params.book.title}</Text>
                    <TextInput value={this.state.price} onChangeText={(price) => this.setState({price})}/>
                    <TextInput value={this.state.sellerEmail}
                               onChangeText={(sellerEmail) => this.setState({sellerEmail})}/>
                    <TextInput value={this.state.sellerName}
                               onChangeText={(sellerName) => this.setState({sellerName})}/>
                    <DatePicker date={this.state.publishingDate}
                                mode="date"
                                placeholder="select date"
                                onDateChange={(date) => {
                                    this.setState({publishingDate: date})
                                }}
                    />
                    <Button title="BUY" color='#660000' onPress={() => {
                        this.dbRefUsers.on('value',(snap)=>{
                            if(snap.val().email != this.state.sellerEmail) {
                                this.dbRef.child(this.props.navigation.state.params.book.title).remove().then(() => {
                                    this.props.navigation.state.params.stateModified();
                                    this.props.navigation.goBack();
                                })
                            }
                            else{
                                Alert.alert("You cannot buy your own book!")
                            }
                        });
                        }}
                    />
                    <Button title="SAVE CHANGES" onPress={() => {
                        this.dbRefUsers.on('value',(snap)=>{
                                if(snap.val().email == this.state.sellerEmail) {
                                    this.dbRef.child(this.props.navigation.state.params.book.title).set({
                                        title: this.props.navigation.state.params.book.title,
                                        price: this.state.price,
                                        sellerEmail: this.state.sellerEmail,
                                        sellerName: this.state.sellerName,
                                        publishingDate: this.state.publishingDate,
                                        description: "def descr"
                                    }).then(() => {
                                        this.props.navigation.state.params.stateModified();
                                        this.props.navigation.goBack();
                                    });
                                }
                                else{
                                    Alert.alert("This is not your book, you can only buy it!")
                                }
                            });
                    }
                    }
                    />
                    <Bar
                        data={this.state.data} options={opts} accessorKey='price'
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
        justifyContent: 'space-between',
    },
    chart: {
        flex: 1
    }
});
