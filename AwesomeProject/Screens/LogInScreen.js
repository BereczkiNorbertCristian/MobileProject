import React from 'react';
import { StyleSheet, Text, View, TextInput, Button, Linking, Alert } from 'react-native';

export class LogInScreen extends React.Component {

    constructor(props){
        super(props);
        const {navigate} = this.props.navigation;
        this.state = {email: "", password: ""}
        this.auth = global.firebaseApp.auth();
        this.auth.onAuthStateChanged((user) => {
            if(user){
                return navigate('ListBooks', {uid: user.uid,email: this.state.email});
            }
            else{
                if(this.state.username != "" && this.state.password != ""){
                    Alert.alert('Log in failed', 'Wrong username or password', [{text: 'OK', onPress: () => console.log('OK')}]);
                }
            }
        })
    }

    render() {
        const {navigate} = this.props.navigation;
        return (
            <View style={styles.container}>
                <View >
                    <TextInput onChangeText={(email) => this.setState({email})}/>
                    <TextInput onChangeText={(password) => this.setState({password})}/>
                    <Button title="LOG IN" onPress={ () => {
                        this.auth.signInWithEmailAndPassword(this.state.email, this.state.password);
                    }
                    }
                    />
                    <Button title="SIGN UP" onPress={ () => {
                        navigate('SignUpScreen');
                    }
                    }
                    />
                </View>
            </View>
        );
    }
}



const styles = StyleSheet.create({
    container: {
        backgroundColor: '#fff',
    },
});
