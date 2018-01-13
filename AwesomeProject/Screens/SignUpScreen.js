import React from 'react';
import { StyleSheet, Text, View, TextInput, Button, Linking, Switch } from 'react-native';

export class SignUpScreen extends React.Component {

    constructor(props){
        super(props);
        this.state = {email: "", password: "", name: "", premium: false}
        this.dbRef = global.firebaseApp.database().ref().child('users');
        this.auth = global.firebaseApp.auth();
        this.auth.onAuthStateChanged((user) => {
            if(user){
                this.dbRef.child(user.uid).set({
                    email: this.state.email,
                    name: this.state.name,
                    password: this.state.password,
                    premium: this.state.premium
                })
            }
        })
    }

    render() {
        return (
            <View style={styles.container}>
                <View style={{flexDirection: 'column', flex: 0.2, width: '80%'}}>
                    <View style={{flexDirection: 'row'}}>
                        <Text>Name</Text>
                        <TextInput style={{width: '85%'}} onChangeText={(name) => this.setState({name})}/>
                    </View>
                    <View style={{flexDirection: 'row'}}>
                        <Text>Email</Text>
                        <TextInput style={{width: '85%'}} onChangeText={(email) => this.setState({email})}/>
                    </View>
                    <View style={{flexDirection: 'row'}}>
                        <Text>Password</Text>
                        <TextInput style={{width: '80%'}} onChangeText={(password) => this.setState({password})}/>
                    </View>
                    <View style={{flexDirection: 'row'}}>
                        <Text>Premium</Text>
                        <Switch value={this.state.premium}
                                onValueChange={(isPremium) => this.setState({premium: isPremium}) }/>
                    </View>
                    <Button title="REGISTER" onPress={ () => {
                        this.auth.createUserWithEmailAndPassword(this.state.email, this.state.password);
                    }}
                    />
                </View>
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
